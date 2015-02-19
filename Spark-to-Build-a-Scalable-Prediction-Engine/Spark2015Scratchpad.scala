//First take a look and run the code for building kafka stream for meetup.com RSVP API in this repo

//Load history data to Spark from file in process above to get started
val sqlContext = new org.apache.spark.sql.SQLContext(sc)
val path = "/home/odroid/meetupstream10all"
val meetup = sqlContext.jsonFile(path)
meetup.registerTempTable("meetup")

//Save data to parquet for Impala 
val meetup2 = sqlContext.sql("select event.event_id,event.event_name,event.event_url,event.time,guests,member.member_id,member.member_name,member.other_services.facebook.identifier as facebook_identifier,member.other_services.linkedin.identifier as linkedin_identifier,member.other_services.twitter.identifier as twitter_identifier,member.photo,mtime,response,rsvp_id,venue.lat,venue.lon,venue.venue_id,venue.venue_name,visibility from meetup where mtime is not null")
meetup2.saveAsParquetFile("meetup10.parquet")


//Prep data for modeling in Spark
import sqlContext.createSchemaRDD
meetup2.registerTempTable("meetup2")
val meetup3 = sqlContext.sql("select mtime from meetup2")
import org.joda.time.DateTime
val meetup4 = meetup3.map(x => new DateTime(x(0).toString().toLong,DateTimeZone.forID("America/Los_Angeles"))).map(x => (x.toString("yyyy-MM-dd HH"),x.toString("yyyy-MM-dd"),x.toString("HH")) )
case class Meetup(mdatehr: String, mdate: String, mhour: String)
val meetup5 = meetup4.map(p => Meetup(p._1, p._2, p._3))
meetup5.registerTempTable("meetup5")
val meetup6 = sqlContext.sql("select mdate,mhour,count(*) as rsvp_cnt from meetup5 where mdatehr >= '2015-02-14 08' group by mdatehr,mdate,mhour")
meetup6.registerTempTable("meetup6")
sqlContext.sql("cache table meetup6")
val meetup7 = sqlContext.sql("select case when mhour=0 then 1 else 0 end as hr0,case when mhour=1 then 1 else 0 end as hr1,case when mhour=2 then 1 else 0 end as hr2,case when mhour=3 then 1 else 0 end as hr3,case when mhour=4 then 1 else 0 end as hr4,case when mhour=5 then 1 else 0 end as hr5,case when mhour=6 then 1 else 0 end as hr6,case when mhour=7 then 1 else 0 end as hr7,case when mhour=8 then 1 else 0 end as hr8,case when mhour=9 then 1 else 0 end as hr9,case when mhour=10 then 1 else 0 end as hr10,case when mhour=11 then 1 else 0 end as hr11,case when mhour=12 then 1 else 0 end as hr12,case when mhour=13 then 1 else 0 end as hr13,case when mhour=14 then 1 else 0 end as hr14,case when mhour=15 then 1 else 0 end as hr15,case when mhour=16 then 1 else 0 end as hr16,case when mhour=17 then 1 else 0 end as hr17,case when mhour=18 then 1 else 0 end as hr18,case when mhour=19 then 1 else 0 end as hr19,case when mhour=20 then 1 else 0 end as hr20,case when mhour=21 then 1 else 0 end as hr21,case when mhour=22 then 1 else 0 end as hr22,case when mhour=23 then 1 else 0 end as hr23,case when mdate in ('2015-02-14','2015-02-15') then 1 else 0 end as weekend_day,mdate,mhour,rsvp_cnt from meetup6")
meetup7.registerTempTable("meetup7")
sqlContext.sql("cache table meetup7")


//Build Regression model and score in Spark
import org.apache.spark.mllib.regression.RidgeRegressionWithSGD
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint

val trainingData = meetup7.map { row =>
  val features = Array[Double](1.0,row(24).toString().toDouble,row(0).toString().toDouble, row(1).toString().toDouble, row(2).toString().toDouble, row(3).toString().toDouble, row(4).toString().toDouble, row(5).toString().toDouble, row(6).toString().toDouble,row(7).toString().toDouble,row(8).toString().toDouble,row(9).toString().toDouble,row(10).toString().toDouble, row(11).toString().toDouble, row(12).toString().toDouble, row(13).toString().toDouble, row(14).toString().toDouble, row(15).toString().toDouble, row(16).toString().toDouble,row(17).toString().toDouble,row(18).toString().toDouble,row(19).toString().toDouble,row(20).toString().toDouble, row(21).toString().toDouble, row(22).toString().toDouble, row(23).toString().toDouble)
  LabeledPoint(row(27).toString().toDouble, Vectors.dense(features))
}
val model = new RidgeRegressionWithSGD().run(trainingData)

val scores = meetup7.map { row =>
  val features = Vectors.dense(Array[Double](1.0,row(24).toString().toDouble, row(0).toString().toDouble, row(1).toString().toDouble, row(2).toString().toDouble, row(3).toString().toDouble, row(4).toString().toDouble, row(5).toString().toDouble, row(6).toString().toDouble,row(7).toString().toDouble,row(8).toString().toDouble,row(9).toString().toDouble,row(10).toString().toDouble, row(11).toString().toDouble, row(12).toString().toDouble, row(13).toString().toDouble, row(14).toString().toDouble, row(15).toString().toDouble, row(16).toString().toDouble,row(17).toString().toDouble,row(18).toString().toDouble,row(19).toString().toDouble,row(20).toString().toDouble, row(21).toString().toDouble, row(22).toString().toDouble, row(23).toString().toDouble))
  (row(25),row(26),row(27), model.predict(features))
}

scores.foreach(println)




//Cassandra
create table meetup.rsvps (
 event_id              text ,
 event_name            text ,
 event_url             text ,
 time                  bigint ,
 guests                int    ,
 member_id             int    ,
 member_name           text ,
 facebook_identifier   text ,
 linkedin_identifier   text ,
 twitter_identifier    text ,
 photo                 text ,
 mtime                 bigint ,
 response              text ,
 rsvp_id               int    ,
 lat                   double ,
 lon                   double ,
 venue_id              int    ,
 venue_name            text ,
 visibility            text ,
PRIMARY KEY ((event_id),member_id,rsvp_id));

import com.datastax.spark.connector._
//test record
val collection = sc.parallelize(Seq((219903663," Wattamolla bushwalk, swim and picnic                                    ", "http://www.meetup.com/Wollongong-40-get-out-and-have-fun-club/events/219903663/" ,1.42395E+12,0,183356280, "PeterE"      , "NULL"                , "NULL"                , "NULL"               , "http://photos1.meetupstatic.com/photos/member/7/8/b/5/thumb_242670901.jpeg" ,1.42381E+12, "no"       ,1532853758,-34.136852,151.097794,11246522, "Wattamolla beach (34°08_15_S 151°07_04_E)" , "public")))
collection.saveToCassandra("meetup","rsvps", SomeColumns("event_id","event_name","event_url","time","guests","member_id","member_name","facebook_identifier","linkedin_identifier","twitter_identifier","photo","mtime","response","rsvp_id","lat","lon","venue_id","venue_name","visibility"))

//Streaming load to Cassandra
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.log4j.Logger
import org.apache.log4j.Level
Logger.getLogger("org").setLevel(Level.WARN)
Logger.getLogger("akka").setLevel(Level.WARN)
val ssc = new StreamingContext(sc, Seconds(10))
import com.datastax.spark.connector._
val lines = KafkaUtils.createStream(ssc, "localhost:2181", "meetupstream", Map("meetupstream" -> 10)).map(_._2)
val sqlContext = new org.apache.spark.sql.SQLContext(sc)
import sqlContext._
lines.foreachRDD(rdd => {
  val lines2 = sqlContext.jsonRDD(rdd)
  lines2.registerTempTable("lines2")
  val lines3 = sqlContext.sql("select event.event_id,event.event_name,event.event_url,event.time,guests,member.member_id,member.member_name,member.other_services.facebook.identifier as facebook_identifier,member.other_services.linkedin.identifier as linkedin_identifier,member.other_services.twitter.identifier as twitter_identifier,member.photo,mtime,response,rsvp_id,venue.lat,venue.lon,venue.venue_id,venue.venue_name,visibility from lines2")
  //val lines3 = lines2.map(row => row.toString)
  val lines4 = lines3.map(line => (line(0), line(5), line(13), line(1), line(2), line(3), line(4), line(6), line(7), line(8), line(9), line(10), line(11), line(12), line(14), line(15), line(16), line(17), line(18) )) 
  lines4.saveToCassandra("meetup","rsvps", SomeColumns("event_id","member_id","rsvp_id","event_name","event_url","time","guests","member_name","facebook_identifier","linkedin_identifier","twitter_identifier","photo","mtime","response","lat","lon","venue_id","venue_name","visibility"))
  })




Impala:
CREATE EXTERNAL TABLE meetup10 LIKE PARQUET '/user/ubuntu/meetup10/_metadata' STORED AS PARQUET LOCATION '/user/ubuntu/meetup10';

create table rsvps_by_hour10 as select from_unixtime(cast(mtime/1000 as bigint), "yyyy-MM-dd") as mdate, cast(from_unixtime(cast(mtime/1000 as bigint), "HH") as int) as mhour, count(*) as rsvp_cnt from meetup10 where from_unixtime(cast(mtime/1000 as bigint), "yyyy-MM-dd HH") >= "2015-02-14 16" group by 1,2

create table rsvps_by_hr_training10 as (
select 
 case when mhour=0 then 1 else 0 end as hr0
,case when mhour=1 then 1 else 0 end as hr1
,case when mhour=2 then 1 else 0 end as hr2
,case when mhour=3 then 1 else 0 end as hr3
,case when mhour=4 then 1 else 0 end as hr4
,case when mhour=5 then 1 else 0 end as hr5
,case when mhour=6 then 1 else 0 end as hr6
,case when mhour=7 then 1 else 0 end as hr7
,case when mhour=8 then 1 else 0 end as hr8
,case when mhour=9 then 1 else 0 end as hr9
,case when mhour=10 then 1 else 0 end as hr10
,case when mhour=11 then 1 else 0 end as hr11
,case when mhour=12 then 1 else 0 end as hr12
,case when mhour=13 then 1 else 0 end as hr13
,case when mhour=14 then 1 else 0 end as hr14
,case when mhour=15 then 1 else 0 end as hr15
,case when mhour=16 then 1 else 0 end as hr16
,case when mhour=17 then 1 else 0 end as hr17
,case when mhour=18 then 1 else 0 end as hr18
,case when mhour=19 then 1 else 0 end as hr19
,case when mhour=20 then 1 else 0 end as hr20
,case when mhour=21 then 1 else 0 end as hr21
,case when mhour=22 then 1 else 0 end as hr22
,case when mhour=23 then 1 else 0 end as hr23
,case when mdate in ("2015-02-14","2015-02-15") then 1 else 0 end as weekend_day
,mdate,mhour,rsvp_cnt from rsvps_by_hour10);

//Train Model
use toysvm; SELECT printarray(linr(toarray(hr0,hr1,hr2,hr3,hr4,hr5,hr6,hr7,hr8,hr9,hr10,hr11,hr12,hr13,hr14,hr15,hr16,hr17,hr18,hr19,hr20,hr21,hr22,hr23,weekend_day), rsvp_cnt)) from meetup.rsvps_by_hr_training;

//resulting coefficients (could have been saved to cassandra or impala table for automating)
<8037.43, 7883.93, 7007.68, 6851.91, 6307.91, 5468.24, 4792.58, 4336.91, 4330.24, 4360.91, 4373.24, 4711.58, 5649.91, 6752.24, 8056.24, 9042.58, 9761.37, 10205.9, 10365.6, 10048.6, 9946.12, 9538.87, 9984.37, 9115.12, -2323.73>

//score new data
select mdate,mhour,cast(linrpredict(toarray(8037.43, 7883.93, 7007.68, 6851.91, 6307.91, 5468.24, 4792.58, 4336.91, 4330.24, 4360.91, 4373.24, 4711.58, 5649.91, 6752.24, 8056.24, 9042.58, 9761.37, 10205.9, 10365.6, 10048.6, 9946.12, 9538.87, 9984.37, 9115.12, -2323.73), toarray(hr0, hr1, hr2, hr3, hr4, hr5, hr6, hr7, hr8, hr9, hr10, hr11, hr12, hr13, hr14, hr15, hr16, hr17, hr18, hr19, hr20, hr21, hr22, hr23, weekend_day)) as int) as rsvp_cnt_pred, rsvp_cnt from meetup.rsvps_by_hr_training10 order by 1,2





//Spark Streaming (working version)
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.regression.StreamingLinearRegressionWithSGD
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
val ssc = new StreamingContext(sc, Seconds(20))
val trainingData = ssc.textFileStream("file:///data/TrainStreamDir").map(LabeledPoint.parse)
val testData = ssc.textFileStream("file:///data/TestStreamDir").map(LabeledPoint.parse)
val numFeatures = 2
trainingData.print()
val model = new StreamingLinearRegressionWithSGD().setInitialWeights(Vectors.zeros(numFeatures))
model.trainOn(trainingData)
model.predictOnValues(testData.map(lp => (lp.label, lp.features))).print()
ssc.start()

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.regression.StreamingLinearRegressionWithSGD
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
val ssc = new StreamingContext(sc, Seconds(10))

val trainingData = ssc.textFileStream("file:///data/train/").map(LabeledPoint.parse).cache()
val testData = ssc.textFileStream("file:///data/test/").map(LabeledPoint.parse)

val numFeatures = 2
//val model = new StreamingLinearRegressionWithSGD().setInitialWeights(Vectors.zeros(numFeatures))
var model = new StreamingLinearRegressionWithSGD().setInitialWeights(Vectors.dense(Array(1.0,1.0))).trainOn(trainingData)

//model.trainOn(trainingData)
model.predictOn(testData.map(lp => (lp.features))).print()

ssc.start(); ssc.awaitTermination()

//adtl test code for streaming regression
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.log4j.Logger
import org.apache.log4j.Level
Logger.getLogger("org").setLevel(Level.WARN)
Logger.getLogger("akka").setLevel(Level.WARN)
val ssc = new StreamingContext(sc, Seconds(60))
ssc.checkpoint("/tmp/cp")
val lines = KafkaUtils.createStream(ssc, "localhost:2181", "meetupstream", Map("meetupstream" -> 10)).map(_._2)
import org.json4s._
import org.json4s.jackson.JsonMethods._
val lines2 = lines.map(l => parse(l))
val lines3 = lines2.map(l => ( ( (compact(render(l \ "mtime")).toDouble / 1000) - 1424194230)).toLong)
//lines3.print()
val lines5 = lines3.countByValue().map(x => (x._1,x._2 + 1000))
//val lines5 = lines4.filter(x => x._2 > 1000)
//lines5.print()

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.regression.StreamingLinearRegressionWithSGD

//val lines6 = lines5.map(x => LabeledPoint(x._2.toDouble, Vectors.dense(Array(x._1,1.0)))).cache()
val lines6 = lines5.map(x => LabeledPoint(x._2+5.toDouble, Vectors.dense(Array(x._2/2.0,x._2/2.0)))).cache()
lines6.print()
val numFeatures = 2
//val model = new StreamingLinearRegressionWithSGD().setInitialWeights(Vectors.zeros(numFeatures)).setMiniBatchFraction(1).setNumIterations(25)
val model = new StreamingLinearRegressionWithSGD().setInitialWeights(Vectors.dense(Array(5.0,1.0)))
model.trainOn(lines6)
model.predictOnValues(lines6.map(lp => (lp.label, lp.features))).print()
model.latestModel()

ssc.start()



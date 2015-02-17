#!/bin/bash
#
# Copyright 2015 Silicon Valley Data Science.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
hadoop fs -put fakedata_uuid.dat /tmp/fakedata_uuid.dat
hadoop fs -mkdir -p /user/hive/warehouse/strataca2015.db/wide_table_text
hadoop jar /opt/cloudera/parcels/CDH/lib/hadoop-mapreduce/hadoop-streaming.jar -D mapred.reduce.tasks=0 -D mapreduce.output.fileoutputformat.compress=true -D mapreduce.output.fileoutputformat.compress.codec=org.apache.hadoop.io.compress.SnappyCodec -mapper /bin/cat -input /tmp/fakedata_uuid.dat -output /tmp/fakedata_uuid.dat.snappy
hadoop fs -mv /tmp/fakedata_uuid.dat.snappy/p* /user/hive/warehouse/strataca2015.db/wide_table_text

hadoop fs -mkdir -p /user/hive/warehouse/strataca2015.db/drwho_text
hadoop fs -put drwho.dat /user/hive/warehouse/strataca2015.db/drwho_text/.




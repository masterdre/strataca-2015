-- Copyright 2015 Silicon Valley Data Science.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--      http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.

CREATE TABLE
    rsvps_by_hour10 AS
SELECT
    from_unixtime(CAST(mtime/1000 AS bigint), "yyyy-MM-dd")      AS mdate,
    CAST(from_unixtime(CAST(mtime/1000 AS bigint), "HH") AS INT) AS mhour,
    COUNT(*)                                                     AS rsvp_cnt
FROM
    meetup10
WHERE
    from_unixtime(CAST(mtime/1000 AS bigint), "yyyy-MM-dd HH") >= "2015-02-14 16"
GROUP BY
    1,2;
    
CREATE TABLE
    rsvps_by_hr_training10 AS
    (
        SELECT
            CASE
                WHEN mhour=0
                THEN 1
                ELSE 0
            END AS hr0 ,
            CASE
                WHEN mhour=1
                THEN 1
                ELSE 0
            END AS hr1 ,
            CASE
                WHEN mhour=2
                THEN 1
                ELSE 0
            END AS hr2 ,
            CASE
                WHEN mhour=3
                THEN 1
                ELSE 0
            END AS hr3 ,
            CASE
                WHEN mhour=4
                THEN 1
                ELSE 0
            END AS hr4 ,
            CASE
                WHEN mhour=5
                THEN 1
                ELSE 0
            END AS hr5 ,
            CASE
                WHEN mhour=6
                THEN 1
                ELSE 0
            END AS hr6 ,
            CASE
                WHEN mhour=7
                THEN 1
                ELSE 0
            END AS hr7 ,
            CASE
                WHEN mhour=8
                THEN 1
                ELSE 0
            END AS hr8 ,
            CASE
                WHEN mhour=9
                THEN 1
                ELSE 0
            END AS hr9 ,
            CASE
                WHEN mhour=10
                THEN 1
                ELSE 0
            END AS hr10 ,
            CASE
                WHEN mhour=11
                THEN 1
                ELSE 0
            END AS hr11 ,
            CASE
                WHEN mhour=12
                THEN 1
                ELSE 0
            END AS hr12 ,
            CASE
                WHEN mhour=13
                THEN 1
                ELSE 0
            END AS hr13 ,
            CASE
                WHEN mhour=14
                THEN 1
                ELSE 0
            END AS hr14 ,
            CASE
                WHEN mhour=15
                THEN 1
                ELSE 0
            END AS hr15 ,
            CASE
                WHEN mhour=16
                THEN 1
                ELSE 0
            END AS hr16 ,
            CASE
                WHEN mhour=17
                THEN 1
                ELSE 0
            END AS hr17 ,
            CASE
                WHEN mhour=18
                THEN 1
                ELSE 0
            END AS hr18 ,
            CASE
                WHEN mhour=19
                THEN 1
                ELSE 0
            END AS hr19 ,
            CASE
                WHEN mhour=20
                THEN 1
                ELSE 0
            END AS hr20 ,
            CASE
                WHEN mhour=21
                THEN 1
                ELSE 0
            END AS hr21 ,
            CASE
                WHEN mhour=22
                THEN 1
                ELSE 0
            END AS hr22 ,
            CASE
                WHEN mhour=23
                THEN 1
                ELSE 0
            END AS hr23 ,
            CASE
                WHEN mdate IN ("2015-02-14",
                               "2015-02-15")
                THEN 1
                ELSE 0
            END AS weekend_day ,
            mdate,
            mhour,
            rsvp_cnt
        FROM
            rsvps_by_hour10
    );

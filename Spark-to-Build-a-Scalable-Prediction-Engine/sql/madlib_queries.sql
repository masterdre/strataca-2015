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

USE toysvm;
SELECT
    printarray(linr(toarray(hr0,hr1,hr2,hr3,hr4,hr5,hr6,hr7,hr8,hr9,hr10,hr11,hr12,hr13,hr14,hr15,
    hr16,hr17,hr18,hr19,hr20,hr21,hr22,hr23,weekend_day), rsvp_cnt))
FROM
    meetup.rsvps_by_hr_training;

//resulting coefficients (could have been saved to cassandra or impala table for automating)
//<8037.43, 7883.93, 7007.68, 6851.91, 6307.91, 5468.24, 4792.58, 4336.91, 4330.24, 4360.91, 4373.24,
//4711.58, 5649.91, 6752.24, 8056.24, 9042.58, 9761.37, 10205.9, 10365.6, 10048.6, 9946.12, 9538.87,
//9984.37, 9115.12, -2323.73>
//score new data

SELECT
    mdate,
    mhour,
    CAST(linrpredict(toarray(8037.43, 7883.93, 7007.68, 6851.91, 6307.91, 5468.24, 4792.58, 4336.91
    , 4330.24, 4360.91, 4373.24, 4711.58, 5649.91, 6752.24, 8056.24, 9042.58, 9761.37, 10205.9,
    10365.6, 10048.6, 9946.12, 9538.87, 9984.37, 9115.12, -2323.73), toarray(hr0, hr1, hr2, hr3,
    hr4, hr5, hr6, hr7, hr8, hr9, hr10, hr11, hr12, hr13, hr14, hr15, hr16, hr17, hr18, hr19, hr20,
    hr21, hr22, hr23, weekend_day)) AS INT) AS rsvp_cnt_pred,
    rsvp_cnt
FROM
    meetup.rsvps_by_hr_training10
ORDER BY
    1,2;
    
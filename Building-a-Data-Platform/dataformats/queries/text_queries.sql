/*
 * Copyright 2015 Silicon Valley Data Science.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 SELECT
    COUNT(*)
FROM
    strataca2015.strata_wide_table_text;
    
SELECT
    COUNT(*)
FROM
    strataca2015.strata_wide_table_text
WHERE
    email2_183 NOT LIKE '%edu'
AND email2_11 LIKE '%a%'
AND email2_82 NOT LIKE '%edu'
AND alpha2_90 LIKE '%A%C%'
AND alpha2_98 LIKE '%B%A%';

SELECT
    COUNT(*)
FROM
    strataca2015.strata_wide_table_text
WHERE
    email2_74 NOT LIKE '%com'
AND street2_115 LIKE '%6%'
AND alpha2_60 LIKE 'U%'
AND street2_64 NOT LIKE '%Rd%'
AND word2_110 NOT LIKE '%lorem%'
AND street2_46 NOT LIKE '%Street%'
AND num2_178 >100
AND street2_145 NOT LIKE '%Ave%'
AND alpha2_187 LIKE '%C%U%'
AND alpha2_51 NOT LIKE '%A%';

SELECT
    COUNT(*)
FROM
    strataca2015.strata_wide_table_text
WHERE
    alpha2_15 NOT LIKE '%A%B%'
AND word2_151 LIKE '%c%'
AND num2_199 > 109
AND word2_147 LIKE '%a%'
AND email2_24 NOT LIKE '%edu'
AND num2_95 !=100
AND email2_49 NOT LIKE '%uk'
AND street2_157 NOT LIKE '%Rd%'
AND word2_71 LIKE '%s%'
AND alpha2_133 LIKE '%A%B%'
AND street2_53 NOT LIKE '%Street%'
AND street2_45 NOT LIKE '%Road%'
AND alpha2_16 LIKE '%E%'
AND num2_101 !=99
AND alpha2_108 LIKE '%A%'
AND street2_83 NOT LIKE '%Ave%'
AND word2_181 LIKE '%qu%'
AND email2_2 LIKE '%ca'
AND num2_76 <100
AND alpha2_115 LIKE '%AO%';


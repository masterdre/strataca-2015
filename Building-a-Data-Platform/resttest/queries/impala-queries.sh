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
set -x 
curl -X GET "http://localhost:8080/resettest/v1/genericData/count/impala/fake-people-parquet"
echo ""
curl -X GET "http://localhost:8080/resettest/v1/genericData/count/impala/fake-people-parquet?operator__zipcode_plus4=stringlike&column__zipcode_plus4=12345%25&operator__first_name=stringlike&column__first_name=J%25&limit=10"
echo ""
curl -X GET "http://localhost:8080/resettest/v1/genericData/count/impala/fake-people-parquet?operator__zipcode_plus4=stringlike&column__zipcode_plus4=90120%25&operator__first_name=stringlike&column__first_name=J%25&limit=10"
echo ""
curl -X GET "http://localhost:8080/resettest/v1/genericData/results/impala/fake-people-parquet?operator__zipcode_plus4=stringlike&column__zipcode_plus4=12345%25&operator__first_name=stringlike&column__first_name=J%25&limit=10"
echo ""
curl -X GET "http://localhost:8080/resettest/v1/genericData/results/impala/fake-people-parquet?operator__zipcode_plus4=stringlike&column__zipcode_plus4=90120%25&operator__first_name=stringlike&column__first_name=J%25&limit=10"
echo ""
sleep 10
curl -X GET "http://localhost:8080/resettest/v1//genericData/count/impala/strata-wide-parquet"
echo ""
curl -X GET "http://localhost:8080/resettest/v1//genericData/count/impala/strata-wide-parquet?operator__alpha2_199=stringequals&column__alpha2_199=OZCOU2AC9AVB68&operator__num2_0=intequals&columns__num2_0=2602"
echo ""
curl -X GET "http://localhost:8080/resettest/v1//genericData/count/impala/strata-wide-parquet?operator__alpha2_15=stringnotlike&&column__alpha2_15=%25A%25B%25&operator__word2_151=stringlike&column__word2_151=%25c%25&operator__num2_199=intgreaterthan&column__num2_199=109&operator__word2_147=stringlike&column__word2_147=%25a%25&operator__email2_24=stringnotlike&column__email2_24=%25edu&operator__num2_95=intnotequals&column__num2_95=100&operator__email2_49=stringnotlike&column__email2_49=%25uk&operator__street2_157=stringnotlike&column__street2_157=%25Rd%25&operator__word2_71=stringlike&column__word2_71=%25s%25&operator__alpha2_133=stringlike&column__alpha2_133=%25A%25B%25&operator__street2_53=stringnotlike&column__street2_53=%25Street%25&operator__street2_45=stringnotlike&column__street2_45=%25Road%25&operator__alpha2_16=stringlike&column__alpha2_16=%25E%25&operator__num2_101=intnotequals&column__num2_101=99&operator__alpha2_108=stringlike&column__alpha2_108=%25A%25&operator__street2_83=stringnotlike&column__street2_83=%25Ave%25&operator__word2_181=stringlike&column__word2_181=%25qu%25&operator__email2_2=stringlike&column__email2_2=%25ca&operator__num2_76=intlessthan&column__num2_76=100&operator__alpha2_115=stringlike&column__alpha2_115=%25AO%25"
echo ""
curl -X GET "http://localhost:8080/resettest/v1//genericData/results/impala/strata-wide-parquet?operator__alpha2_15=stringnotlike&&column__alpha2_15=%25A%25B%25&operator__word2_151=stringlike&column__word2_151=%25c%25&operator__num2_199=intgreaterthan&column__num2_199=109&operator__word2_147=stringlike&column__word2_147=%25a%25&operator__email2_24=stringnotlike&column__email2_24=%25edu&operator__num2_95=intnotequals&column__num2_95=100&operator__email2_49=stringnotlike&column__email2_49=%25uk&operator__street2_157=stringnotlike&column__street2_157=%25Rd%25&operator__word2_71=stringlike&column__word2_71=%25s%25&operator__alpha2_133=stringlike&column__alpha2_133=%25A%25B%25&operator__street2_53=stringnotlike&column__street2_53=%25Street%25&operator__street2_45=stringnotlike&column__street2_45=%25Road%25&operator__alpha2_16=stringlike&column__alpha2_16=%25E%25&operator__num2_101=intnotequals&column__num2_101=99&operator__alpha2_108=stringlike&column__alpha2_108=%25A%25&operator__street2_83=stringnotlike&column__street2_83=%25Ave%25&operator__word2_181=stringlike&column__word2_181=%25qu%25&operator__email2_2=stringlike&column__email2_2=%25ca&operator__num2_76=intlessthan&column__num2_76=100&operator__alpha2_115=stringlike&column__alpha2_115=%25AO%25"
echo ""
sleep 10
curl -X GET "http://localhost:8080/resettest/v1//genericData/count/impala/drwho-parquet"
echo ""
curl -X GET "http://localhost:8080/resettest/v1//genericData/results/impala/drwho-parquet"
echo ""
curl -X GET "http://localhost:8080/resettest/v1//genericData/results/impala/drwho-parquet?operator__episode_title=stringlike&column__episode_title=%25Cybermen%25"
echo ""
curl -X GET "http://localhost:8080/resettest/v1//genericData/results/impala/drwho-parquet?operator__episode_title=stringlike&column__episode_title=%25Daleks%25"

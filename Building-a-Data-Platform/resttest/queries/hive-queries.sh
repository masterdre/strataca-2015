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
curl -X GET "http://localhost:8080/resettest/v1/genericData/count/hive/fake-people-parquet"
echo ""
curl -X GET "http://localhost:8080/resettest/v1/genericData/count/hive/fake-people-parquet?operator__zipcode_plus4=stringlike&column__zipcode_plus4=12345%25&operator__first_name=stringlike&column__first_name=J%25&limit=10"
echo ""
curl -X GET "http://localhost:8080/resettest/v1/genericData/count/hive/fake-people-parquet?operator__zipcode_plus4=stringlike&column__zipcode_plus4=90120%25&operator__first_name=stringlike&column__first_name=J%25&limit=10"
echo ""
curl -X GET "http://localhost:8080/resettest/v1/genericData/results/hive/fake-people-parquet?operator__zipcode_plus4=stringlike&column__zipcode_plus4=12345%25&operator__first_name=stringlike&column__first_name=J%25&limit=10"
echo ""
curl -X GET "http://localhost:8080/resettest/v1/genericData/results/hive/fake-people-parquet?operator__zipcode_plus4=stringlike&column__zipcode_plus4=90120%25&operator__first_name=stringlike&column__first_name=J%25&limit=10"

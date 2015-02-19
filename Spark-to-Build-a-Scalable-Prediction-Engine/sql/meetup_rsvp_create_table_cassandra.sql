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
    meetup.rsvps
    (
        event_id text ,
        event_name text ,
        event_url text ,
        TIME bigint ,
        guests INT ,
        member_id INT ,
        member_name text ,
        facebook_identifier text ,
        linkedin_identifier text ,
        twitter_identifier text ,
        photo text ,
        mtime bigint ,
        response text ,
        rsvp_id INT ,
        lat DOUBLE ,
        lon DOUBLE ,
        venue_id INT ,
        venue_name text ,
        visibility text ,
        PRIMARY KEY ((event_id),member_id,rsvp_id)
    );
    

# Description

The files under this directory have SQL queries that can be run in Hive and Impala to run aggregation queries for each of the three file formats.

These queries provide an example of the types of queries that can be run across the three formats (note no differences in the SQL other than the tables being queried).
In addition, they demonstrate how the impact to performance changes as we add more columns and how that varies depending on the storage type.

# Prerequisites

- Hadoop environment needed to run, in particular CDH for Impala examples. Tested under CDH 5.1.2 using parcels.
- JDK 7. Tested with JDK 1.7u55

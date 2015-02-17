# Description

This directory contains the shell scripts used to load the data into Hadoop.

The `loadata_into_hdfs.sh` shell script takes the two input data files from the `data` folder and stores them into Hadoop. The fakedata file is Snappy compressed and both files are added as tables in Hive.

Note the location of the Snappy jar file. This may be different in your installation of Hadoop. The location here corresponds with CDH 5.1 installed using parcels.

More info on the `data` files can be found in the `README.md` of the corresponding directory.

# Prerequisites

- Hadoop environment needed to run, in particular CDH for Impala examples. Tested under CDH 5.1.2 using parcels.
- JDK 7. Tested with JDK 1.7u55

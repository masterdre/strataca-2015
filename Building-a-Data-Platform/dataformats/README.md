# Description

The `dataformats` folder provides a set of examples to load data into Hadoop, store it in three different storage formats (text, sequence, and parquet), and run aggregation queries against each of these formats with different types of filters.

In order to setup the example, the code must be executed in the following order:
- Review the data contained under the `data` folder that will be loaded into Hadoop.
- Load the data into Hadoop running the shell script under the `bin` folder.
- Create the Hive tables in the three formats by running the queries under the `sql` folder.
- Run the aggregation queries under the `queries` folder to review the differences in query execution.

Additional details can be found in the README file in each of the subfolders.

*Optional*
- Run the fakedata python script under the `fakedata` folder to generate the fake user information.
- Load the data into Hadoop by running your own `hadoop` commands or extending the script under the `bin` folder.
- Create the Hive tables. There are starter scripts under the `sql` folder.

# Prerequisites

- Hadoop environment needed to run, in particular CDH for Impala examples. Tested under CDH 5.1.2 using parcels.
- JDK 7. Tested with JDK 1.7u55

*Optional*
- Python 2.7 or above to run the fakedata script.
- Pip or easyinstall to install the faker lib

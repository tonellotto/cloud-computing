# Spark Context

We have seen that Spark can be used interactively with the Spark Shell and Python. Now we will focus on the development of applications that will run on top of Spark.

Apart from running interactively, Spark can be linked into standalone applications in either Java, Scala, or Python. The main difference from using it in the shell is that you need to initialize your own SparkContext. After that, the API is the same.

The process of linking to Spark varies by language. In Java and Scala, you give your application a Maven dependency on the `spark-core` artifact.

In Python, you simply write applications as Python scripts, but you must run them using the `spark-submit` script included in Spark. The `spark-submit` script includes the Spark dependencies for us in Python.

Simply run your script with the following command:

```bash
$ spark-submit my_script.py
```
When spark-submit is called with nothing but the name of a script, it simply runs the supplied Spark program locally. We can use the `--master` flag to specify the execution mode of the Spark application. Some values of this flag are:

* `local`: run in local mode with a single core.
* `local[N]`: run in local mode with N cores.
* `local[*]`: run in local mode and use as many cores as the machine has.
* `yarn`: connect to a YARN cluster. When running on YARN you need to set the `HADOOP_CONF_DIR` environment variable to point the location of your Hadoop configuration directory, which contains information about the cluster.

To run your script on our YARN cluster, installed and configuraed with our Hadoop 3 installation, use the following command:

```bash
$ spark-submit --master yarn my_script.py
```

## Initializing a SparkContext

You need to import the Spark packages in your program and create a SparkContext. You do so by first creating a `SparkConf` object to configure your application, and then building a SparkContext for it.

```python
from pyspark import SparkConf, SparkContext

conf = SparkConf().setMaster("local").setAppName("My App")
sc = SparkContext(conf = conf)
```

or, more simply:

```python
from pyspark import SparkContext

sc = SparkContext("local", "WordCount")
```

These examples show the minimal way to initialize a SparkContext, where you pass two parameters:
* A cluster URL, namely `local` in this example, which tells Spark how to connect to a cluster. `local` is a special value that runs Spark on one thread on the local machine, without connecting to a cluster.
* An application name, namely `My App` in this example. This will identify your application on the cluster manager's UI if you connect to a cluster.

After you have initialized a SparkContext, you can use all the methods we know to create RDDs (e.g., from a text file) and manipulate them.

Finally, to shut down Spark, you can either call the `stop()` method on your SparkContext, or simply exit the application (e.g., with `sys.exit()`).

# Spark Exercises

## 1. Pi

Write a Spark program to compute an approximation of <img src="https://latex.codecogs.com/svg.latex?\pi" title="\pi" /> by counting the fraction of points that end up inside the circle out of a total population of points randomly thrown at the circumscribed square. The program must accept arguments as follow:

```bash
pi [<num partitions>]
```

If `<num partitions>` is not present, the default number of partitions is 2 ([code](pi.py)).

## 2. WordCount

Write a Spark program that implements a simple "WordCount" algorithm. The program must accept arguments as follow:

```bash
wordcount <input file> [<output file>]
```

If the `<output file>` is not present, the output is printed to standard output ([code](wordcount.py)).

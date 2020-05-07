# Cloud Programming

## Lectures

|Date|Time|Room|Topics|Slide|
|:---:|:--------:|:----:|---------|:---:|
|10/03|8:30-10:30|Online| Python 3 crash course. |[Slides](slides/python.pdf)<br>[Notes](notes/python-notes.md)<br>[Notebooks](python/readme.md)|
|17/03|15:30-18:30|Online| Python 3 crash course. Introduction to the course. Overview of parallel architectures and relative programming paradigms. | [Slides](slides/intro.pdf)<br>[Slides](slides/parallel.pdf) |
|24/03|15:30-18:30|Online| Large-scale programming issues. Functional programming concepts. MapReduce programming model. Examples with pseudocode: word count and image tiling. |[Slides](slides/mapreduce.pdf) |
|31/03|15:30-18:30|Online| Partitioners and reducers. Introduction to Hadoop. Hadoop download and setup. | [Notes](notes/hadoop3-installation.md)<br>[Notebooks](notebooks) |
|07/04|15:30-18:30|Online| Hadoop setup and configuration. Programming in Hadoop (Java). [Wordcount](exercises/wordcount) exercise. | [Slides](slides/hadoop.pdf)<br>[HDFS](notes/hadoop3-hdfs.md)<br>[Java](notes/hadoop3-java-programming.md) |
|21/04|15:30-18:30|Online| Hadoop Distributed File System (HDFS). Hadoop runtime framework for MapReduce (YARN). [Moving Average](exercises/movingaverage) exercise. | [Slides](slides/hdfs_yarn.pdf) |
|28/04|15:30-18:30|Online| Fault tolerance in Hadoop MapReduce. MapReduce Design Patterns: Intermediate data reduction, Matrix generation and multiplication, Selection and filtering, Joining, Graph algorithms. [Matrix Multiplication](exercises/matrix) exercise. | [Slides](slides/design-patterns.pdf) |
|05/05|8:30-10:30|Online| Spark introduction: resilient distributed datasets, Lineage. Spark architecture: driver, executors, context.| [Slides](slides/spark-intro.pdf)<br>[Notes](notes/spark-installation-notes.md) |

## Messages

>**Node Manager Issues**<br>
If you do not see all your node managers list in the Web UI (or by running the command `yarn node -list -all` on the `hadoop-namenode` machine) please update the `yarn-site.xml` configuration files **on all your machines** with the following new property:
```xml
<property>
  <name>yarn.resourcemanager.hostname</name>
  <value>hadoop-namenode</value>
</property>
```
>Note that on all your machines you must assign the same *value*, i.e., the  hostname of the virtual machines hosting the YARN resource manager. This will allow the node managers to correctly communicate with the resource manager.

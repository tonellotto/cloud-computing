# Moving Average Exercise

Time series data represents the values of a variable over a period of time, such as a second, minute, hour, day, week, month, quarter, or year. 
We represent time series data as a sequence of triplets:

```
(key, time, value)
```

Typically, time series data occurs whenever the same measurements are recorded over a period of time. The mean (or average) of time series data (observations equally spaced in time, such as per hour or per day) from several consecutive periods is called the **moving average**. It is called moving because the average is continually recomputed as new time series data becomes available, and it progresses by dropping the earliest value and adding the most recent.

More formally, let *A* be a sequence of an ordered set of objects *A = (a_1, a_2, a_3, ..., a_n)*. Then an *N* moving average is a new sequence *s_i* with *i = 1, ...,  n − N + 1* from *a_i* by taking
the arithmetic mean of subsequences of *n* consecutive objects.

## Simple Java solution

A simple plain Java solution using an array is [here](src/main/java/it/unipi/hadoop/SimpleMovingAverage.java).

## Hadoop solution

Now we focus on the MapReduce solution for a moving average problem.
The [input](../data/stock_prices.txt) data has the following format:

```
<key as string>,<time as timestamp>,<value as double>
```

The `key` is a stock symbol, the `time` is the timestamp, and the `value` is the closing price of the stock on that day.

The output from our MapReduce solution will have the following format:

```
    <key as string>,<time as timestamp>,<moving average as double>
```

for every valid `time` value. 

We will implement the mapper to parse the input tuples and emit them with the string `key` as key and the pair `time`,`value` as value. Each reducer will received all the value pairs for a given key.
The reducer must then sort these values based on timestamp, and finally apply the moving average algorithm.

Implement:
1. the [`TimeSeriesData`](src/main/java/it/unipi/hadoop/TimeSeriesData.java) class, implementing the Hadoop's `Writable` interface, since these objects will persist in Hadoop, and `Comparable<TimeSeriesData>`, since we need to sort the objects in *time* order.
2. the [`InMemoryMovingAverage`](src/main/java/it/unipi/hadoop/InMemoryMovingAverage.java) class, containing the mapper, the reducer and the driver code.

The expected output on the [input](../data/stock_prices.txt) data with a window size of 2 is:
```
AAPL	2013-10-04, 483.22
AAPL	2013-10-07, 485.39
AAPL	2013-10-08, 484.345
AAPL	2013-10-09, 483.765
GOOG	2004-11-03, 193.26999999999998
GOOG	2004-11-04, 188.18499999999997
GOOG	2013-07-17, 551.625
GOOG	2013-07-18, 914.615
GOOG	2013-07-19, 903.6400000000001
IBM	2013-09-26, 189.845
IBM	2013-09-27, 188.57
IBM	2013-09-30, 186.05
```

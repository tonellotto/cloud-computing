# Exercises

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

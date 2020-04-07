# Word Count Exercise

Write a MapReduce program in Hadoop that implements a simple "Word Count" algorithm. The key idea is to count the number of occurrences of all word in a collection of text documents.

## Input

* Download the input file [snippets.zip](../data/snippets.zip)
* Unzip it: `unzip snippets.zip`
* The `snippets` folder contains 782 text files with name `lineXXX`, where `XXX` runs from `000` to `781`.
* Each input file contains a text on multiple lines, including numbers, punctuation, etc.

## Algorithm

Split every line in every document into "tokens" (i.e., space-delimited sequences of characters) and count, for every token, how many times it is found in the input.

## Output

The output should contain one line per word in the following format:

    <word><TAB><count>

where `word` is a space-delimited sequence of characters appearing at least once in the input files, `<TAB>` is the TAB character, and `count` is an integer greater than 0 representing the number of occurrence of `word` in the input files.

## Hint

To split a Java string into tokens you can use the following code, adapted as necessary:

```java
import java.util.StringTokenizer;
...
String line = "aaaa bbbb";
StringTokenizer itr = new StringTokenizer(line);
while (itr.hasMoreTokens()) {
    String word = it.nextToken();
    ...
}
```

## Extra

1. Modify your program to clean up the input files, removing all not alphabetic characters.
2. Add a combiner to the program:
    ```java
    job.setCombinerClass(NewReducer.class);
    ```

# Hadoop 3 and Python

[**Hadoop streaming**](https://hadoop.apache.org/docs/r1.2.1/streaming.html) is a utility that comes with the Hadoop distribution. The utility allows you to create and run Map/Reduce jobs with any executable or script as the mapper and/or the reducer.
Using this utility it is possible to write Python programs that run on Hadoop. We will use the [**mrjob**](https://github.com/Yelp/mrjob) module to write some simple Hadoop programs in Python.

## Setup

To install it, make sure you have a properly working Python setup on your **hadoop-namenode** machine.

```bash
$ sudo apt install python3
$ sudo update-alternatives --install /usr/bin/python python /usr/bin/python3 10
$ python --version
Python 3.6.9
$ python3 --version
Python 3.6.9
$ pip3 install mrjob
```

## Single-step Hadoop job

To test it, open your text editor and create a file called `word_count.py`:
```bash
$ nano word_count.py
```

The contents of this file will be as follows.
```python
"""
  The classic word count job: count the frequency of words.
"""
from mrjob.job import MRJob
import re

WORD_RE = re.compile(r"[\w']+")

class MRWordFreqCount(MRJob):

    def mapper(self, _, line):
        for word in WORD_RE.findall(line):
            yield (word.lower(), 1)

    def combiner(self, word, counts):
        yield (word, sum(counts))

    def reducer(self, word, counts):
        yield (word, sum(counts))

if __name__ == '__main__':
     MRWordFreqCount.run()
```

An Hadoop job is defined by a class that inherits from `MRJob`. This class contains methods that define the steps of your job.

A "step" consists of a mapper, a combiner, and a reducer. All of those are optional, though you must have at least one. So you could have a step that is just a mapper, or just a combiner and a reducer.

When you only have one step, all you have to do is write methods called `mapper()`, `combiner()`, and `reducer()`.

The `mapper()` method takes a key and a value as arguments (in this case, the key is ignored and a single line of text input is the value) and yields as many key-value pairs as it likes. The `reduce()` method takes a key and an iterator of values and also yields as many key-value pairs as it likes.

The final required component of a job file is these two lines at the end of the file, **every time**:
```python
if __name__ == '__main__':
    MRWordFreqCount.run()  # where MRWordFreqCount is your job class
```
These lines pass control over the command line arguments and execution to mrjob. **Without them, your job will not work**.


You can run this Python program locally, i.e., without Hadoop:
```bash
$ python word_count.py pg100.txt
```
The input file `pg100.txt` must be locally accessible, and you will receive the output to standard output. This execution mode is useful for debugging your Python code.

Alternatively, upload the `pg100.txt` file to HDFS and run the Python program on your Hadoop cluster:
```bash
$ hadoop fs -put pg100.txt
$ python word_count.py pg100.txt -r hadoop
```
The output, as well as a bunch of logging information, will be redirected to your standard output.

## Multi-step Hadoop job

To define **multiple steps**, override `steps()` to return a list of `MRStep`s.

Here's a job that finds the most commonly used word in the input:

```python
from mrjob.job import MRJob
from mrjob.step import MRStep
import re

WORD_RE = re.compile(r"[\w']+")

class MRMostUsedWord(MRJob):

    def steps(self):
        return [
            MRStep(mapper=self.mapper_get_words,
                   combiner=self.combiner_count_words,
                   reducer=self.reducer_count_words),
            MRStep(reducer=self.reducer_find_max_word)
        ]

    def mapper_get_words(self, _, line):
        # yield each word in the line
        for word in WORD_RE.findall(line):
            yield (word.lower(), 1)

    def combiner_count_words(self, word, counts):
        # optimization: sum the words we've seen so far
        yield (word, sum(counts))

    def reducer_count_words(self, word, counts):
        # send all (num_occurrences, word) pairs to the same reducer.
        # num_occurrences is so we can easily use Python's max() function.
        yield None, (sum(counts), word)

    # discard the key; it is just None
    def reducer_find_max_word(self, _, word_count_pairs):
        # each item of word_count_pairs is (count, word),
        # so yielding one results in key=counts, value=word
        yield max(word_count_pairs)

if __name__ == '__main__':
    MRMostUsedWord.run()
```

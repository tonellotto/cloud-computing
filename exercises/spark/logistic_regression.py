"""
A logistic regression implementation that uses NumPy (http://www.numpy.org)
to act on batches of input data using efficient matrix operations.
"""
import sys
from random import random
from operator import add
import numpy as np ### We need to install numpy on all nodes with sudo!!!!
from pyspark import SparkContext

D = 10  # Number of dimensions

def readPoint(line):
    return np.fromstring(line, dtype=np.float32, sep=' ')


if __name__ == "__main__":

    if len(sys.argv) != 3:
        print("Usage: logistic_regression <file> <iterations>", file=sys.stderr)
        sys.exit(-1)

    master = "yarn"
    sc = SparkContext(master, "LogisticRegression")

    points = sc.textFile(sys.argv[1]).filter(lambda line: len(line) > 0).map(readPoint).cache()
    iterations = int(sys.argv[2])

    # Initialize w to a random value
    w = 2 * np.random.ranf(size=D) - 1
    print("Initial w: " + str(w))
    def gradient(point):
        y = point[0]
        x = point[1:]
        # For each point (x, y), compute gradient function, then sum these up
        return ((1.0 / (1.0 + np.exp(-y * x.dot(w))) - 1.0) * y * x.T)

    def add(x, y):
        x += y
        return x

    for i in range(iterations):
        print("On iteration %i" % (i + 1))
        w -= points.map(lambda point: gradient(point)).reduce(add)

    print("Final w: " + str(w))

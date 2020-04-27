# Matrix Multiplication Exercise

Suppose we have a *p x q* matrix *M*, whose element in row *i* and column *j* will be denoted *m_ij* and a *q x r* matrix *N* whose element in row *j* and column *k* is donated by *n_jk*.
The product *P = MN* will be *p x r* matrix *P* whose element in row *i* and column *k* will be donated by *p_ik*, where *p_ik = m_ij x n_jk*.

![Matrix Multiplication Example](img/matrices.png)

## Matrix Data Model

We represent matrix *M* as a list of tuples *(i,j,m_ij)*, and matrix *N* as a list of *(j,k,n_jk)*. 
Most matrices are sparse so large amount of cells have value 0. 
When we represent matrices in this form, we do not need to keep entries for the cells that have values of zero to save 
large amount of disk space. As input data files, we store the matrices *M* and *N* on HDFS as two text files with the following format:

```
M, 0, 0, 10.0
M, 0, 2, 9.0
M, 0, 3, 9.0
M, 1, 0, 1.0
M, 1, 1, 3.0
M, 1, 2, 18.0
M, 1, 3, 25.2
....
```
```bash
N, 0, 0, 1.0
N, 0, 2, 3.0
N, 0, 4, 2.0
N, 1, 0, 2.0
N, 3, 2, -1.0
N, 3, 6, 4.0
N, 4, 6, 5.0
N, 4, 0, -1.0
....
```

where on each row we have:
* a string representing the matrix name;
* a row index value;
* a colum index value;
* the actual value stored in the matrix.

## Map-Reduce

We will write an Hadoop program to compute the matrix multiplication on the input files contents. The map and reduce functions we will implement are the following: 

![Matrix Multiplication Algorithm](img/algorithm.png)

The map function will produce *key, value* pairs from the input data as it is described in *Algorithm 1*. 
The reduce function uses the output of the map function and performs the calculations and produces *key,value* pairs as described in *Algorithm 2*. 

All outputs are written to HDFS as text files, using the same format as the input matrices.

## Data

The [rand_matrix.py](./rand_matrix.py) Python script (requires the SciPy package) can be used to generate a 100 x 1000 matrix *M* and a 1000 x 100 matrix *N* with sparsity level 0.25 and save them in the text files `M.txt` and `N.txt`.
Edit the script to modify the size of the matrices and to generate data to test your code.

Note that the Hadoop program must know the size of the input and output matrices. Configure the Hadoop job using the same values you decide to use in the Python script.

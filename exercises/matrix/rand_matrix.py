import scipy.sparse as sparse
import scipy.stats as stats
import numpy as np

# Matrix M of size I x J
# Matrix N of size J x K
I=100
J=1000
K=100

# set random seed to repeat
np.random.seed(42)

# create sparse matrix with density 0.25
M = 10 * sparse.random(I, J, density=0.25)

with open('M.txt','w') as f:
	for i, j, v in zip(M.row, M.col, M.data):
		f.write(f"M,{i},{j},{v:.2f}\n")

N = 10 * sparse.random(J, K, density=0.25)

with open('N.txt','w') as f:
        for i, j, v in zip(N.row, N.col, N.data):
                f.write(f"N,{i},{j},{v:.2f}\n")

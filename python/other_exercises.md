# Exercise 1

In this exercises, you will write a simple pocket calculator program. The program will be able to display the current value on the screen of the calculator. You will store the current value of the calculator in a variable. The initial value of the calculator (i.e., the initial current value) is 0.

1. Write code that displays the following: 
```
Welcome to the calculator program.
Current value: 0
```
2. Write a function whose signature is `display_current_value()`, and which displays the current value in the calculator. Test this function by calling it and observing the output.
3. Write a function whose signature is `add(to_add)`, and which adds `to_add` to the current value in the calculator, and modifies the current value accordingly. Test the function `add` by calling it, as well as by calling `display_current_value()`. Hint: when modifying global variables from within functions, declare them as **global**.
4. Write a function whose signature is `mult(to_mult)`, and which multiplies the current value in the calculator by `to_mult`, and modifies the current value accordingly. Test the function.
5. Write a function whose signature is `div(to_div)`, and which divides the current value in the calculator by `to_div`, and modifies the current value accordingly. Test the function. What values of `to_div` might cause problems?
6. Pocket calculators usually have a *memory* and a *recall* button. The memory button saves the current value and the recall button restores the saved value. Implement this functionality.
7. Implement a function that simulates the Undo button: the function restores the previous value that appeared on the screen before the current one.

### Solution
```python
def display_current_value():
    '''Display the current value'''
    print('Current value:', current_value)

def save_value():
    '''Save the current value for future use'''
    global prev_value
    prev_value = current_value

def add(to_add):
    '''Add to_add to the current value'''
    global current_value
    save_value()
    if current_value != "ERROR":
        current_value += to_add

def subtract(to_subtract):
    '''Subtract to_subtract from the current value'''
    global current_value
    save_value()
    if current_value != "ERROR":
        current_value -= to_subtract

def multiply(to_mult):
    '''Multiply the current value by to_mult'''
    global current_value
    save_value()
    if current_value != "ERROR":
        current_value *= to_mult

def divide(to_divide):
    '''Divide the current value by to_divide if to_divide != 0, set the current value to
      'ERROR' otherwise'''
    global current_value
    save_value()
    if current_value != "ERROR":
        if to_divide == 0:
            current_value = 'ERROR'
        else:
            current_value /= to_divide

def store():
    '''Emulate the memory button by storing the current value for future use
       Note: cannot be undone with the undo() button'''
    global mem_value
    mem_value = current_value

def recall():
    '''Emulate the recall button by retriving a stored memory value'''
    global current_value
    current_value = mem_value

def undo():
    '''Make the current value have the value it had before the last operation'''
    global current_value, prev_value
    current_value, prev_value = prev_value, current_value

def initialize():
    global current_value, prev_value, mem_value
    current_value = 0
    prev_value = 0
    mem_value = 0
    
######################################################################################################
# Test for add and subtract
######################################################################################################
initialize()
add(5)
display_current_value() # expected output: 0 + 5 = 5

current_value = 15
subtract(7)

display_current_value() # expected output: 15 - 7 = 8

# Other things to test for:
# - divide and multiply

######################################################################################################
# Test for interactions between the different functions
######################################################################################################
initialize()
add(5)
subtract(10)
multiply(2)

display_current_value() # expected output: (5 - 10) * 2 = -10

# Other things to test for:
# - undo() twice
# - use both undo() and recall()
# - recall() and then undo()
# - undo(), recall(), add(), ... all together (maybe do a couple tests like that)

######################################################################################################
# Test for adding negative number
######################################################################################################
initialize()
add(-5) # expected output: 0 - 5 = -5

######################################################################################################
# Test for an "irrational" number
######################################################################################################
import math
current_value = 42
divide(math.pi)
display_current_value() # expected value: approx. 13.36901521971921

######################################################################################################
# Test for boundary case: something that involves zero
######################################################################################################
initialize()
add(5)
subtract(5)
display_current_value() # expected: 5 - 5 = 0

# try adding zero:
current_value = 5
add(0) #expected output: 5 + 0 = 0    

######################################################################################################
# Test for error cases: try dividing by 0
######################################################################################################
current_value = 10
divide(0)
display_current_value() # expected output: ERROR

# produce an error, then try adding 10
current_value = 10
divide(0)
add(5) # expected output: you should decide

######################################################################################################
# Special cases where you might expect problems:
# - Divide by a very large number (so as to get 0), then multiply by the same number
# - add a very large number to another large number, see if there's a limit to the number of digits
# - divide 0 by 0
# - can you undo an "ERROR"?
# - can you store "ERROR"
# - try adding 5 to "ERROR"
######################################################################################################
```

# Exercise 2

The following is the Leibniz formula for <img src="https://latex.codecogs.com/svg.latex?\pi"/>:

<img src="https://latex.codecogs.com/svg.latex?\sum_{n=0}^\infty&space;\frac{(-1)^n}{2n&plus;1}&space;=&space;\frac{\pi}{4}" />

Write a program (using a `for` or a `while` loop) to compute:

<img src="https://latex.codecogs.com/svg.latex?\sum_{n=0}^{1000}&space;\frac{(-1)^n}{2n&plus;1}&space;=&space;\frac{\pi}{4}" />

and then print an approximation for <img src="https://latex.codecogs.com/svg.latex?\pi" /> using the result of the computation. 

### Solution
```python
def leibniz_pi(n_terms):
    s = 0
    for n in range(n_terms + 1):
        s += ((-1) ** n)/(2 * n + 1)
    return 4 * s
    
res = leibniz_pi(1000)
print("The approximation using terms up to n = 1000 is", res)
```

# Exercise 3

Write a function with the signature `simpify_fraction(n, m)` which prints the simplified version of the fraction *n/m*.

*Hint*: use a similar technique to the one we used when determining whether a number *m* is prime. You do not need to use a complicated algorithm to compute the greatest common divisor.

For example, `simplify_fraction(3, 6)` should print `1/2`, and `simplify_fraction(8, 4)` should print `2`.

```python
# try the largest possible divisor first
def simplify_fraction(n, m):
    factor = min(n, m)
    while factor >= 2:
        if n % factor == 0 and m % factor == 0:
            n, m = n // factor, m // factor
            break  # factor is the largest common divisor, so no need to proceed
        factor -= 1
    
    # Take care of the case where m == 1, so we don't need to display the whole fraction
    if m == 1:
        print(n)
    else:
        print(str(n) + "/" + str(m))
```

# Exercise 4

Write a function that returns the number of the terms required to obtain an approximation of <img src="https://latex.codecogs.com/svg.latex?\pi"/> using the Leibniz formula (exercise 2) that agrees with the actual value of <img src="https://latex.codecogs.com/svg.latex?\pi" /> to *n* significant digits (i.e., the first *n* digits are the same in <img src="https://latex.codecogs.com/svg.latex?\pi"/> and in the approximation.)

The best approximation of <img src="https://latex.codecogs.com/svg.latex?\pi"/> using a float is available in `math.pi` (execute `import math` to be able to use it.)

Part of your job is to figure out whether two numbers agree to *n* significant digits. To figure that out, for a float *x*, consider what `int(x * (10 ** n))` means.

```python
import math

def agree_to_n_sig_figs(x, y, n):
    '''
    Return True iff x and y agree to n significant figures  
    Arguments:
    x, y -- floats between 1 and 9.99999999
    '''
    return int(x * 10 ** (n - 1)) == int(y * 10 ** (n - 1))
    
def approx_pi(n_sig_fig):
    s = 0
    i = 0
    
    while not agree_to_n_sig_figs(4 * s, math.pi, n_sig_fig):
        s += ((-1) ** i)/(2 * i + 1)
        i += 1
    return i
```

# Exercise 5

You can use `str()` to convert objects to strings:
```
>> str(42) 
42
```
In particular, you can obtain the string representation of a list `list0` by using `str()`:
```
>> list0 = [1, 2, 3]
>> str(list0)
[1, 2, 3]
```
Without using `str()` with arguments that are lists (using it with arguments that are not lists is fine), write a function `list_to_str(lis)` which returns the string representation of the list `lis`. You may assume `lis` only contains integers.

Reminder:
```
>> "hello" + "python"
"hellopython"
```

### Solution

```python
def list_to_str(lis):
    '''
    Return the same output as str(lis) of a list of integers lis 
    '''
    s = '['                        # start with '[' and build up a solutionfrom there
    for i in range(len(lis) - 1):  # Don't include i = len(lis) - 1 becausewe need ']' and not ', ' after it
        s += (str(lis[i]) + ', ')  
    s += (lis[-1] + ']')
    return s 
```

# Exercise 6

You can compare lists using the `==` operator:
```
>> l1 = [1, 2, 3]
>> l2 = [4, 5, 6]
>> l3 = [1, 2, 3]
>> l1 == l2
False
>> l1 == l3
True
```
Without using the `==` operator to compare lists (you can still compare individual elements of the lists), write a function `lists_are_the_same(list1, list2)` which returns `True` iff `list1` and `list2` contain the same elements in the same order. You’ll need to use a loop (either `while` or `for`)

### Solution

```python  
def lists_are_the_same(list1, list2):
    '''
    Return True iff list1 and list2 have the same elements in the same order
    '''
    if len(list1) != len(list2):   # Take care of the easy case first
        return False
    
    i = 0
    while i < len(list1):
        if list1[i] != list2[i]:
            return False          # If even one pair differs, we can return False right away
        i += 1
    
    return True                   # Checked all the pairs, didn't return False so therefore must return True
```

# Exercise 7

Write a function with the signature `list1_start_with_list2(list1, list2)`, which returns `True` iff `list1` is at least as long as `list2`, and the first `len(list2)` elements of `list1` are the same as `list2`.

Note: `len(lis)` is the length of the list `lis`, i.e., the number of elements in `lis`.

First write the function without using slicing (slicing means saying things like `list1[2:5]`), and using a loop.

### Solution 1

```python
def list1_starts_with_list2(list1, list2):
    '''
    Return True iff the first len(list2) elements of list1 are the same, 
    and are in the same order as, list2
    '''
    if len(list1) < len(list2):  
        return False
    
    i = 0
    while i < len(list2):
        if list1[i] != list2[i]:
            return False       
        i += 1

    return True
```

### Solution 2

```python
def list1_starts_with_list2_noloops(list1, list2):
    '''
    Return True iff the first len(list2) elements of list1 are the same, 
    and are in the same order as, list2
    '''
    
    return list1[:len(list2)] == list2
    
    # Note: we didn't have to check the whether  list1 is longer than list2,
    # since list1[:len(list2)] happens to work even for large len(list2)
    # (if n > len(list1), list1[:n] is simply list1[:]
    
    # Longer (and worse) version that does the same thing:
    # if list1[:len(list2)] == list2
    #     return True
    # else:
    #     return False
```

# Exercise 8

Write a function with the signature `match_pattern(list1, list2)` which returns `True` iff the pattern `list2` appears in `list1`. In other words, we return `True` iff there is an `i` such that `0 ≤ i ≤ (list1) - len(list2)` and `list1[i] = list2[0]`, `list1[i + 1] = list2[1]`, ..., `list1[i + len(list2)  1] = list2[-1]`.

For example, if `list1` is `[4, 10, 2, 3, 50, 100]` and `list2` is `[2, 3, 50]`, `match_pattern(list1, list2)` returns `True` since the pattern `[2, 3, 50]` appears in `list1`.

### Solution

```python
def match_pattern(list1, list2):
    '''
    Return True iff list2 appears as a sublist of list1
    '''
    # Note: len(n) for n < 1 is just [], so we don't need to explicitly consider
    # the case that len(list2) > len(list1)

    for i in range(len(list1) - len(list2) + 1):
        if list1[i:(i + len(list2))] == list2:
            return True
    
    return False
```

# Exercise 9

Write a function with the signature `duplicates(list0)`, which returns `True` iff `list0` contains at least two adjacent elements with the same value.

### Solution

```python
def duplicates(list1):
    '''
    Return True iff list1 has two equal adjacent elements
    '''
    
    for i in range(len(list1) - 1):
        if list1[i] == list1[i + 1]:
            return True
    
    return False
```

# Exercise 10

In Python, you can use a list of lists to store a matrix, with each inner list representing a row. For example, you can store the matrix
􏰁

<img src="https://latex.codecogs.com/svg.latex?M&space;=&space;\begin{bmatrix}&space;5&space;&&space;6&space;&&space;7\\&space;0&space;&&space;-3&space;&&space;5&space;\end{bmatrix}" />


by storing each row as a list: `M = [[5, 6, 7], [0, -3, 5]]`. For ease of reading, since Python allows for line breaks inside brackets, you can write it as follows:

```python
M = [ [5, 6,7],
      [0, -3, 5] ]
```

You can use `M[1]` to access the second row of `M` (i.e., `[0, -3, 4]`), and you can use `M[1][2]` to access the third entry in the second row (i.e., `5`).

1. Write a function with the signature `print_matrix_dim(M)` which accepts a matrix `M` in the format above, and prints the matrix dimensions. For example, `print_matrix_dim([[1,2],[3,4],[5,6]])` should print `3 x 2`.
2. Write a function with the signature `mult_M_v(M, v)` which returns the product of a matrix `M` and a vector `v`. Vectors are stored as lists of floats. To write this function, you will need to create a new vector. Here are two ways to create a new vector (stored as a list) with 10 zeros in it:

```python
ten_zeros1 = [0] * 10
ten_zeros2 = []
for i in range(10):
    ten_zeros2.append(0)
```

### Solution 1
```python
def print_matrix_dim(M):
    '''
    Print the dimensions of matrix M, represented as a list of lists
    '''
    print(str(len(M)) + " x " + str(len(M[0])))
```


### Solution 2

```python
def dot_product(a, b):
    '''
    Return the dot product of the vectors a and b
    '''
   
    s = 0
    for i in range(len(a)):
        s += a[i] * b[i]
        
    return s

def mult_M_v(M, v):
    '''
    Return the product Mv of the matrix M and the vector v.
    '''
    
    prod = []
    for i in range(len(M)):
        prod.append(dot_product(M[i], v))
        
    return prod
```

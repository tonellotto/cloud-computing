# Python 3 and Jupyter Notes

We will use Python 3. Note that Python 2 is not compatible with Python 3.

## Python 3 Setup

Make sure that you have `python3` and `pip3` installed **on all your machines**.

```bash
$ sudo apt install python3
$ sudo apt install python3-pip
```
Create a `python` alias to the `python3` command **on all your machines**:

```bash
$ sudo update-alternatives --install /usr/bin/python python /usr/bin/python3 10
```
Now you can use `python` and `python3` commands interchangebly in any shell.
Also install the IPython shell.

```bash
$ sudo apt install ipython3
```

Check that everything is correctly installed.
```bash
$ python3 --version
Python 3.6.9
$ pip3 --version
pip 9.0.1 from /usr/lib/python3/dist-packages (python 3.6)
$ ipython3 --version
7.11.1
```
To start a Python interactive shell, the command `python3`:
```bash
$ python3
Python 3.6.9 (default, Nov  7 2019, 10:44:02)
[GCC 8.3.0] on linux
Type "help", "copyright", "credits" or "license" for more information.
>>>
```
To exit from a Python interactive shell, press `Ctrl + D` or use the `exit()` shell command.

## Jupyter Notebook Setup

[Jupyter Notebook](http://jupyter.org/) is an open-source web application that lets you create and share interactive code, visualizations, and more.

### Install

Install Jupyter with the local instance of pip, as follows.
```bash
$ pip3 install jupyter
```
That installed the executables into `~/.local/bin/`, which then needs to be added to the execution path.
```bash
$ export PATH=$PATH:~/.local/bin/
```
It's a good idea to add that to one of your startup scripts, probably `.bashrc`.<br>
At this point, you have successfully installed all the software needed to run Jupyter.

### Configure

The Jupyter notebook server can be run with a variety of command line options.
Defaults for these options can also be set by creating a file named `jupyter_notebook_config.py` in your Jupyter folder. The Jupyter folder is in your home directory, `~/.jupyter`.<br>

To create a `jupyter_notebook_config.py` file, with all the defaults commented out, you can use the following command line:
```bash
$ jupyter notebook --generate-config
```
By default, the Jupyter notebook server will run locally and listening on port 8888, i.e., you need to open locally a browser and use the address `http://localhost:8888` using a random session token provided from the command line.

Since on your virtual machine there is no browser, we will change some configuration options in the `jupyter_notebook_config.py` file to allow access to the Jupyter notebook server from your local machine without a token id.

To allow remote connections from all IP addresses, uncomment the option `c.NotebookApp.ip` in the `jupyter_notebook_config.py` file and set it to `'0.0.0.0'`.

In order to protect your Jupyter notebook server, you can set a login password with the following command:
```bash
$ jupyter notebook password
Enter password:
Verify password:
[NotebookPasswordApp] Wrote hashed password to /home/hadoop/.jupyter/jupyter_notebook_config.json
```

A hash of the password is stored in the file listed above. Restart the server and notice that the URL provided no longer contains a random session token to be used for login.

Now you can run the Jupyter notebook server on your virtual machine:
```bash
$ jupyter notebook --no-browser
```
and connect to it from your local browser using the following address
```
http://<vm machine IP address>:8888/
```
The notebook dashboard, which is the landing page with an overview of the notebooks in your working directory, is typically found and accessed at the default URL you just used.

In the non-root user's terminal, press `Ctrl + C` to stop the running Jupyter notebook server.

Navigate to the provided address to access your Jupyter notebook server. If you find yourself in the homepage without entering a password, click on the logout button and try again.

## Using Jupyter Notebooks

This section goes over the basics of using Jupyter Notebook. You should now be connected to it using a web browser.

Jupyter Notebook will show all of the files and folders in the directory it is run from, so when you are working on a project make sure to start it from the project directory.

To create a new Notebook file, select **New > Python 3** from the top right pull-down menu. This will open a **Python 3 notebook**. We can now run Python code in the cell or change the cell to _markdown_. For example, change the first cell to accept Markdown by clicking **Cell > Cell Type > Markdown** from the top navigation bar. We can now write notes using Markdown and even include equations written in LaTeX by putting them between the `$$`' symbols.

For example, type the following into the cell after changing it to markdown:
```
# First Equation

Let us now implement the following equation:
$$y = x^2$$

where $x = 2$
```
To turn the markdown into rich text, press `CTRL+ENTER`.

You can use the markdown cells to make notes and document your code. Let's implement that equation and print the result. Click on the top cell, then press `ALT+ENTER` to add a cell below it. Enter the following code in the new cell.
```python
x = 2
y = x**2
print(y)
```
To run the code, press `CTRL+ENTER`.

An open notebook has exactly one interactive session connected to a kernel which will execute code sent by the user and communicate back results. This kernel remains active if the web browser window is closed, and reopening the same notebook from the dashboard will reconnect the web application to the same kernel. This means that notebooks are an interface to kernel, the kernel executes your code and outputs back to you through the notebook. The kernel is essentially our programming language we wish to interface with.


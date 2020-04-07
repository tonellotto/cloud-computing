This folder contains some small datasets and utilities.

* The [snippets.zip](./snippets.zip) archive contains a bunch of multi-line text files, including the Bible and the collection of Shakespeare works.

* The [pg100.txt](./pg100.txt) file contains the *Complete Works of William Shakespeare* from Project Gutenberg retrieved at http://www.gutenberg.org/cache/epub/100/pg100.txt.

* The [soc-LiveJournal1Adj.txt](./soc-LiveJournal1Adj.txt) file contains the adjacency list and has multiple lines in the following format:
```
<User><TAB><Friends>
```
Here, `<User>` is a unique integer ID corresponding to a unique user and `<Friends>` is a comma separated list of unique IDs corresponding to the friends of the user with the unique ID `<User>`. Note that the friendships are mutual (i.e., edges are undirected): if A is friend with B then B is also friend with A. The data provided is consistent with that rule as there is an explicit entry for each side of each edge.

* The [`google_ngrams_downloader.sh`](./google_ngrams_downloader.sh) shell script allows to download the [Google Book Ngrams dataset](http://storage.googleapis.com/books/ngrams/books/datasetsv2.html) and load into HDFS. Please check it to download smaller datasets and to configure the HDFS destination.
	

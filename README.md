# rind

Using Clojure for naive bayes text classification. For fun!

It's called "rind" after the part of the fruit or vegetable that you don't want to eat.

## Usage

First, cd to this directory and run:

    ./getcorpus.sh

This downloads the Enron-Spam email corpus for spam detection, using the wget tool.

Now you should be able to run the program with

    lein run

This will train a Naive Bayes classifier on sections 1-4 of the Enron-Spam corpus,
and test on sections 5 and 6. It will use several feature extraction functions:

- bag-of-words
- bag-of-words (excluding stopwords, using nltk's stopword list)
- bigrams
- trigrams

It will print the accuracy and confusion matrix for each of these tasks.

## License

Copyright © 2015 David Ellison

Distributed under The MIT License (MIT).

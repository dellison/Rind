# rind

Using Clojure for naive bayes text classification. For fun!

It's called "rind" after the part of the fruit or vegetable that you don't want to eat.

## Usage

First, cd to this directory and run:

    ./getcorpus.sh

This downloads the
[Enron-Spam email corpus](http://www.aueb.gr/users/ion/data/enron-spam/)
for spam detection and the
[Cornell movie review corpus](http://www.cs.cornell.edu/people/pabo/movie-review-data/)
for sentiment classification
using the wget tool.

Now you should be able to run the program with

    lein run

This will train a Naive Bayes classifier, first on sections 1-4 of the
Enron-Spam corpus for testing on sections 5 and 6, and second on the
first 900 positive and 900 negative movie reviews, for testing on the
last 100 of each.

It will use several feature extraction functions:

- bag-of-words
- bag-of-words (excluding stopwords, using nltk's stopword list)
- bigrams
- trigrams

For each of these tasks, the accuracy and confusion matrix will be printed.

## Output
    Email classification with BOW features
    10975 / 11175 = 0.9821
    Confusion Matrix:
    	 :spam	:ham
    :spam	8078	97
    :ham	103	2897
    
    
    Email classification with BOW features (minus skipwords)
    10944 / 11175 = 0.9793
    Confusion Matrix:
    	 :spam	:ham
    :spam	8050	125
    :ham	106	2894
    
    
    Email classification with bigram features
    10866 / 11175 = 0.9723
    Confusion Matrix:
    	 :spam	:ham
    :spam	8156	19
    :ham	290	2710
    
    
    Email classification with trigram features
    9382 / 11175 = 0.8396
    Confusion Matrix:
    	 :spam	:ham
    :spam	8168	7
    :ham	1786	1214
    
    
    Movie review sentiment classification with BOW features
    165 / 200 = 0.8250
    Confusion Matrix:
    	 :pos	:neg
    :pos	75	25
    :neg	10	90
    
    
    Movie review sentiment classification with BOW features (minus skipwords)
    162 / 200 = 0.8100
    Confusion Matrix:
    	 :pos	:neg
    :pos	74	26
    :neg	12	88
    
    
    Movie review sentiment classification with bigram features
    148 / 200 = 0.7400
    Confusion Matrix:
    	 :pos	:neg
    :pos	49	51
    :neg	1	99
    
    
    Movie review sentiment classification with trigram features
    102 / 200 = 0.5100
    Confusion Matrix:
    	 :pos	:neg
    :pos	2	98
    :neg	0	100


## License

Copyright © 2015 David Ellison

Distributed under The MIT License (MIT).

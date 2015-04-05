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

## License

Copyright © 2015 David Ellison

Distributed under The MIT License (MIT).

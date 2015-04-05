(ns rind.core
  "A spam filter for email!
  Eat the fruit, but not the rind."
  (:gen-class)
  (:require [clojure.string :as str])
  (:require [clojure.java.io :as io])
  (:use rind.corpus)
  (:use rind.counter)
  (:use rind.eval))

(def training-data
  "The training data consists of the first four sections of the Enron email corpus.
  Represented as a list of (:label path/to/file) pairs."
  (for [section ["enron1" "enron2" "enron3" "enron4"]
        dir '("ham" "spam")
        fs (all-files-in-dir (.getPath (io/file (io/resource section) dir)))]
    [(keyword dir) fs]))

(def test-data
  "Test data consists of the fifth and sixth secition of the Enron email corpus.
  Represented as a list of (:label path/to/file) pairs. "
  (let [corpus-dir "/Users/david/projects/clojure/nlplol/resources/enronspam"]
    (for [section ["enron5" "enron6"]
          dir '("ham" "spam")
          fs (all-files-in-dir (.getPath (io/file (io/resource section) dir)))]
      [(keyword dir) fs])))

(def stopwords
  "NLTK's stopwords corpus."
  (set (tokens (io/resource "stopwords.txt"))))

(defn extract-features-bow
  "Get bag-of-words features from the file at path f."
  [f]
  (tokens f))

(defn extract-features-bow-skipwords
  "Get bag-of-words features (minus stopwords) from the file at path f."
  [f]
  (filter #(not (contains? stopwords %)) (tokens f)))

(defn extract-features-bigrams
  "Get bigram features from file at path f."
  [f]
  (ngrams 2 (tokens f)))

(defn extract-features-trigrams
  "Get trigram features from file at path f."
  [f]
  (ngrams 3 (tokens f)))

(defn p-c-f-add1
  "Probability of class given feature: P(C|W).
  Computed using the naive bayes assumption."
  [model cl feature]
  (let [c (get model cl)
        total-words (apply + (map #(get-total (second %)) model))
        pw (/ (inc (get-count c feature)) (inc total-words))      ;; P(feature)
        pc (/ (inc (get-total c)) (inc total-words))           ;; P(class)
        pwc (/ (inc (get-count c feature)) (inc (get-total c)))]  ;; P(feature|class)
    (/ (* pwc pc)
       pc)))

(defn train
  "Builds the model for naive bayes spam classification."
  [train-data extract-feat-fn]
  (apply merge-with merge-counters
         (for [[label file] train-data]
           {label (frequencies (extract-feat-fn file))})))

(defn classify
  "Classify an email using Naive Bayes probability estimation."
  [model [gold f] extract-feat-fn]
  (let [tokens (extract-feat-fn f)]
    (first
     (first 
      (sort-by second >
               (for [c (keys model)]
                 [c (apply + (map #(Math/log (p-c-f-add1 model c %)) tokens))]))))))

(defn pp-conf-matrix
  "Pretty-print the confusion matrix."
  [cm]
  (println "\t" (str/join "\t" (keys cm)))
  (println 
   (str/join "\n"
             (for [c (keys cm)]
               (str/join "\t" (list c
                                    (str/join "\t" (map #(get-in cm [c %] 0) (keys cm)))))))))

(defn pp-trial-results
  "Pretty print the results to the console.
  <#correct> / <total> = <accuracy>
  <confusion-matrix>"
  [results]
  (println (format "%d / %d = %.04f"
                   (:num-correct results)
                   (:num-total results)
                   (double (:accuracy results))))
  (println "Confusion Matrix:")
  (pp-conf-matrix (:confusion-matrix results))
  (println))

(defn run-nb-trial
  "End-to-end training and evaluation of Naive Bayes classification using the features
  extracted by feature extraction function fun."
  [fun]
  (let [model (train training-data fun)
        results (evaluate-classifier-fn #(classify model % fun) test-data)]
    (pp-trial-results results)))

(defn -main
  "Run me!"
  []
  (println "Email classification with BOW features")
  (run-nb-trial extract-features-bow)
  (println)

  (println "Email classification with BOW features (minus skipwords)")
  (run-nb-trial extract-features-bow-skipwords)
  (println)

  (println "Email classification with bigram features")
  (run-nb-trial extract-features-bigrams)
  (println)

  (println "Email classification with trigram features")
  (run-nb-trial extract-features-trigrams) 
  (println))

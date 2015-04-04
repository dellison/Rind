(ns rind.corpus
  "Utilities for reading a text corpus of email."
  (:require [clojure.java.io :as io]))

(defn all-files-in-dir
  "Returns a sequence of files (absolute path) in directory dir."
  [dir]
  (map #(.getAbsolutePath %)
       (filter #(.isFile %) (file-seq (io/file dir)))))

(defn tokens
  "Returns a sequence of tokens from file f"
  [f]
  (re-seq #"[A-Za-z0-9_\-]+" (slurp f)))

(defn ngrams
  "Returns a seq of ngrams of length n from tokens"
  [n tokens]
  (partition n 1 tokens))

(ns rind.eval
  "Functions for evaluating a classifier function.")

(defn count-true-positive
  "Returns the number of instances of class c
  that were classified correctly."
  [gold results c]
  (count (filter true? (map #(= c %1 %2) gold results))))

(defn count-false-negative
  "Returns the number of false negatives for class c
  in gold and actual results."
  [gold results c]
  (count (filter true? (map #(and (= c %1) (not= c %2)) gold results))))

(defn recall
  "Returns a map of {class: R ...} for each class
  where R is the recall for that class."
  [gold results]
  (let [classes (set gold)
        goldcnt (frequencies gold)
        rescnt (frequencies results)]
    (zipmap classes (map #(/ (count-true-positive gold results %)
                             (% goldcnt)) classes))))

(defn precision
  "Returns a map of {class: P ...} for each class
  where P is the precision for that class."
  [gold results]
  (let [classes (set gold)
        truepos (frequencies gold)]
    (zipmap classes (map #(/ (count-true-positive gold results %)
                             (get (frequencies results) % 1))
                         classes))))

(defn confusion-matrix
  "Produces a confusion matrix (in an odd format).
  Format is a sequence of [goldclass resultclass count]."
  [gold results]
  (let [classes (set gold)
        getcnt (fn [gld res] (count (filter true? (map #(and (= %1 gld) (= %2 res)) gold results))))]
    (apply merge-with merge
           (for [c1 classes c2 classes]
             {c1 {c2 (getcnt c1 c2)}}))))

(defn evaluate-classifier-fn
  "Returns a map of some evaluation metrics and other information
  for calling classifier-fn on the instances in test-data.
  test-data should be a sequence of [class features],
  where features is a sequence of feature observations."
  [classifier-fn test-data]
  (let [gold (map first test-data)
        ;; instances (map second test-data)
        ninstances (count test-data)
        results (map classifier-fn test-data)]
    {:num-correct (count (filter true? (map = gold results)))
     :num-total (count gold)
     :results-per-class (frequencies results)
     :gold-per-class (frequencies gold)
     :accuracy (/ (apply + (map #(if (= %1 %2) 1 0) gold results))
                  ninstances)
     :precision (precision gold results)
     :recall (recall gold results)
     :confusion-matrix (confusion-matrix gold results)
     }))

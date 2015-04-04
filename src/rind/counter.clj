(ns rind.counter
  "Counter protocol.
  A counter is a map of keys to counts with a default value of 0,
  and also caches the total.")

(defprotocol ICounter
  "Protocol for a Counter,
  which is a map with a default count of 0 for nonmembers"

  ;; iterating through a counter
  (all-counts [c] "map from keys to counts")

  ;; retrieving counts
  (get-count [c k] "count of key k in counter c")
  (get-total [c] "total of counted items (sum of values)")

  ;; updating counts
  (inc-count [c k] "update count of k by one")
  (add-count [c k v] "update count of k by v")
  )

(extend-protocol ICounter
  clojure.lang.IPersistentMap
  (all-counts [this] this)
  (get-count [this k] (get this k 0.0))
  (get-total [this] (apply + (vals this)))
  (inc-count [this k] (assoc this k (inc (get this k 0.0))))
  (add-count [this k v] (assoc this k (+ (get this k 0.0) v)))

  clojure.lang.ITransientMap
  (all-counts [this] this)
  (get-count [this k] (get this k 0.0))
  (get-total [this] (apply + (vals this)))
  (inc-count [this k] (assoc this k (inc (get this k 0.0))))
  (add-count [this k v] (assoc this k (+ (get this k 0.0) v))))

(deftype Counter [counts total]

  clojure.lang.Seqable
  (seq [this] (seq (.counts this)))

  clojure.lang.Counted
  (count [this] (count (.counts this)))

  clojure.lang.IFn
  (invoke [this k] (get (.counts this k 0.0)))

  java.lang.Object
  (toString [this] (str (.counts this)))
  (hashCode [this] (.hashCode (.counts this)))

  clojure.lang.IPersistentCollection

  clojure.lang.IEditableCollection
  (asTransient [this] (Counter. (transient counts) total))

  clojure.lang.ITransientCollection
  (persistent [this] (Counter. (persistent! counts) total))
  
  ICounter
  (all-counts [this] (.counts this))
  (get-total [this] (.total this))
  (get-count [this k] (get (.counts this) k 0.0))
  (inc-count [this k]
    (Counter.
     (assoc (.counts this) k (inc (get-count this k)))
     (inc (.total this))))
  (add-count [this k v]
    (Counter.
     (assoc (.counts this) k (+ v (get-count this k)))
     (+ (.total this) v))))


(defn counter
  "make a counter"
  ([] (Counter. {} 0.0))
  ([counts] (Counter. counts (reduce + (map second counts)))))

(defn merge-counters
  "returns a new counter from counters added together"
  [& counters]
  (Counter.
   (apply merge-with + (map all-counts counters))
   (apply + (map get-total counters))))

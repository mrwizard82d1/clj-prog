(ns ch-03.collection
  (use [ch-03.utils :only [print-eval-symbol]]))

(defn swap-pairs
  [a-seq]
  (into (empty a-seq)
        (interleave (take-nth 2 (drop 1 a-seq))
                    (take-nth 2 a-seq))))

(defn do-swap-pairs
  []
  (println (str "    "
                '(swap-pairs (apply list (range 10)))
                " = "
                (swap-pairs (apply list (range 10)))))
  (println (str "    "
                '(swap-pairs (apply vector (range 10)))
                " = "
                (swap-pairs (apply vector (range 10))))))

(defn map-map
  [f m]
  (into (empty m)
        (for [[k v] m]
          [k (f v)])))

(defn do-map-map
  []
  (println (str "    "
                '(map-map inc (hash-map :z 5 :c 6 :a 0))
                " = "
                (map-map inc (hash-map :z 5 :c 6 :a 0))))
  (println (str "    "
                '(map-map inc (sorted-map :z 5 :c 6 :a 0))
                " = "
                (map-map inc (sorted-map :z 5 :c 6 :a 0)))))

(defn do-count
  []
  (let [v [1 2 3]
        m {:a 1 :b 2}
        s #{1 2 3 4}
        l '(1)]
    (print-eval-symbol (count v))
    (print-eval-symbol (count m))
    (print-eval-symbol (count s))
    (print-eval-symbol (count l))))

(defn do-all
  []
  (println)
  (println "  Swap pairs")
  (do-swap-pairs)
  (println)
  (println "  Map-map")
  (do-map-map)
  (println)
  (println "  Count is pretty boring")
  (do-count))

(ns ch-03.sequences
  (use [clojure.pprint :only [pprint]])
  (require [ch-03.utils :as u]))

(def sequables ['(seq "Clojure")
                '(seq {:a 5 :b 6})
                '(seq (java.util.ArrayList. (range 5)))
                '(seq (into-array ["Clojure" "Programming"]))
                '(seq [])
                '(seq nil)])

(defn do-sequables
  []
  (doseq [s sequables]
    (u/do-print s)))

(def auto-seqs ['(map str "Clojure")
                '(set "Programming")])
(defn do-auto-seq
  []
  (doseq [s auto-seqs]
    (u/do-print s)))

(def fundamentals ['(first "Clojure")
                   '(rest "Clojure")
                   '(next "Clojure")])
(defn do-fundamentals
  []
  (doseq [s fundamentals]
    (u/do-print s)))

(def rest-v-next ['(rest [1])
                  '(next [1])
                  '(rest nil)
                  '(next nil)])
(defn do-rest-v-next
  []
  (doseq [s rest-v-next]
    (u/do-print s)))

(defn do-rest-next
  []
  (doseq [s ['(= (next [1]) (seq (rest [1])))
             '(= (next []) (seq (rest [])))
             '(= (next nil) (seq (rest nil)))]]
    (u/do-print s)))

(defn do-seq-not-iterator
  []
  (let [r (range 3)
        rst (rest r)]
    (print "    ")
    (pprint (map str rst))
    (print "    ")
    (pprint (map #(+ 100 %) r))
    (println (str "    "
              (conj r -1)
                  " "
              (conj rst 42)))))

(defn do-seq-not-list
  []
  (println "    Counting a seq is SLOW....")
  (let [s (range 1e6)]
    (time (count s)))
  (println "    ...but counting a list is very fast.")
  (let [s (apply list (range 1e6))]
    (time (count s))))

(defn do-all
  []
  (println)
  (println "  Sequable types")
  (do-sequables)
  (println)
  (println "  Function map and set wrap arguments in seq")
  (do-auto-seq)
  (println)
  (println "  Fundamental seq operations")
  (do-fundamentals)
  (println)
  (println "  Rest versus next")
  (do-rest-v-next)
  (println)
  (println "  Relationship between next and rest")
  (do-rest-next)
  (println)
  (println "  Seqs are not iterators")
  (do-seq-not-iterator)
  (println)
  (println "  Seqs are not lists")
  (do-seq-not-list))

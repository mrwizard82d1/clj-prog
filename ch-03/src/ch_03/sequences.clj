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

(defn do-creating-seqs
  []
  (println "    Using cons")
  (print "  ")
  (u/do-print '(cons 0 (range 1 5)))
  (println "    Cons always \"prepends\"")
  (print "  ")
  (u/do-print '(cons :a [:b :c :d]))
  (println "    Cons and list* are equivalent")
  (print "  ")
  (u/do-print '(cons 0 (cons 1 (cons 2 (cons 3 (range 4 10))))))
  (print "  ")
  (u/do-print '(list* 0 1 2 3 (range 4 10))))

(defn random-ints
  "Returns a lazy seq of random integers in the range [0, limit]."
  [limit]
  (lazy-seq (cons (rand-int limit)
                  (random-ints limit))))

(defn random-ints-pr
  [limit]
  (lazy-seq (println "Realizing random number")
            (cons (rand-int limit)
                  (random-ints-pr limit))))

(def rands (take 10 (random-ints-pr 50)))

(defn do-lazy_seqs
  []
  (println "    Silly example")
  (print "      ")
  (println '(lazy-seq [1 2 3]) " = " (lazy-seq [1 2 3]))
  (println "    Implementing a lazy sequence")
  (print "      ")
  (print (str '(take 10 (random-ints 50)) " = "))
  (print (take 10 (random-ints 50)))
  (println "    Realizing a lazy sequence")
  (println "      The first item")
  (println "      " (first rands))
  (println "      Next three items")
  (println "      " (nth rands 3))
  (println "      The remaining items")
  (println "      " (count rands))
  (println "    Once realized, no more realizations")
  (println "      " (count rands))
  (println "    A better version of random-ints")
  (print "      ")
  (print (str '(repeatedly 10 (partial rand-ints 50))) "= ")
  (println (repeatedly 10 (partial rand-int 50)))
  (println "    Be careful when realizing a lazy-seq")
  (println "      next forces realization of the head of the next")
  (let [x (next (random-ints-pr 50))])
  (println "      rest, on the other hand, will not realize the head of the rest")
  (let [x (rest (random-ints-pr 50))])
  (println "      Beware, sequential destructing uses next")
  (let [[x & rest] (random-ints-pr 50)])
  (println "      doall retains entire sequences as it is realized")
  (println "      but dorun disposes of each value as it is realized")
  (dorun (take 5 (random-ints-pr 50)))
  )

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
  (do-seq-not-list)
  (println)
  (println "  Creating seqs")
  (do-creating-seqs)
  (println)
  (println "  Lazy seqs")
  (do-lazy_seqs))

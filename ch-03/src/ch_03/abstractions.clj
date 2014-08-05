(ns ch-03.abstractions
  (require [ch-03.utils :as u]))

(defn do-vector-ops
  []
  (let [the-vector [1 2 3]]
    (u/print-eval-symbol the-vector)
    (u/print-eval-symbol (conj the-vector 4))
    (u/print-eval-symbol (conj the-vector 4 5))
    (u/print-eval-symbol (seq the-vector))))

(defn do-map-ops
  []
  (let [the-map {:a 5 :b 6}]
    (u/print-eval-symbol the-map)
    (u/print-eval-symbol (conj the-map {:c 7}))
    (u/print-eval-symbol (conj the-map {:c 7 :d 8}))
    (u/print-eval-symbol (seq the-map))))

(defn do-set-ops
  []
  (let [the-set #{1 2 3}]
    (u/print-eval-symbol the-set)
    (u/print-eval-symbol (conj the-set 10))
    (u/print-eval-symbol (conj the-set 3 4))
    (u/print-eval-symbol (seq the-set))))

(defn do-list-ops
  []
  (let [the-list '(1 2 3)]
    (u/print-eval-symbol the-list)
    (u/print-eval-symbol (conj the-list 0))
    (u/print-eval-symbol (conj the-list 0 -1))
    (u/print-eval-symbol (seq the-list))))

(defn do-into
  []
  (let [v [1 2 3]
        m {:a 5 :b 6}
        s #{1 2}
        l '(1)]
    (u/print-eval-symbol (into v [4 5]))
    (u/print-eval-symbol (into m [[:c 7] [:d 8]]))
    (u/print-eval-symbol (into s [2 3 4 5 3 3 2]))
    (u/print-eval-symbol (into l {:a 1 :b 2}))))

(defn do-all
  []
  (println)
  (println "  Vector operations")
  (do-vector-ops)
  (println)
  (println "  Map operations")
  (do-map-ops)
  (println)
  (println "  Set operations")
  (do-set-ops)
  (println)
  (println "  List operations")
  (do-list-ops)
  (println)
  (println "  Into is built atop conj and seq")
  (do-into))

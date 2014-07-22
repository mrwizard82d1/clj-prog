(ns ch-03.abstractions)

;; The function do-vector-ops contains a number of identical forms.
;; To avoid repeating myself, I created this macro to expand an
;; sexpr into the form. The problem I have is that any symbols
;; in the quoted sexpr were printed as symbols instead of being
;; evaluated. Because I had spent a couple of hours "fighting"
;; with how to eliminate this duplication, I opted to live with it.
(defmacro print-eval-symbol [sexpr]
  `(println (str "    " '~sexpr " = " ~sexpr)))

(defn do-vector-ops
  []
  (let [the-vector [1 2 3]]
    (print-eval-symbol the-vector)
    (print-eval-symbol (conj the-vector 4))
    (print-eval-symbol (conj the-vector 4 5))
    (print-eval-symbol (seq the-vector))))

(defn do-map-ops
  []
  (let [the-map {:a 5 :b 6}]
    (print-eval-symbol the-map)
    (print-eval-symbol (conj the-map {:c 7}))
    (print-eval-symbol (conj the-map {:c 7 :d 8}))
    (print-eval-symbol (seq the-map))))

(defn do-set-ops
  []
  (let [the-set #{1 2 3}]
    (print-eval-symbol the-set)
    (print-eval-symbol (conj the-set 10))
    (print-eval-symbol (conj the-set 3 4))
    (print-eval-symbol (seq the-set))))

(defn do-list-ops
  []
  (let [the-list '(1 2 3)]
    (print-eval-symbol the-list)
    (print-eval-symbol (conj the-list 0))
    (print-eval-symbol (conj the-list 0 -1))
    (print-eval-symbol (seq the-list))))

(defn do-into
  []
  (let [v [1 2 3]
        m {:a 5 :b 6}
        s #{1 2}
        l '(1)]
    (print-eval-symbol (into v [4 5]))
    (print-eval-symbol (into m [[:c 7] [:d 8]]))
    (print-eval-symbol (into s [2 3 4 5 3 3 2]))
    (print-eval-symbol (into l {:a 1 :b 2}))))

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

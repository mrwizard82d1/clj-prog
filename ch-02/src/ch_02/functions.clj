(ns ch-02.functions
  (require [clojure.string :as str]))

(defn call-twice
  [f x]
  (f x)
  (f x))

(defn do-call-twice
  []
  (call-twice println 123))

(defn do-call-lib-fs
  []
  (println (str "  max(5, 6) => " (max 5 6)))
  (println (str "  str/lower-case(\"Clojure\") => " (str/lower-case "Clojure"))))

(defn do-map
  []
  (println "  Mapping str/lower-case")
  (println (str "    " (vec (map str/lower-case
                                 ["Java" "Imperative" "Weeping"
                                  "Clojure" "Learning" "Peace"]))))
  (println "  Mapping *")
  (println (str "    "  (vec (map * [1 2 3 4] [5 6 7 8])))))

(defn do-reduce
  []
  (println "  Reduce using max")
  (println (str "    " (reduce max [0 -3 10 48])))
  (println "  Reduce using +")
  (println (str "    " (reduce + 50 [1 2 3 4])))
  (println "  Reduce vector to a map")
  (println (str "    " (reduce (fn [m v]
                                  (assoc m v (* v v)))
                                  {}
                                  [1 2 3 4]))))

(def args [2 -2 10])
(defn do-apply
  []
  (println "  Apply hash-map")
  (println (str "    " (apply hash-map [:a 5 :b 6])))
  (println "  Apply using defined args")
  (println (str "    " (apply * 0.5 3 args)))
  (println "  Define a partial function"))

(def only-strings (partial filter string?))
(defn do-partials
  []
  (println "  Apply a partial function")
  (println (str "    " (vec (only-strings ["a" 5 "b" 7])))))

(defn do-literals
  []
  (println "  Function literals allow specifying some arguments")
  (println (str "    " (vec (#(filter string? %) ["a" 5 :b "b"]))))
  (println "  Literals can specify arguments other than first")
  (println (str "    " (vec (#(filter % ["a" 5 :b "b"]) string?))))
  (println "  Exception if function literals specify no arguments")
  (try
    (#(map *) [1 2 3] [4 5 6] [7 8 9])
    (catch clojure.lang.ArityException e
      (println (str "    " e))
      (println "  Literals specify all arguments to functions we use")
      (println (str "    " (vec (#(map * % %2 %3) [1 2 3]
                                                  [4 5 6]
                                                  [7 8 9]))))))
  (println "  Exception if function literals specify some arguments")
  (try
    (#(map * % %2 %3) [1 2 3] [4 5 6])
    (catch clojure.lang.ArityException e
      (println (str "    " e))
      (println "  Literals can use rest args")
      (println (str "    " (vec (#(apply map * %&) [1 2 3]
                                                   [4 5 6]
                                                   [7 8 9]))))))
  (println "  Partial accomplishes same results as %&")
  (println (str "    " (vec ((partial map *) [1 2 3]
                                             [4 5 6]
                                             [7 8 9])))))

(defn do-all
  []
  (println)
  (println "Functions are first class citizens.")
  (do-call-twice)
  (println)
  (println "Calling library functions directly")
  (do-call-lib-fs)
  (println)
  (println "The workhorse: map")
  (do-map)
  (println)
  (println "Reduce: collection -> scalar")
  (do-reduce)
  (println)
  (println "Partial function applications (apply)")
  (do-apply)
  (println)
  (println "Partial functions")
  (do-partials)
  (println)
  (println "Partials versus function literals")
  (do-literals))

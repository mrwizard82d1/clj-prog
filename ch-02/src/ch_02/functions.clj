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
  (println (str "  " (vec (map str/lower-case
                               ["Java" "Imperative" "Weeping"
                                "Clojure" "Learning" "Peace"]))))
  (println "  Mapping *")
  (println (str "  "  (vec (map * [1 2 3 4] [5 6 7 8])))))

(defn do-reduce
  []
  (println "  Reduce using max")
  (println (str "  " (reduce max [0 -3 10 48])))
  (println "  Reduce using +")
  (println (str "  " (reduce + 50 [1 2 3 4])))
  (println "  Reduce vector to a map")
  (println (str "  " (reduce (fn [m v]
                               (assoc m v (* v v)))
                             {}
                             [1 2 3 4]))))

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
  (do-reduce))

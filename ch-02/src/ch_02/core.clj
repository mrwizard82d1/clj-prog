(ns ch-02.core
  (require [clojure.string :as str])
  (:gen-class))

(defn values
  []
  (println "Values")
  (doseq [x [true false 5 14.2 \T "hello" nil]]
    (println x)))

(defn do-something-with
  [n]
  (inc n))

(defn values-equality
  []
  (println)
  (println "Values equality")
  (println (str "(= 5 5) => " (= 5 5)))
  (println (str "(= 5 (+ 2 3)) => " (= (+ 2 3))))
  (println (str "(= \"boot\" (str \"bo\" \"ot\")) => "
                (= "boot" (str "bo" "ot"))))
  (println (str "(= nil nil) => " (= nil nil)))
  (println)
  (println "Function calls do not changes values")
  (println (let [a 5]
             (do-something-with a)
             (= a 5))))

(defn immutable-values
  []
  (println)
  (println "Immutable values")
  (doseq [[v change-f test-f] [["lorem" str/capitalize #(= %1 %2)]
                               [{[1 2] 3} #(conj (keys %1) 3) #(= %1 %2)]]]
    (println (str "Before: " v))
    (let [before v]
      (println (str "Changed: " (change-f v)))
      (println (str "Test: " (test-f before v))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (values)
  (values-equality)
  (immutable-values))

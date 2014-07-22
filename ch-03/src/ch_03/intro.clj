(ns ch-03.intro)

(defn do-all
  []
  (println)
  (println "Intro to collections and data structures.")
  (println (str "  "
                "list: "
                '(a b :name 12.5)))
  (println (str "  "
                "vector: "
                ['a 'b :name 12.5]))
  (println (str "  "
                "map: "
                {:name "Chas" :age 31}))
  (println (str "  "
                "set: "
                #{1 2 3}))
  (println (str "  "
                "Another (complex) map: "
                {Math/PI "~3.14"
                 [:composite "key"] 42
                 nil "nothing"}))
  (println (str "  "
                "A set of maps: "
                #{{:first-name "chas" :last-name "emerick"}
                  {:first-name "brian" :last-name "carper"}
                  {:first-name "christophe" :last-name "grand"}})))

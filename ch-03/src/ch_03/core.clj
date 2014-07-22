(ns ch-03.core
  (require [ch-03.intro :as intro]
           [ch-03.abstractions :as abstractions])
  (:gen-class))


(defn -main
  [& args]
  (intro/do-all)
  (abstractions/do-all))

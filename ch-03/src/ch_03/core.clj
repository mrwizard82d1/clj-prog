(ns ch-03.core
  (require [ch-03.intro :as intro]
           [ch-03.abstractions :as abstractions]
           [ch-03.collection :as collection]
           [ch-03.sequences :as sequences])
  (:gen-class))


(defn -main
  [& args]
  (intro/do-all)
  (abstractions/do-all)
  (collection/do-all)
  (sequences/do-all))

(ns ch-03.core
  (require [ch-03.intro :as intro]
           [ch-03.abstractions :as abstractions]
           [ch-03.collection :as collection])
  (:gen-class))


(defn -main
  [& args]
  (intro/do-all)
  (abstractions/do-all)
  (collection/do-all))

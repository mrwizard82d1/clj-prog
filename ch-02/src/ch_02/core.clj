(ns ch-02.core
  (require [ch-02.values :as values])
  (require [ch-02.functions :as functions])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (values/do-all)
  (functions/do-all))

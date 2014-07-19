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

(defn negated-sum-str-straightforward
  [& numbers]
  (str (- (apply + numbers))))

(def negated-sum-str (comp str - +))

(def camel->keyword (comp keyword
                          str/join
                          (partial interpose \-)
                          (partial map str/lower-case)
                          #(str/split % #"(?<=[a-z])(?=[A-Z])")))

(defn camel->keyword-threaded
  [s]
  (->> (str/split s #"(?<=[a-z])(?=[A-Z])")
       (map str/lower-case)
       (interpose \-)
       str/join
       keyword))

(def camel-pairs->map (comp (partial apply hash-map)
                            (partial map-indexed
                                     (fn [i x]
                                       (if (odd? i)
                                         x
                                         (camel->keyword x))))))

(defn do-composition
  []
  (println (str "  "
                "A \"straightforward\" implementation of"
                " negating a sum of numbers as a string."))
  (println (str "    " (negated-sum-str-straightforward 10 12 3.4)))
  (println (str "  "
                "The same function using comp. Remember that comp defines"
                " new top-level functions using def and NOT using defn."))
  (println (str "    " (negated-sum-str 10 12 3.4)))
  (println (str "  "
                "Reversing the order of functions in a composition"
                " often produces an error."))
  (try
    ((comp + - str) 10 12 3.4)
    (catch ClassCastException e
      (println (str "    " e))))
  (println (str "  "
                "A composed function to convert camel case words to Clojure keywords."))
  (println (str "    "
                (camel->keyword "CamelCase")
                "\n    "
                (camel->keyword "lowerCamelCase")))
  (println (str "  "
                "The difference between using comp and using threaded macros (-> and ->>)"
                " is often a matter of style."))
  (println (str "    "
                (camel->keyword-threaded "CamelCase")
                "\n    "
                (camel->keyword-threaded "lowerCamelCase")))
  (println (str "  "
                "Composed functions can be nested. We use camel->keyword as part of a"
                " larger composition : camel-pairs->map."))
  (println (str "    "
                (camel-pairs->map ["CamelCase" 5 "lowerCaseCamel" 3]))))

;; Returns a function that adds its argument to n.
(defn adder
  [n]
  (fn [x] (+ x n)))

;; Doubles the value of the function supplied (at a later time).
(defn doubler
  [f]
  (fn [& args]
    (* 2(apply f args))))

(def doubler-+ (doubler +))

(defn do-hofs
  []
  (println (str "  "
                "Sigh.... An adder."))
  (println (str "    "
                "((adder 5) 18) => "
                ((adder 5) 18)))
  (println (str "  "
                "Doubler doubles the value of the function passed in. The"
                " arguments are passed to the function in the SECOND call."))
  (println (str "    "
                "(doubler-+ 1 2 3) => ")
           (doubler-+ 1 2 3)))

;; Returns a functions that prints its argument to the writer supplied to this function.
(defn print-logger
  [writer]
  #(binding [*out* writer]
     (println %)))

;; A "silly" function that writes to standard out.
(def *out*-logger (print-logger *out*))

;; Logs arguments to a file.
(defn file-logger
  [file]
  #(with-open [f (clojure.java.io/writer file :append true)]
      ((print-logger f) %)))

(defn multi-logger
  [& loggers]
  #(doseq [l loggers]
     (l %)))

(defn timestamped-logger
  [logger]
  #(logger (format "[%1$tY-%1$tm-%1$te %1$tH:%1$tM:%1$tS] %2$s"
                   (java.util.Date.)
                   %)))

(defn do-logging
  []
  (println (str "  "
                "(Tortuously) Logging to standard out."))
  (*out*-logger (str "    " "hello"))
  (println (str "  "
                "Logging in-memory (to a StringWriter)."))
  (let [writer (java.io.StringWriter.)
        retained-logger (print-logger writer)]
    (retained-logger "hello")
    (println (str "  "
                  "And what did I just write?"))
    (println (str "    "
                  (str writer))))
  (println (str "  "
                "And printing to a file using the same approach."))
  (let [filename "messages.log"
        _ (clojure.java.io/delete-file filename true)
        messages-logger (file-logger filename)]
    (println (str "  "
                  "Logging to a file."))
    (messages-logger "hello")
    (println (str "  "
                  "Again, what did I log?"))
    (println (str "    "
                  (with-open [rdr (clojure.java.io/reader filename)]
                    (str/join "\n" (reduce conj [] (line-seq rdr)))))))
  (println (str "  "
                "All these loggers are nice, but multi-logger is a composite!"))
  (println (str "  "
                "Logging to standard output AND a file."))
  (let [filename "multi-messages.log"
        _ (clojure.java.io/delete-file filename true)
        messages-logger (file-logger filename)
        composite-logger (multi-logger *out*-logger messages-logger)]
    (composite-logger "hello")
    (println (str "  "
                  "I see standard output - but what about the file?"))
    (println (str "    "
                  (with-open [rdr (clojure.java.io/reader filename)]
                    (str/join "\n" (reduce conj [] (line-seq rdr)))))))
  (println (str "  "
                "And finally, a time-stamped logger."))
  (let [filename "stamped-messages.log"
        _ (clojure.java.io/delete-file filename true)
        messages-logger (file-logger filename)
        stamped-logger (timestamped-logger (multi-logger *out*-logger messages-logger))]
    (stamped-logger "hello")
    (println (str "  "
                  "I see timestamped standard output and a similar file."))
    (println (str "    "
                  (with-open [rdr (clojure.java.io/reader filename)]
                    (str/join "\n" (reduce conj [] (line-seq rdr))))))))

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
  (do-literals)
  (println)
  (println "Composition of functions")
  (do-composition)
  (println "Higher-order functions")
  (do-hofs)
  (println "Building a logging \"system\" using HOFs.")
  (do-logging))

(ns ch-01.core)

;; Example 1-2.

(defn average [numbers]
  (/ (apply + numbers) (count numbers)))

(average [60 80 100 400])

60

[60 80 100 400]

(average [60 80 100 400])

(+ 1 2)


(let [a 5]
  (inc 5))

(let [k nil]
  (not k))

(let [k 0]
  (not k))

(let [x 3, y 4]
  (/ (+ x y) 2))

(import 'java.util.List)

(let [a1 (java.util.ArrayList.)]
  (instance? java.util.List a1))

(Math/pow 2 10)

(.concat "To be or not to be" (.concat " That is" " the question"))

(read-string "42")

;; read/read-string returns a form / s-expr; that is, a list.
(read-string "(+ 1 2)")

;; The "dual" to read-string.
(pr-str [1 2 3])
(read-string (pr-str [1 2 3]))

"hello there"

"multiline strings
are very handy"

(let [s "multiline strings
are very handy"]
  s)

true
false
nil

(class \c)

;; Cannot query documentation of Java classes.
;; (doc java.lang.Character)

(str \u00ff)
(str \o41)

(str \space)
(str \newline)
(str \formfeed)
(str \return)
(str \backspace)


:name
:city

(def person {:name "Sandra Cruz"
             :city "Portland, ME"})

;; keywords are functions that accept maps as arguments
;; and look themselves up in the map
(:city person)
(:name person)

;; keywords may have "scope": the namespace in which they are defined.
(def pizza {:name "Ramunto's",
            :location "Claremont, NH"
            ::location "43.3734,-72.3365"})
(:name pizza)
(:location pizza)
(::location pizza)
(:user/location pizza)
(:ch-01/location pizza)
(:ch-01.core/location pizza)

pizza

;; keywords have a name AND a namespace
(name :ch-01.core/location)
(namespace :ch-01.core/location)

;; "global" keywords have nil for namespace
(namespace :location)

;; Remember must quote symbol or evaluating them returns their value!
(symbol? 'average)
(symbol? '+)

42
(integer? 0xff)
(integer? 2r111)

;; Clojure supports radices up to 36 (same as java.math.BigInteger radices)
(= 7 2r111)
(integer? 040)
(= 32 040)
(float? 3.14)
(float? 6.0221415e23)
(integer? 42N)
(instance? clojure.lang.BigInt 42N)
(instance? java.math.BigDecimal 0.01M)

;; Create ratios by numerator/denominator.
(instance? clojure.lang.Ratio 22/7)
;; Literal ratio CANNOT contain spaces
;; (instance? clojure.lang.Ratio 22 / 7)

(class #"(p|h)ail")
(re-seq #"(...) (...)" "foo bar")
(re-seq #"(\d+)-(\d+)" "1-3")

;; Form-level comments (supported by reader)
;; Skips entire forms no matter how complicated /deep
(read-string "(+ 1 2 #_(+ 2 2) 8)")

;; The comment macro returns nil (does not simply elide a form)
(comment (+ 1 2))

;; Commas are whitespace...
(= [1 2 3] [1, 2, 3])


;; ...mostly used to separate key-value pairs in maps
;; although most code puts pairs on different lines.
(= {:a 1, :b 2, :c, 3}
   {:a 1
    :b 2
    :c 3})

;; Collection literals
(list? '(a b :name 12.5))
(vector? ['a 'b :name 12.5])
(map? {:name "Chas" :age 31})
(set? #{'a 'b :name 12.5})

;; Miscellaneous reader sugar
(fn? #(+ %1 %2))

;; vars have values. values are NOT vars.
(def v :v)
(false? (var? v))
(true? (var? #'v))

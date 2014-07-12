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

(quote x)
'x
'(+ x x)
(list '+ 'x 'x)

;; Reader trick quote form to be read
''x
'@x
'#(+ % %)
'`(a b ~c) ; differs from lein repl. Don't know why.

;; REPL prints "hi" before printing 120.
(do
  (println "hi")
  (apply * [4 5 6]))

;; Again, the REPL prints the formatted text.
(let [a (inc (rand-int 6))
      b (inc (rand-int 6))]
  (println (format "You rolled a %s and a %s." a b))
  (+ a b))

(def p "foo")
p
(var p)

(defn hypot
  [x y]
  (let [x2 (* x x)
        y2 (* y y)]
    (Math/sqrt (+ x2 y2))))
(= 5.0 (hypot 3 4))

;; Sequential destructuring.
(def v [42 "foo" 99.2 [5 12]])

;; Various ways to access list items.
(first v)
(second v)
(last v)
(nth v 2)
(nth v 0)
(nth v -1)
(v 2)
(.get v 2)

(+ (first v) (v 2))
(+ (first v) (first (last v)))

;; Sequential Destructuring.
(= (let [[x y z] v]
    (+ x z))
   141.2)

(= (let [[x _ _ [y z]] v]
    (+ x y z))
   59)

(rest v) ;; a sequence.
(= (let [[x & rest] v]
     rest)
   (rest v))


(= (let [[x _ z :as original-vector] v]
     (conj original-vector (+ x z)))
   [42 "foo" 99.2 [5 12] 141.2])

;; Map destructuring.

(def m {:a 5
        :b 6
        :c [7 8 9]
        :d {:e 10 :f 11}
        "foo" 88
        42 false})

(= (let [{a :a
         b :b} m]
    (+ a b))
   11)

(= 100
   (let [{f "foo"} m]
     (+ f 12)))
(false? (let [{v 42} m] v))

;; One can apply map "destructuring" to vectors.
(= -17
   (let [{x 3 y 8} [12 0 0 -18 44 6 0 0 1]]
     (+ x y)))

(= 20
   (let [{{e :e} :d} m]
     (* 2 e)))

;; Combined map and sequence destructuring.
(= 16
   (let [{[x _ y] :c} m]
     (+ x y)))

(def map-in-vector ["James" {:birthday (java.util.Date. 73 1 6)}])
(not (false? (re-matches #"James was born on Tue Feb 06 00:00:00 [A-Z]{3} 1973"
       (let [[name {bd :birthday}] map-in-vector]
         (str name " was born on " bd)))))

(let [{r1 :x r2 :y :as randoms}
      (zipmap [:x :y :z] (repeatedly (partial rand-int 10)))
      result (assoc randoms :sum (+ r1 r2))]
  (or (= 4 (count result))
      (contains? :sum)
      (= (+ r1 r2) (:sum result))))


;; Default map destructuring values.
(= 55
   (let [{k :unknown
          x :a
          :or {k 50}} m]
     (+ k x)))

;; Binding values to keys names
;; We often bind values to names of keys. For example,
;; name is bound to result of applying :name and
;; age is bound to result of applying :age.
(def chas {:name "Chas" :age 31 :location "Massachusetts"})
(= "Chas is 31 years old and lives in Massachusetts."
   (let [{name :name
          age :age
          location :location} chas]
     (str name " is " age " years old and lives in " location ".")))

;; Since our map uses keywords for keys, we use the
;; :keys keyword to specify the keys whose values we map.
(= "Chas is 31 years old and lives in Massachusetts."
   (let [{:keys [name age location]} chas]
     (str name " is " age " years old and lives in " location ".")))

;; If the keys are string values, we use :strs.
(def brian {"name" "Brian"
            "age" 31
            "location" "British Columbia"})
(= "Brian is 31 years old and lives in British Columbia."
   (let [{:strs [name age location]} brian]
     (str name " is " age " years old and lives in " location ".")))

;; Finally, if the keys are symbols, we use :syms.
(def christophe {'name "Christophe"
                 'age 33
                 'location "Rhone-Alpes"})
(= "Christophe is 33 years old and lives in Rhone-Alpes."
   (let [{:syms [name age location]} christophe]
     (format "%s is %s years old and lives in %s." name age location)))


;; Destructuring rest sequences as map key/value pairs.
(def user-info ["robert8990" 2011 :name "Bob" :city "Boston"])
(= "Bob is in Boston."
   (let [[username account & extra-info] user-info
         {:keys [name city]} extra-info]
     (format "%s is in %s." name city)))

;; Even simpler.
(= "Bob is in Boston."
   (let [[username account & {:keys [name city]}] user-info]
     (format "%s is in %s." name city)))




(ns ch-03.utils)

;; The function do-vector-ops contains a number of identical forms.
;; To avoid repeating myself, I created this macro to expand an
;; sexpr into the form. The problem I have is that any symbols
;; in the quoted sexpr were printed as symbols instead of being
;; evaluated. Because I had spent a couple of hours "fighting"
;; with how to eliminate this duplication, I opted to live with it.
(defmacro print-eval-symbol [sexpr]
  `(println (str "    " '~sexpr " = " ~sexpr)))

(defn do-print
  [sexpr]
  (println (str "    "
                sexpr
                " = "
                (let [v (eval sexpr)]
                  (if (not (nil? v))
                    v
                    "nil")))))

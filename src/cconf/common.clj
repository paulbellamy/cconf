(ns cconf.common)

(defn parse-value
  "Parse a value, or return true if the value is nil. Attempts to parse the value as a Clojure object, and if that fails returns a string."
  [value]
  (try
    (binding [*read-eval* false]
      (let [parsed (read-string value)]
        (if (symbol? parsed)
          value
          parsed)))
    (catch NullPointerException e value)
    (catch NumberFormatException e value)
    (catch RuntimeException e value)
    (catch Exception e nil)))
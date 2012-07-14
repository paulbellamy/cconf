(ns cconf.argv
  (:require [clojure.string :as string]))

(defn- merge-options
  "Recursively merge options based on their type"
  [val-in-result val-in-latter]
  (concat val-in-result val-in-latter))

(defn- parse-capture
  "Parse a capture value, or return true if the capture is nil. Attempts to parse the value as a Clojure object, and if that fails returns a string."
  [value]
  (try
    (binding [*read-eval* false]
      (let [parsed (read-string value)]
        (if (symbol? parsed)
          value
          parsed)))
    (catch NumberFormatException e value)
    (catch Exception e true)))

(defn- parse-boolean-group
  "Parse grouped booleans (-bgI) or with a trailing capture: (-bgh localhost)"
  [opt capture]
  (let [matches (nthrest (string/split opt #"") 2)
        keywords (map keyword matches)
        values (repeat true)
        result (apply hash-map (interleave keywords values))]
    (if (not (nil? capture))
      (let [last-key (keyword (re-find #".$" opt))
            last-value (parse-capture capture)]
        (assoc result last-key last-value))
      result)))

(defn- parse-long-boolean
  "Parse a long boolean (--bool)"
  [opt capture]
  (let [[_ no] (re-matches #"^--no-(.+)" opt)
        [_ name] (re-matches #"^--(.+)" opt)
        value (parse-capture capture)]
    (if no
      (assoc {} (keyword no) false)
      (assoc {} (keyword name) value))))

(defn- parse-bare
  "Parse a bare option into the result array"
  ([opt] (parse-bare opt nil))
  ([opt capture]
     (if capture
       {:_ [(parse-capture opt) (parse-capture capture)]}
       {:_ [(parse-capture opt)]})))

(defn- parse-option
  "Determine an option's type and parse it"
  [opt capture]
  (cond
   (re-matches #"^--.+" opt)    (parse-long-boolean opt capture)
   (re-matches #"^-[^-]+" opt)  (parse-boolean-group opt capture)
   :else                        (parse-bare opt capture)))  

(defn- parse-options
  "Parse an option off of argv, returning the result, and remainder"
  [[opt & argv]]
  (when opt
    (if (= opt "--")
      (map parse-bare argv)
      (if-let [[_ opt equals] (re-matches #"^(--.*)=(.*)$" opt)]
        (cons (parse-option opt equals)
              (parse-options argv))
        (if-let [matched (and (first argv) (re-matches #"^[^-].*" (first argv)))]
          (cons (parse-option opt matched)
                (parse-options (rest argv)))
          (cons (parse-option opt nil)
                (parse-options argv)))))))

(defn parse
  "Parse command line options"
  ([argv] (parse argv {}))
  ([argv options]
     (apply (partial merge-with merge-options) (parse-options argv))))
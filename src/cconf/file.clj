(ns cconf.file
  (:use [cconf.common])
  (:require [clojure.data.json :as json]))

(defn parse-json
  "Parse a json config file"
  [filename]
  (json/read-str (slurp filename) :key-fn keyword))

(defn parse-edn
  "Parse an EDN config file"
  [filename]
  (read-string (slurp filename)))

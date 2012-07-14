(ns cconf.env
  (:use [cconf.common])
  (:require [clojure.walk :as walk]))

(defn- parse-option
  "Parse a key-value pair"
  [[key value]]
  {(keyword key) (parse-value value)})

(defn parse
  "Parse the environment variables"
  [env]
  (apply merge (map parse-option env)))
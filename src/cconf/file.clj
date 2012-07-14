(ns cconf.file
  (:use [cconf.common])
  (:require [clojure.data.json :as json])
  (:import [java.io FileWriter]))

(defn parse
  "Parse a json config file"
  [filename]
  (json/read-json (slurp filename)))
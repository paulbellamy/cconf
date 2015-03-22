(ns cconf.core
  (:require [cconf.argv]
            [cconf.env]
            [cconf.file]))

(defn argv
  "Load argv data into the config"
  ([] (argv {}))
  ([config]
     (merge (cconf.argv/parse *command-line-args*) config)))

(defn env
  "Load environment variables into the config"
  ([] (env {}))
  ([config]
     (merge (cconf.env/parse (System/getenv)) config)))

(defn file
  "Load data from a json file into the config"
  ([filename] (file {} filename))
  ([config filename]
     (merge (cconf.file/parse filename) config)))

(defn defaults
  "Load any default values into the config"
  ([config default-values]
     (merge default-values config)))

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

(defn- read-file [config filename parse-fn]
  (merge (parse-fn filename) config))

(defn file
  "Load data from a json file into the config"
  ([filename] (read-file {} filename cconf.file/parse-json))
  ([config filename] (read-file config filename cconf.file/parse-json)))

(defn edn-file
  "Load data from a EDN file into the config"
  ([filename] (read-file {} filename cconf.file/parse-edn))
  ([config filename] (read-file config filename cconf.file/parse-edn)))

(defn defaults
  "Load any default values into the config"
  ([config default-values]
     (merge default-values config)))

(ns cconf.core
  (:require [cconf.argv]
            [cconf.env]
            [cconf.file]))

(defn argv
  "Load argv data into the config"
  ([] (argv {}))
  ([options] (argv {} options))
  ([config options]
     (merge (cconf.argv/parse *command-line-args*) config)))

(defn env
  "Load environment variables into the config"
  ([] (env {}))
  ([options] (env {} options))
  ([config options]
     (merge (cconf.env/parse (System/getenv)) config)))

(defn file
  "Load data from a json file into the config"
  ([filename] (file {} filename))
  ([config filename]
     (merge (cconf.file/parse filename) config)))

(defn defaults
  "Load any default values into the config"
  ([options] (defaults {} options))
  ([config options]
     (merge options config)))

(ns cconf.core
  (:require [cconf.argv]))

(defn argv
  "Load argv data into the config"
  ([] (argv {}))
  ([options] (argv {} options))
  ([config options] (cconf.argv/parse *command-line-args*)))

(defn env
  "Load environment variables into the config"
  ([] (env {}))
  ([options] (env {} options))
  ([config options] {}))

(defn file
  "Load data from a json file into the config"
  ([filename] (file {} filename))
  ([config filename] {}))

(defn defaults
  "Load any default values into the config"
  ([options] (defaults {} options))
  ([config options] {}))

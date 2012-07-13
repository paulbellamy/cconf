(ns cconf.core)

(defn argv
  "Load argv data into the config"
  ([options] (argv {} options))
  ([config options] {}))

(defn env
  "Load environment variables into the config"
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

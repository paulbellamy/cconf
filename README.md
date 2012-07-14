# cconf

Easy configuration for Clojure apps

## Install

```
[cconf "0.1.0-alpha"]
```

## Usage

```Clojure
(require 'cconf)

(def settings (-> (cconf/argv)                                    ;; Load command-line arguments    (highest priority)
                  (cconf/env)                                     ;; Load environment variables
                  (cconf/file "config.json")                      ;; Load options from config.json
                  (cconf/defaults {:database-host "localhost"}))) ;; Set some default options       (lowest priority)

;; Get some settings. The 'settings' object is just a map.
(:database-host settings)
(:database-port settings)
```

## License

Copyright © 2012 Paul Bellamy

Distributed under the MIT License.

# cconf

Easy configuration for Clojure apps

## Install

```
[cconf "0.1.0-alpha"]
```

## Usage

```Clojure
(require 'cconf)

(def settings (-> (cconf/argv)                  ;; Load command-line arguments    (highest priority)
                  (cconf/env)                   ;; Load environment variables
                  (cconf/file "config.json")    ;; Load options from config.json
                  (cconf/defaults {:port 80}))) ;; Set some default options       (lowest priority)

;; Get some settings. The 'settings' object is just a map.
(:database-host settings)
(:database-port settings)
```

Options are merged with priority for the first listed, so in the above example we can override the default port (80) by either: passing ```--port 3000``` to the command line, setting ```port=4000``` in the environment, or by having a ```"port":5000``` value in our ```config.json``` file. The first value found is the one which will be used. If we passed in all three options the port will be 3000, because ```cconf/argv``` is listed first.

## ```cconf/argv``` Command-Line Arguments

There is no custom configuration for cconf's command-line arg parsing, yet. However, cconf automatically supports:

<table>
  <tr>
    <th></th><th>Usage</th><th>Becomes</th>
  </tr>
  <tr>
    <td>booleans</td><td>```-b``` or ```--debug```</td><td>```{:b true, :debug true}```</td>
  </tr>
  <tr>
    <td>grouped booleans</td><td>```-avz```</td><td>```{:a true, :v true, :z true}```</td>
  </tr>
  <tr>
    <td>capturing params</td><td>```-h localhost``` or ```--host localhost```</td><td>```{:h "localhost", :host "localhost"}```</td>
  </tr>
  <tr>
    <td>auto-parsing numbers</td><td>```-p 80``` or ```--port 80```</td><td>```{:p 80, :port 80}```</td>
  </tr>
  <tr>
    <td>double-dash</td><td>```-p 80 -- other args```</td><td>```{:p 80, :_ ["other" "args"]}```</td>
  </tr>
  <tr>
    <td>clojure objects</td><td>```--a-map {:a 1, :b 2}```</td><td>```{:a-map {:a 1, :b 2}}```</td>
  </tr>
  <tr>
    <td>negating booleans</td><td>```--no-debug``` or ```--debug false```</td><td>```{:debug false}```</td>
  </tr>
</table>

## ```cconf/env``` Environment Variables

Environment variables are not downcased, or otherwise renamed. For example, ```HOME=/Users/paulbellamy``` will be parsed as ```{:HOME "/Users/paulbellamy"}```. The values will be parsed where possible, so values like ```22/7```, ```{:a 1, :b 2}```, ```true```, ```false```, etc... will be parsed into their Clojure counterparts. If parsing a value is not possible it will be kept as a string.

## ```cconf/file``` JSON Config Files

JSON files are parsed with the clojure.data.json library.

## ```cconf/defaults``` Default Options

Defaults takes a map of options and fills in any missing options with their defaults.

If you wish to override some specific settings (maybe for debugging, or development), you can put a ```cconf/defaults``` source first in your list, with your desired overrides. For example

```Clojure
(def settings (-> (cconf/defaults {:port 3000}) ;; Override 'port' to *always* be 3000
                  (cconf/argv)))                ;; Load other command-line arguments

(:port settings) ;; Even if we call our app with
                 ;; ```our_app.clj --port 80```
                 ;; the port will still equal
                 ;; 3000, because we've given
                 ;; the defaults a higher priority
```

## License

Copyright © 2012 Paul Bellamy

Distributed under the MIT License.

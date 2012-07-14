(ns cconf.argv-test
  (:use clojure.test
        cconf.argv))

(deftest short-boolean
  (testing "parsing a short boolean"
    (let [result (parse ["-b"])]
      (is (= (:b result) true))))
  (testing "grouped booleans"
    (let [result (parse ["-bgI"])]
      (is (= (:b result)
             (:g result)
             (:I result)
             true))))
  (testing "remainder should be empty"
    (let [result (parse ["-b"])]
      (is (empty? (:_ result))))))

(deftest long-boolean
  (let [result (parse ["--bool"])]
    (testing "parsing a long-form boolean flag"
      (is (= (:bool result) true)))
    (testing "remainder should be empty"
      (is (empty? (:_ result))))))

(deftest bare
  (let [result (parse ["foo" "bar" "baz"])]
    (testing "bare arguments go into the remainder"
      (is (= (:_ result) ["foo" "bar" "baz"])))))

(deftest short-capture
  (let [result (parse ["-h" "localhost"])]
    (testing "-h localhost"
      (is (= (:h result) "localhost")))
    (testing "remainder is empty"
      (is (empty? (:_ result))))))

(deftest short-captures
  (let [result (parse ["-h" "localhost" "-p" "555"])]
    (testing "-h localhost -p 555"
      (is (= (:h result) "localhost"))
      (is (= (:p result) 555)))
    (testing "remainder is empty"
      (is (empty? (:_ result))))))

(deftest short-boolean-group-with-capture
  (let [result (parse ["-cats" "meow"])]
    (testing "-cats meow"
      (is (= (:c result) true))
      (is (= (:a result) true))
      (is (= (:t result) true))
      (is (= (:s result) "meow")))
    (testing "remainder is empty"
      (is (empty? (:_ result))))))

(deftest long-capture-split
  (let [result (parse ["--never" "give-you-up"])]
    (testing "capturing long split args"
      (is (= (:never result) "give-you-up")))
    (testing "remainder is empty"
      (is (empty? (:_ result))))))

(deftest long-capture-equals
  (let [result (parse ["--never=give-you-up"])]
    (testing "capturing long args with equals"
      (is (= (:never result) "give-you-up")))
    (testing "remainder is empty"
      (is (empty? (:_ result))))))

(deftest mixed-short-and-long-and-captures
  (let [result (parse ["-h" "localhost"
                       "--long" "oh-boy"
                       "-bg" "123"
                       "file.clj"])]
    (testing "captures everything"
      (is (= (:h result) "localhost"))
      (is (= (:long result) "oh-boy"))
      (is (= (:b result) true))
      (is (= (:g result) 123))
      (is (= (:_ result) ["file.clj"])))))

(deftest no
  (let [result (parse ["--no-fail"])]
    (testing "should be false if prefixed with 'no'"
      (is (= (:fail result) false))
      (is (= (:no-fail result) nil)))))

(deftest vectors
  (let [result (parse ["-a" "[1 2 3 4 5]"])]
    (testing "should parse vectors"
      (is (= (:a result) [1 2 3 4 5])))))

(deftest strings
  (let [result (parse ["-s" "a more complicated string"])]
    (testing "should parse strings with spaces"
      (is (= (:s result) "a more complicated string")))))

(deftest double-dash
  (let [result (parse ["-b" "--" "-d" "ohai"])]
    (testing "should put everything after -- into :_"
      (is (= (:b result) true))
      (is (= (:_ result) ["-d" "ohai"])))))

(deftest explicit-booleans
  (let [result (parse ["-t" "true" "-f" "false"])]
    (testing "should capture the explicit true"
      (is (= (:t result) true)))
    (testing "should capture the explicit false"
      (is (= (:f result) false)))
    (testing "remainder should be empty"
      (is (empty? (:_ result))))))

(deftest safe-parsing
  (let [result (parse ["--eval" "#=(java.util.Date.)}"
                       "--map" "{:map \"of stuff\"}"
                       "--symbol" "fooey"])]
    (testing "trying to eval stuff in the capture"
      (is (= (:eval result) true)))
    (testing "loading in an object"
      (is (= (:map result) {:map "of stuff"})))
    (testing "raw symbols should stay strings"
      (is (string? (:symbol result)))
      (is (= (:symbol result) "fooey")))))

(deftest numbers
  (let [argv ["--int" "1234"
              "--float" "5.67"
              "--scientific" "1e7"
              "--tricky" "10f"
              "--hex" "0xdeadbeef"
              "--fraction" "22/7"
              "789"]
        result (parse argv)]
    (testing "parsing ints"
      (is (= (:int result) 1234)))
    (testing "parsing floats"
      (is (= (:float result) 5.67)))
    (testing "parsing scientific"
      (is (= (:scientific result) 1e7)))
    (testing "parsing tricky strings"
      (is (= (:tricky result) "10f")))
    (testing "parsing hex numbers"
      (is (= (:hex result) 0xdeadbeef)))
    (testing "parsing fractions"
      (is (= (:fraction result) 22/7)))
    (testing "parsing numbers in the remainder"
      (is (= (:_ result) [789])))))
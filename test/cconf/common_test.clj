(ns cconf.common-test
  (:use clojure.test
        cconf.common))

(deftest parse-value-test
  (testing "it should pass through nil"
    (is (nil? (parse-value nil))))
  (testing "it should not let us eval stuff"
    (is (= (parse-value "#=(java.util.Date.)}") "#=(java.util.Date.)}")))
  (testing "it should parse booleans"
    (is (= (parse-value "true") true))
    (is (= (parse-value "false") false)))
  (testing "it should parse numbers"
    (is (= (parse-value "1234") 1234))
    (is (= (parse-value "5.67") 5.67))
    (is (= (parse-value "1e7") 1e7))
    (is (= (parse-value "10f") "10f"))
    (is (= (parse-value "0xdeadbeef") 0xdeadbeef))
    (is (= (parse-value "22/7") 22/7))
    (is (= (parse-value "789") 789)))
  (testing "it should keep symbols as strings"
    (is (string? (parse-value "fooey")))
    (is (= (parse-value "fooey") "fooey")))
  (testing "it should parse maps"
    (is (= (parse-value "{:map \"of stuff\"}") {:map "of stuff"})))
  (testing "it should parse paths as strings"
    (is (= (parse-value "/Users/paulbellamy") "/Users/paulbellamy"))))
(ns cconf.core-test
  (:use clojure.test
        cconf.core))

(deftest "loading configs"
  (testing "argv"
    (is (= 0 1))))

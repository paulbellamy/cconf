(ns cconf.core-test
  (:use clojure.test
        cconf.core))

(deftest loading-individual-configs
  (testing "argv"
    (is (= 0 1)))
  (testing "env"
    (is (= 0 1)))
  (testing "file"
    (is (= 0 1)))
  (testing "defaults"
    (is (= 0 1))))

(deftest merging-configs
  (testing "non-conflicting keys"
    (is (= 0 1)))
  (testing "conflicting keys"
    (is (= 0 1)))
  (testing "deeply merging hashes"
    (is (= 0 1))))

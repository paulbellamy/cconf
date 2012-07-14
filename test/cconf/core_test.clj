(ns cconf.core-test
  (:use clojure.test
        cconf.core))

(deftest loading-individual-configs
  (testing "arg (no options)"
    (binding [*command-line-args* ["-b" "--port" "80"]]
      (is (= (argv) {:b true, :port 80}))))
  (testing "env (no options)"
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
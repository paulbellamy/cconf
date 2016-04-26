(ns cconf.file-test
  (:use clojure.test
        cconf.file))

(deftest parsing-a-json-file
  (let [result (parse-json "./test/cconf/config.json")]
    (testing "parsing a boolean"
      (is (= (:bool result) true)))
    (testing "parsing a string"
      (is (= (:stringy result) "a string")))
    (testing "parsing a vector"
      (is (= (:vector result) [1 2 3 4 5])))))

(deftest parsing-an-edn-file
  (let [result (parse-edn "./test/cconf/config.edn")]
    (testing "parsing a boolean"
      (is (= (:bool result) true)))
    (testing "parsing a string"
      (is (= (:stringy result) "a string")))
    (testing "parsing a vector"
      (is (= (:vector result) [1 2 3 4 5])))))


(deftest failing
  (testing "Failing to find a file should throw"
    (try
      (parse-json "not-a-file.json")
      (is false)
      (catch java.io.FileNotFoundException e
        (is true)))))

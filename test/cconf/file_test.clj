(ns cconf.file-test
  (:use clojure.test
        cconf.file))

(deftest parsing-a-file
  (let [result (parse "./test/cconf/config.json")]
    (testing "parsing a boolean"
      (is (= (:bool result) true)))
    (testing "parsing a string"
      (is (= (:stringy result) "a string")))
    (testing "parsing a vector"
      (is (= (:vector result) [1 2 3 4 5])))))

(deftest failing
  (testing "Failing to find a file should throw"
    (try
      (parse "not-a-file.json")
      (is false)
      (catch java.io.FileNotFoundException e
        (is true)))))

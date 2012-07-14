(ns cconf.env-test
  (:use clojure.test
        cconf.env))

(defn make-env
  [x]
  (java.util.Collections/unmodifiableMap x))

(deftest boolean-variables
  (testing "parsing a boolean"
    (let [result (parse (make-env {"ON" "true"}))]
      (is (= result {:ON true})))))

(deftest string-variables
  (testing "parsing a string"
    (let [result (parse (make-env {"stringy" "a string"}))]
      (is (= (result {:stringy "a string"}))))))

(deftest vector-variables
  (testing "parsing a vector"
    (let [result (parse (make-env {"vector" "[1 2 3 4 5]"}))]
      (is (= (result {:vector [1 2 3 4 5]}))))))

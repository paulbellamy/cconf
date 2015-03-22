(ns cconf.core-test
  (:use clojure.test
        cconf.core))

(deftest loading-individual-configs
  (testing "arg (no options)"
    (binding [*command-line-args* ["-b" "--port" "80"]]
      (is (= (argv) {:b true, :port 80}))))
  (testing "env (no options)"     ;; System/getenv is hard to stub, so
    (is (string? (:HOME (env))))) ;; we just check it gets *something*
  (testing "file"
    (is (= (file "./test/cconf/config.json") {:bool true
                                              :vector [1 2 3 4 5]
                                              :stringy "a string"})))
  (testing "defaults"
    (is (= (defaults {} {:a true :b "hi"}) {:a true :b "hi"}))))

(deftest merging-configs
  (testing "non-conflicting keys"
    (let [config (-> {:a "hi"}
                     (defaults {:b "bye"}))]
      (is (= config {:a "hi" :b "bye"}))))
  (testing "conflicting keys"
    (let [config (-> {:a "hi"}
                     (defaults {:a "bye"}))]
    (is (= config {:a "hi"}))))
  (testing "no deeply merging hashes"
    (let [config (-> {:a {:b "hi"}}
                     (defaults {:a {:c "bye"}}))]
    (is (= config {:a {:b "hi"}})))))

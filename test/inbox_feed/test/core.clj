(ns inbox-feed.test.core
  (:use [inbox-feed.core])
  (:use [clojure.test]))

(deftest feed-test
  (let [f1 [{:id :id1 :title :title1}
            {:id :id2 :title :title2}
            {:id :id3 :title :title3}]
        f2 [{:id :id1 :title :title1 :published :d}
            {:id :id2 :title :title2 :published :d}
            {:id :id4 :title :title4 :published :d}]]
    (is (= [{:id :id3 :title :title3}] (diff-feed-entries f1 f2)))
    (is (= [{:id :id1 :title :title1}
            {:id :id2 :title :title2}
            {:id :id4 :title :title4}] (feed-state f2)))))

(deftest fixed-seq
  (is (= [2 3 4] (fixed-size-seq [] [2 3 4])))
  (is (= [1 2 3 4 5 6] (fixed-size-seq [1 2 3 4 5 6] [])))
  (is (= (concat [2 1] (range 0 498)) (fixed-size-seq (range 0 500) [1 2]))))


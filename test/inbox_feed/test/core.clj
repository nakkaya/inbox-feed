(ns inbox-feed.test.core
  (:use [inbox-feed.core])
  (:use [clojure.test]))

(setup-logging)

(defn- create-dummy-fs []
  (spit (java.io.File. "config.clj")
	(with-out-str
          (prn [:smtp-creds {:to "nurullah@nakkaya.com"}
                :feed-list [["http://news.ycombinator.com/rss" 15 "3cdbbd1e-5559-45d5-8dd6-d60408301580"]]]))))

(defn- delete-dummy-fs []
  (.delete (java.io.File. "config.clj"))
  (.delete (java.io.File. "feeds.data")))

(defn dummy-fs-fixture [f]
  (create-dummy-fs)
  (f)
  (delete-dummy-fs))

(use-fixtures :once dummy-fs-fixture)

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

(deftest no-send-test
  (let [config (prepare-config "config.clj")
        state (prepare-state "feeds.data")]
    (discard-feeds state config)
    (let [url (-> config :feed-list first first)
          old-state (state url)
          curr-state (feed-state (:entries (parse url)))]
      (is (= 0 (count (diff-feed-entries curr-state old-state)))))))

(defproject inbox-feed "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [com.draines/postal "1.7-SNAPSHOT"]
                 [org.clojars.nakkaya/rome "1.0"]
                 [org.clojars.nakkaya/jdom "1.1.2"]
                 [org.clojure/tools.cli "0.1.0"]
                 [org.clojure/tools.logging "0.2.3"]
                 [org.clojars.paraseba/hiccup "0.2.3"]]
  :jvm-opts ["-Dfile.encoding=UTF-8"]
  :main inbox-feed.core
  :jar-name "inbox-feed.jar"
  :uberjar-name "inbox-feed-app.jar")
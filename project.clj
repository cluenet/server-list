(defproject server-list "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :dependencies [[org.clojure/clojure "1.4.0"]
                           [ring "1.0.1"]
                           [compojure "1.0.1"]
                           [enlive "1.0.0"]
                           [ring/ring-jetty-adapter "1.0.1"]
                           [org.clojars.pntblnk/clj-ldap "0.0.9"]]
            :main server-list.server)


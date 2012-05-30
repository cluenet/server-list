(ns server-list.models.server
  (:require [clj-ldap.client :as ldap]
            [clojure.string :as str]))

(def ^:dynamic *ldap-server* (ldap/connect {:host "ldap.cluenet.org:636" :ssl? true}))

(defn- convert-values
  [server]
  (into {}
   (map
    (fn [[k v]]
      [(->> (name k)
            (re-seq #"[A-Z]?[a-z]+")
            (map str/lower-case)
            (str/join "-")
            keyword)
       (get {"TRUE" true "FALSE" false} v v)])
    server)))

(defn get-servers
  []
  (map convert-values (ldap/search *ldap-server* "ou=servers,dc=cluenet,dc=org" {:filter "(objectClass=server)"})))

(defn get-server
  [fqdn]
  (convert-values (ldap/get *ldap-server* (str "cn=" fqdn ",ou=servers,dc=cluenet,dc=org"))))
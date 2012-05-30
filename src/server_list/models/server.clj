(ns server-list.models.server
  (:require [clj-ldap.client :as ldap]))

(def ^:private ldap-server (ldap/connect {:host "ldap.cluenet.org:636" :ssl? true}))

(defn- convert-values
  [server]
  (into {}
   (map
    (fn [[k v]]
      (cond
       (= v "TRUE") [k true]
       (= v "FALSE") [k false]
       :else [k v]))
    server)))

(defn get-servers
  []
  (map convert-values (ldap/search ldap-server "ou=servers,dc=cluenet,dc=org" {:filter "(objectClass=server)"})))

(defn get-server
  [fqdn]
  (convert-values (ldap/get ldap-server (str "cn=" fqdn ",ou=servers,dc=cluenet,dc=org"))))
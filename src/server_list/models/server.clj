(ns server-list.models.server
  (:require [clj-ldap.client :as ldap]))

(def ^:private ldap-server (ldap/connect {:host "ldap.cluenet.org:636" :ssl? true}))


(defn get-servers
  []
  (ldap/search ldap-server "ou=servers,dc=cluenet,dc=org" {:filter "(objectClass=server)"}))

(defn get-server
  [fqdn]
  (ldap/get ldap-server (str "cn=" fqdn ",ou=servers,dc=cluenet,dc=org")))
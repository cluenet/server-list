(ns server-list.server
  (:use [compojure.core :only (GET defroutes)]
        [ring.adapter.jetty :only (run-jetty)]
        [ring.middleware.resource :only (wrap-resource)]
        [ring.middleware.reload :only (wrap-reload)])
  (:require compojure.route
            compojure.handler
            [server-list.models.server :as ldap]
            [server-list.views :as views]))

(defroutes app*
  (GET "/" request
       (views/servers-index
        (sort-by #(vec (map % [:is-active :user-accessible])) #(compare %2 %1) (ldap/get-servers))))
  (GET "/:cn" [cn] (views/servers-show (ldap/get-server cn))))

(def app (->
          (compojure.handler/api app*)
          (wrap-resource "public")
          (wrap-reload)))

(defn -main
  [port]
  (run-jetty app {:port (Integer. port)}))
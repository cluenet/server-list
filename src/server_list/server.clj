(ns server-list.server
  (:use [compojure.core :only (GET defroutes)]
        [ring.adapter.jetty :only (run-jetty)])
  (:require compojure.route
            compojure.handler
            [net.cgrand.enlive-html :as html]
            [server-list.models.server :as ldap]))

(defn ldap-true?
  [attr]
  (= attr "TRUE"))

(html/deftemplate index "templates/index.html"
  [servers]
  [:ul :li] (html/clone-for [x servers]
                            [:a]
                            (html/do->
                             (html/content (:cn x))
                             (html/set-attr :href (str "/" (:cn x))))))

(html/deftemplate show "templates/show.html"
  [server]
  [:h1] (html/content (:cn server))
  [:#description] (html/content (:description server))
  [:#is-official] (html/content (if (ldap-true? (:isOfficial server)) "Yes" "No"))
  [:#is-accessible] (html/content (if (ldap-true? (:userAccessible server)) "Yes" "No")))

(defroutes app*
  (GET "/" request (index (ldap/get-servers)))
  (GET "/:cn" [cn] (show (ldap/get-server cn))))

(def app (compojure.handler/api app*))

(defn -main
  [port]
  (run-jetty app {:port (Integer. port)}))
(ns server-list.views.servers
  (:require [server-list.models.server :as server])
  (:use [noir.core :only [defpage]]
        [hiccup.page :only [html5]]
        [hiccup.element :only [link-to unordered-list]]))

(defpage "/servers" []
  (html5
   [:h1 "ClueNet servers"]
   (unordered-list (for [x (server/get-servers)] (link-to (str "/servers/" (:cn x)) (:cn x))))))

(defpage "/servers/:server" {:keys [server]}
  (html5
   [:h1 server]
   (unordered-list
    (for [[k v] (server/get-server  server)]
      (list [:strong (str (name k) ": ")] v)))))
   
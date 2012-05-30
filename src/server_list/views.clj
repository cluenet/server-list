(ns server-list.views
  (:use hiccup.core
        hiccup.page
        hiccup.element)
  (:require [clojure.string :as str]))

(defn- bool->str
  [bool]
  (if bool "Yes" "No"))

(defn- header
  [title]
  (html
   [:div.page-header
    [:h1 title]]))

(defn layout
  [& content]
  (html5
   [:head
    [:meta {:charset "utf-8"}]
    [:title "ClueNet Server List"]
    [:meta {:name "viewport" :content "width=device-width; initial-scale=1.0"}]
    (include-css "/css/bootstrap.min.css"
                 "/css/bootstrap-responsive.min.css")]
   [:body
    [:div.container
     content]]))

(defn- info-label
  [content]
  (html
   "&nbsp;"
   [:span.label.label-info content]))

(defn- success-label
  [content]
  (html
   "&nbsp;"
   [:span.label.label-success content]))

(defn- servers-index-list-item
  [server]
  (html
   (link-to (:cn server) (:cn server))
   (when (:is-active server) (success-label "Online"))
   (when (:user-accessible server)(info-label "Public"))))

(defn servers-index
  [servers]
  (layout
   (header "ClueNet Server List")
   [:ul.breadcrumb
    [:li.active "Server List"]]
   (unordered-list
    (for [server servers] (servers-index-list-item server)))))

(defn- extract-uid
  [dn]
  (re-find #"(?<=uid=)[^,]+" dn))

(defn- field
  ([name value]
     (field value name value))
  ([test name value]
     (when test
       (html
        [:dt name]
        [:dd value]))))

(defn servers-show
  [server]
  (layout
   (header (:cn server))
   [:ul.breadcrumb
    [:li
     [:a {:href "/"} "Server List"]
     [:span.divider "/"]]
    [:li.active (:cn server)]]
   [:p#description (:description server)]
   [:dl.dl-horizontal
    (field "Owner" (extract-uid (:owner server)))
    (field (:authorized-administrator server)
           "Admins"
           (str/join ", " (map extract-uid (:authorized-administrator server))))
    (field "Official" (bool->str (:is-official server)))
    (field "User accessible" (bool->str (:user-accessible server)))
    (field "Purpose" (:purpose server))
    (field "RAM" (str (:ram-size server) " MB"))
    (field "HDD" (str (:hdd-size server) " GB"))
    (field "OS" (:operating-system server))
    (field "Network" (:network-lan server))
    (field "SSH port" (:ssh-port server))]))

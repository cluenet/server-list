(ns server-list.views
  (:use hiccup.core
        hiccup.page
        hiccup.element))

(defn- lbool->str
  [lbool]
  (if (= lbool "TRUE") "Yes" "No"))

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

(defn- red-label
  [content]
  (html
   "&nbsp;"
   [:span.label.label-important content]))

(defn- yellow-label
  [content]
  (html
   "&nbsp;"
   [:span.label.label-warning content]))

(defn- servers-index-list-item
  [server]
  (html
   (link-to (:cn server) (:cn server))
   (when (not= (:isActive server) "TRUE") (red-label "Offline"))
   (when (not= (:userAccessible server) "TRUE") (yellow-label "Private"))))

(defn servers-index
  [servers]
  (layout
   (header "ClueNet Server List")
   [:ul.breadcrumb
    [:li.active "Server List"]]
   (unordered-list
    (for [server servers] (servers-index-list-item server)))))

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
    [:dt "Is official"]
    [:dd (lbool->str (:isOfficial server))]

    [:dt "User accessible"]
    [:dd (lbool->str (:uerAccessible server))]]))
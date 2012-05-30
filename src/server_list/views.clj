(ns server-list.views
  (:use hiccup.core
        hiccup.page
        hiccup.element))

(defn- lbool->str
  [lbool]
  (if (= lbool "TRUE") "Yes" "No"))

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

(defn servers-index
  [servers]
  (layout
   [:div.page-header
    [:h1 "ClueNet Server List"]]
   [:ul.breadcrumb
    [:li.active "Server List"]]
   (unordered-list
    (map :cn servers))))

(defn servers-show
  [server]
  (layout
   [:div.page-header
    [:h1 (:cn server)]]
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
(ns server-list.views.welcome
  (:require [server-list.views.common :as common])
  (:use [noir.core :only [defpage]]
        [hiccup.page :only [html5]]))

(defpage "/welcome" []
         (common/layout
           [:p "Welcome to server-list"]))

(defpage "/my-page" []
  (html5
   [:h1 "This is my first page!"]))
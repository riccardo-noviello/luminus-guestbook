(ns guestbook.routes.home
  (:require [guestbook.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :refer [ok]]
            [clojure.java.io :as io]
            [guestbook.db.core :as db]
            [bouncer.core :as b]
            [bouncer.validators :as v]
            [ring.util.response :refer [redirect]]))


(defn home-page [{:keys [flash]}]
  (layout/render
   "home.html"
   (merge {:messages (db/run db/get-messages)}
          (select-keys flash [:name :message :errors]))))


(defn about-page []
  (layout/render "about.html"))

;function to validate the form parameters
(defn validate-message [params]
  (first
   (b/validate
    params
    :name v/required
    :message [v/required [v/min-count 10]])))

;function to validate and save messages
(defn save-message! [{:keys [params]}]
  (if-let [errors (validate-message params)]
    ;if the validate-message function returns error we redirect to "/"
    (-> (redirect "/")
        (assoc :flash (assoc params :errors errors)))
    ;otherise we save to the DB
    (do
      (db/run
       db/save-message!
       (assoc params :timestamp (java.util.Date.)))
      (redirect "/"))))

;pass the request to both the home-page and the save-message! handlers
(defroutes home-routes
  (GET "/" request (home-page request))
  (POST "/" request (save-message! request))
  (GET "/about" [] (about-page)))


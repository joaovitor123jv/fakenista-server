(ns fakenista-server.posts-handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-response wrap-json-params]]
            [ring.util.response :refer [response]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))


(defn response-post-users [params] 
  (if (get params "name")
    (response {:success "User created" 
               :user-name (get params "name")})
    (response {:error "User can not be created, missing user 'name'"})))

(defn response-post-levels [params] 
  (if (and 
        (get params "character")
        (get params "message")
        (get params "receiver"))
    (response {:success "Level created"
               :character (get params "character")
               :message (get params "message")
               :receiver (get params "receiver")})
    (response {:error "Level could not be created, missing data"})))

(ns fakenista-server.posts-handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [fakenista-server.database]
            [monger.collection :as mc]
            [ring.middleware.json :refer [wrap-json-response wrap-json-params]]
            [ring.util.response :refer [response]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))


(defn response-post-users [params]
  (if-let [[name username] [(get params "name") (get params "username")]]
    (let [db (fakenista-server.database/connect)]
      (if (mc/find-maps db :users)
        (response {:status "ERROR"
                   :error (str "O user " username " já está em uso")})
        (if-let [user (mc/insert-and-return
                        :users
                        {:name name
                         :created-at (java.time.LocalDateTime/now)
                         :username username})]
          (response {:id (get user :_id)
                     :status "success"})
          (response {:error "User can not be created, missing user 'name'"
                     :status "ERROR"}))))))


(defn response-post-levels [params]
  (let [character (get params "character")
        message   (get params "message")
        receiver  (get params "receiver")
        db (fakenista-server.database/connect)]
    (if (and character message receiver)
      (if-let [level-data (mc/insert-and-return db :levels {:character character
                                                            :message message
                                                            :receiver receiver})]
        (response {:status "success" :level level-data})
        (response {:status "error"   :error "Failed to create new level on database"}))
      (response {:status "success"
                 :error "Level could not be created. Must have 'character', 'message' and 'receiver'"}))))


(ns fakenista-server.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-response wrap-json-params]]
            [ring.util.response :refer [response]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))


(defroutes app-routes
  (GET "/levels"          []               (fakenista-server.gets-handler/response-levels))
  (GET "/level/:level-id" [level-id]       (fakenista-server.gets-handler/response-level level-id))
  (GET "/users"           []               (fakenista-server.gets-handler/response-users))
  (GET "/user/:user-id"   [user-id]        (fakenista-server.gets-handler/response-user user-id))
  (GET "/"                []               "Fakenista server\n")
  (GET "/ping"            []               "Pong\n")
  (POST "/users"          {:keys [params]} (fakenista-server.posts-handler/response-post-users params))
  (POST "/levels"         {:keys [params]} (fakenista-server.posts-handler/response-post-levels params))

  (route/not-found "Not Found\n"))

(def app
  (-> (wrap-json-response app-routes) wrap-json-params))


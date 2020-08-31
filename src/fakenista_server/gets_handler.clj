(ns fakenista-server.gets-handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [fakenista-server.database]
            [monger.collection :as mc]
            [monger.json]
            [ring.middleware.json :refer [wrap-json-response wrap-json-params]]
            [ring.util.response :refer [response]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
  (:import org.bson.types.ObjectId))

;; - GET:
;; 	- /levels-index
;; 		- Retorna o índice dos levels
;; 	- /level/1
;; 		- Retornar o conteúdo do level 1
;; 	- /users
;; 		- Retorna o índice dos usuários
;; 	- /user/1
;; 		- Retorna o conteúdo do usuário 1


(def level-data {
                 "1" {
                      :character "Dona Felizberta"
                      :receiver "Jão"
                      :message "Ô Jão, cê esqueceu de lavá a rôpa!"
                      }
                 "2" {
                      :character "Seu Itaquaquecetuba"
                      :receiver "rafiusky"
                      :message "Ow, divulga meu channel aí"
                      }
                 "3" {
                      :character "Rafiusky"
                      :receiver "oitavio"
                      :message "Cara, hoje eu acompanhei a melhor live possível!"
                      }
                 "4" {
                      :character "Blablabla"
                      :receiver "asjkdhajklsd"
                      :message "askdhjlashdjashjdlahjskldhjaklsdhjkals"
                      }})

(def available-levels (list 1 2 3 4))

(defn response-levels [] (let [db (fakenista-server.database/connect)]
                          (response (mc/find-maps db :levels))))


(defn response-level [level-id] (let [db (fakenista-server.database/connect)]
                                  (if-let [level-data (mc/find-map-by-id db :levels (ObjectId. level-id))]
                                    (response {:level-data level-data})
                                    (response {:error "This level does not exists"}))))


(defn response-users [] (let [db (fakenista-server.database/connect)]
                          (response (mc/find-maps db :users))))


(defn response-user [username] (let [db (fakenista-server.database/connect)]
                                 (if-let [user (mc/find-one-as-map db :users {:username username})]
                                   (response {:user user
                                              :status "success"})
                                   (response {:error "This user does not exists"
                                              :status "error"}))))








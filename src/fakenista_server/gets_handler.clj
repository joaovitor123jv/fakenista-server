(ns fakenista-server.gets-handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [fakenista-server.database :as db]
            [somnium.congomongo :as mongo]
            [ring.middleware.json :refer [wrap-json-response wrap-json-params]]
            [ring.util.response :refer [response]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

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

(defn response-levels [] (response {:levels available-levels}))
(defn response-level [level-id] (if (get level-data level-id)
                                  (response {:level-content (get level-data level-id)})
                                  (response {:error "This level does not exists"})))

(defn response-users [] (response 
                          (db/escape-bson 
                            (mongo/fetch :users))))

(defn response-user [username] (if-let [user (mongo/fetch-one :users :where { :username username })]
                                (response {:user (db/escape-bson user)})
                                (response {:error "This user does not exists"})))



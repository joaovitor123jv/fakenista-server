(ns fakenista-server.gets-handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
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



(def user-data {
                "42" {:name "João" :points 128}
                "43" {:name "Rafiusky" :points 243}
                "44" {:name "oitavio" :points 241}})

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
(def available-users (list 42 43 44))



(defn response-levels [] (response {:levels available-levels}))
(defn response-level [level-id] (if (get level-data level-id)
                                  (response {:level-content (get level-data level-id)})
                                  (response {:error "This level does not exists"})))

(defn response-users [] (response {:users available-users}))
(defn response-user [user-id] (if (get user-data user-id) 
                                (response {:user (get user-data user-id)})
                                (response {:error "This user does not exists"})))

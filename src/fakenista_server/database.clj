(ns fakenista-server.database
  (:require [compojure.core :refer :all]
            [clojure.walk]
            [somnium.congomongo :as mongo]))

(defn convert-ObjectId-to-string [[k v]]
  (if (= org.bson.types.ObjectId (class v))
    [k (str v)]
    [k v]))

(defn convert-all-ObjectId [m]
  (clojure.walk/postwalk
    (fn [m]
      (if (map? m)
        (into {} (map convert-ObjectId-to-string m))
        m))
    m))

(defn escape-bson [object] (convert-all-ObjectId object))


(def conn
  (mongo/make-connection "faketory"
                         :instances [{:host "127.0.0.1" :port 27017}]))


(defn connect! [] (mongo/set-connection! conn))

(defn insert! [collection document] (mongo/insert! collection document))


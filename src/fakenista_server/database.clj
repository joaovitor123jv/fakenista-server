(ns fakenista-server.database
  (:require [compojure.core :refer :all]
            [clojure.walk]
            [monger.core :as mg]
            [monger.collection :as mc]))

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

(defn connect [] (let [conn (mg/connect {:host "127.0.0.1" :port 27017})]
                   (mg/get-db conn "faketory")))


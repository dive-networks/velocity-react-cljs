(ns velocity-react-cljs.util
  (:require [clojure.string :as s]))

(defn dash-to-camel [dashed]
  (let [[first-word & other-words] (s/split (name dashed) #"-")]
    (apply str first-word (map s/capitalize other-words))))

(defn camelcase-map [m]
  (into
    {}
    (for [[k v] m]
      {(dash-to-camel k)
       (if (map? v)
         (camelcase-map v) v)})))

(defn throw-err [& message]
  (throw (js/Error. (str (s/join message)))))

(def error-messages
  {:collection-to-component "You provided a collection to velocity-react-cljs/motion-component, but it requires a single component. If you want to animate a list of components, use velocity-react-cljs/motion-group instead.\n Child component: "
   :config-nil-to-group "velocity-react-cljs/motion-group received a collection of components as the second parameter. Perhaps you forgot the config?"
   :config-nil-to-component "velocity-react-cljs/motion-component received a collection of components as the second parameter. Perhaps you forgot the config?"
   :nil-children-to-group "velocity-react-cljs/motion-group received a nil instead of a collection of components."
   :nil-children-to-component "velocity-react-cljs/motion-component received a nil instead of a component."
   :children-not-collection "velocity-react-cljs/motion-group requires a collection of components to work. If you want to animate a single component, use velocity-react-cljs/motion-component.\n Child component: "
   })

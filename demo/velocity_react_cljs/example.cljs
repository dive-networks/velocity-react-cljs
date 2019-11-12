(ns velocity-react-cljs.example
  (:require [velocity-react-cljs.components.toggle-box :refer [toggle-box]]
            [velocity-react-cljs.components.square-list :refer [square-list]]
            [velocity-react-cljs.components.custom-animation :refer [custom-animation]]
            [velocity-react-cljs.components.custom-transition :refer [custom-transition]]
            [reagent.core :as r]
            [goog.dom :as d]
            [velocity-react-cljs.core :as p]))


(enable-console-print!)

(def nav-items
  [{:id 0
    :title "Toggle box"}
   {:id 1
    :title "Square list"}
   {:id 2
    :title "Custom animation"}
   {:id 3
    :title "Custom transition"}])

(def views
  [{:id 0
    :view toggle-box}
   {:id 1
    :view square-list}
   {:id 2
    :view custom-animation}
   {:id 3
    :view custom-transition}])

(defn examples []
  (let [state (r/atom 0)]
    (fn []
      [:div.main
       [:h1.title "velocity-react-cljs Examples"]
       [:ul.nav
        (doall
         (for [{:keys [id title]} nav-items]
           [:li {:key id
                 :on-click #(reset! state id)
                 :class-name (if (= @state id)
                               "nav__item nav__item--active"
                               "nav__item")} title]))]
       [p/motion-group
        {:component "div"
         :class-name "content"
         :enter {:animation "transition.slideUpIn"
                 :delay 400
                 :duration 300}
         :leave {:animation "transition.slideUpOut"
                 :duration 300}}
        (doall
         (for [{:keys [id view]} views]
           (if (= @state id)
             ^{:key id}[view])))]])))

(defn mount! []
  (r/render
   [examples]
   (d/getElement "app")))

(mount!)

(def +version+ "0.1.0-1")
(def +project+ 'velocity-react-cljs)

(set-env!
 :resource-paths #{"src"}
 :dependencies '[[org.clojure/clojure "1.8.0" :scope "provided"]
                 [org.clojure/clojurescript "1.9.293" :scope "provided"]
                 [reagent "0.6.0" :exclusions [cljsjs/react]]
                 [adzerk/bootlaces "0.1.13"]])

(require
  '[adzerk.bootlaces :refer :all])

(bootlaces! +version+)

(task-options!
 pom {:project +project+
      :version +version+
      :url "https://github.com/dive-networks/velocity-react-cljs"
      :description "ClojureScript wrapper for using velocity-react in Reagent apps."
      :license {"MIT License" "https://opensource.org/licenses/MIT"}})

(deftask deploy-local []
  (comp (build-jar)
        (install)))

(deftask deploy []
  (comp (build-jar)
        (push-release)))

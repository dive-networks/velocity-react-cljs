(def +version+ "0.2.0")
(def +project+ 'velocity-react-cljs)

(set-env!
 :resource-paths #{"src" "static" "demo"}
 :dependencies '[;; Boot
                 [adzerk/boot-cljs "2.1.5" :scope "test"]
                 [adzerk/boot-cljs-repl "0.4.0" :scope "test"]
                 [adzerk/boot-reload "0.6.0" :scope "test"]
                 [pandeiro/boot-http "0.8.3" :scope "test"]
                 [adzerk/bootlaces "0.2.0" :scope "test"]

                 ;; REPL
                 [cider/cider-nrepl "0.22.4" :scope "test"]
                 [refactor-nrepl "2.4.0" :scope "test"]
                 [org.clojure/tools.nrepl "0.2.13" :scope "test"]
                 [cider/piggieback "0.4.2" :scope "test"]
                 [weasel "0.7.0" :scope "test"]

                 ;; Dev
                 [org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.520"]
                 [reagent "0.8.1" :exclusions [cljsjs/react]]
                 
                 [org.clojure/core.async "0.4.500"]])

(require
 '[adzerk.boot-cljs :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
 '[adzerk.boot-reload :refer [reload]]
 '[pandeiro.boot-http :refer [serve]]
 '[adzerk.bootlaces :refer :all])

(bootlaces! +version+)

(task-options!
 pom {:project +project+
      :version +version+
      :url "https://github.com/dive-networks/velocity-react-cljs"
      :description "ClojureScript wrapper for using velocity-react in Reagent apps."
      :license {"MIT License" "https://opensource.org/licenses/MIT"}})

(def react-transition-group '[[cljsjs/react-transition-group "4.3.0-0"]])

(def foreign-libs
  [{:file "lib/velocity-1.4.0.js"
    :file-min "lib/velocity-1.4.0.min.js"
    :provides ["velocity-animate"]}
   {:file "lib/velocity-react-1.4.3.js"
    :file-min "lib/velocity-react-1.4.3.min.js"
    :provides ["velocity-react"]}
   {:file "lib/velocity-ui-1.4.0.js"
    :file-min "lib/velocity-ui-1.4.0.min.js"
    :provides ["velocity-ui"]
    :requires ["velocity-animate"]}])

(deftask dev []
  (set-env! :source-paths #{"src" "demo"})
  (merge-env! :dependencies react-transition-group)
  (comp (serve :dir "static")
        (watch)
        (speak)
        (reload :on-jsload 'velocity-react-cljs.example/mount!)
        (cljs-repl)
        (cljs :compiler-options {:closure-defines {"goog.DEBUG" false}
                                 :source-map :true
                                 :optimizations :none
                                 :source-map-timestamp true
                                 :foreign-libs foreign-libs})))

(deftask version-file []
  (with-pre-wrap [fileset]
    (boot.util/info "Add version properties...\n")
    (-> fileset
        (add-resource (java.io.File. ".") :include #{#"^version\.properties$"})
        commit!)))

(deftask build-cljs []
  (merge-env! :dependencies react-transition-group)
  (cljs :optimizations :advanced
        :compiler-options {:foreign-libs foreign-libs
                           :externs ["externs/velocity_react.ext.js"]
                           :closure-warnings {:externs-validation :off}}))

(deftask demo []
  (merge-env! :source-paths #{"src" "demo"}
              :resource-paths #{"static"}
              :dependencies react-transition-group)
  (comp
   (serve :dir "static")
   (watch)
   (build-cljs)))

(deftask build []
  (merge-env! :source-paths #{"src"}
              :dependencies react-transition-group)
  (comp (version-file)
        (build-cljs)
        (target)))

(deftask deploy-local []
  (comp (build-jar)
        (install)))

(deftask deploy []
  (comp (build-jar)
        (push-release)))

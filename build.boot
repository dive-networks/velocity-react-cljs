(def +version+ "0.1.0-1")
(def +project+ 'velocity-react-cljs)

(set-env!
 :resource-paths #{"src" "static" "demo"}
 :dependencies '[;; Boot
                 [adzerk/boot-cljs "1.7.228-1" :scope "test"]
                 [adzerk/boot-cljs-repl "0.3.0" :scope "test"]
                 [adzerk/boot-reload "0.4.7" :scope "test"]
                 [pandeiro/boot-http "0.7.3" :scope "test"]
                 [degree9/boot-semver "1.2.4" :scope "test"]
                 [adzerk/bootlaces "0.1.13" :scope "test"]

                 ;; REPL
                 [cider/cider-nrepl "0.12.0-SNAPSHOT" :scope "test"]
                 [refactor-nrepl "2.3.0-SNAPSHOT" :scope "test"]
                 [org.clojure/tools.nrepl "0.2.12" :scope "test"]
                 [adzerk/boot-cljs-repl "0.3.0" :scope "test"]
                 [com.cemerick/piggieback "0.2.2-SNAPSHOT" :scope "test"]
                 [weasel "0.7.0" :scope "test"]

                 ;; Dev
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.8.40"]
                 [reagent "0.6.0-alpha" :exclusions [cljsjs/react]]
                 
                 [org.clojure/core.async "0.2.374"]])

(require
 '[adzerk.boot-cljs :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
 '[adzerk.boot-reload :refer [reload]]
 '[pandeiro.boot-http :refer [serve]]
 '[boot-semver.core :refer :all]
 '[adzerk.bootlaces :refer :all])

(bootlaces! +version+)

(task-options!
 pom {:project +project+
      :version +version+
      :url "https://github.com/dive-networks/velocity-react-cljs"
      :description "ClojureScript wrapper for using velocity-react in Reagent apps."
      :license {"MIT License" "https://opensource.org/licenses/MIT"}})

(def react-with-addons '[[cljsjs/react-with-addons "15.0.0-0"]])

(def foreign-libs
  [{:file "lib/velocity-1.3.2.min.js"
    :file-min "lib/velocity-1.3.2.min.js"
    :provides ["velocity-animate"]}
   {:file "lib/velocity-react-1.1.11.min.js"
    :file-min "lib/velocity-react-1.1.11.min.js"
    :provides ["velocity-react"]}
   {:file "lib/velocity-ui-1.3.2.min.js"
    :file-min "lib/velocity-ui-1.3.2.min.js"
    :provides ["velocity-ui"]
    :requires ["velocity-animate"]}])

(deftask dev []
  (set-env! :source-paths #{"src" "demo"})
  (merge-env! :dependencies react-with-addons)
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
  (merge-env! :dependencies react-with-addons)
  (cljs :optimizations :advanced
        :compiler-options {:foreign-libs foreign-libs
                           :externs ["externs/velocity_react.ext.js"]
                           :closure-warnings {:externs-validation :off}}))

(deftask demo []
  (merge-env! :source-paths #{"src" "demo"}
              :resource-paths #{"static"}
              :dependencies react-with-addons)
  (comp
   (serve :dir "static")
   (watch)
   (build-cljs)))

(deftask build []
  (merge-env! :source-paths #{"src"}
              :dependencies react-with-addons)
  (comp (version-file)
        (build-cljs)
        (target)))

(deftask deploy []
  (comp (build-jar)
        (push-release)))

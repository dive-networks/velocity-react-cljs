{:foreign-libs [{:file "lib/velocity-1.3.2.min.js"
                 :file-min "lib/velocity-1.3.2.min.js"
                 :provides ["velocity-animate"]}
                {:file "lib/velocity-react-1.1.11.min.js"
                 :file-min "lib/velocity-react-1.1.11.min.js"
                 :provides ["velocity-react"]}
                {:file "lib/velocity-ui-1.3.2.min.js"
                 :file-min "lib/velocity-ui-1.3.2.min.js"
                 :provides ["velocity-ui"]
                 :requires ["velocity-animate"]}]
 :externs ["externs/velocity_react.ext.js"]}

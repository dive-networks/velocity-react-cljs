# velocity-react-cljs

A ClojureScript wrapper for using velocity-react in Reagent apps. This is a fork of [velocity-react-cljs](http://clojars.org/velocity-react-cljs).

[![Clojars Project](http://clojars.org/velocity-react-cljs/latest-version.svg)](http://clojars.org/velocity-react-cljs)


## Demo

See at [http://dive-networks.github.io/velocity-react-cljs](http://dive-networks.github.io/velocity-react-cljs)


## Usage

First, some attention to your project dependencies. velocity-react-cljs depends on `cljsjs/react-with-addons` because of the `TransitionGroup` component. The developer is responsible to choose the React version needed to the project. One should exclude the `cljsjs/react` dependency from `reagent`, and add the proper `cljsjs/react-with-addons` version:

```clj
  [reagent "0.6.0" :exclusions [cljsjs/react]]
  [cljsjs/react-with-addons "15.3.1-0"]
  [velocity-react-cljs "0.1.0-SNAPSHOT"]
```

velocity-react-cljs has 2 components: `motion-component` and `motion-group`. Both accepts a map with configs as its first argument, and the child component our collection of components as its second argument.

### `motion-component`

If you need to animate one component, you should go with `motion-component`. It accepts the following properties:

* `animation`: Animation config
* `target-query-selector`: Used to animate a descendant of the child component.
* `run-on-mount`: Run the animation when the component is mounted.

Other properties like `duration`, `delay`, `loop` etc are passed directly to Velocity.js.

### `motion-group`

Wraps the `VelocityTransitionGroup` component. Useful when you want to apply transitions for items in a list when they are inserted and/or removed. You can apply the following properties to the wrapper:

* `enter`: Animation config for execution in the time that the component is mounted.
* `leave`: Same as `enter`, but for unmounting components.
* `run-on-mount`: Run the animation when the component is mounted.

As with `motion-component`, other properties like `component`, `class-name`, etc are passed directly as props to the `ReactTransitionGroup` component.


## Transitions

For the transitions, you have two options: Ready-to-use Velocity pre-registered UI Pack effects, or create your own.

If you go with the later, you can use a function `register-effect` provided by velocity-react-cljs. You can define a custom Velocity UI Pack effect to use with the `animation` property of both `motion-component` and `motion-group` components. You have some advantages over declaring your animations as style hashes, like animation chaining. You can get more information in the [Velocity UI Pack](http://julian.com/research/velocity/#uiPack) section of Velocity.js site.

But if the provided pre-registered animations are enough for you to achieve what you need, you can check the available effects [here](https://github.com/julianshapiro/velocity/blob/master/velocity.ui.js#L224).


## Credits

All credits go to the people from the [Velocity React](https://github.com/twitter-fabric/velocity-react) library for its amazing work.


## License

Original work Copyright © 2016 Andre Loureiro
Modified work Copyright © 2016 Dive Networks

Licensed under the MIT License: https://opensource.org/licenses/MIT

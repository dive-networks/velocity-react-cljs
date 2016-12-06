# velocity-react-cljs

A ClojureScript wrapper for using velocity-react in Reagent apps. This is a fork of [Phobos](https://github.com/andreloureiro/phobos).

[![Clojars Project](http://clojars.org/velocity-react-cljs/latest-version.svg)](http://clojars.org/velocity-react-cljs)

## Installing

The project has been configured to package the minified versions of the VelocityJS and velcity-react source files. You can change that behaviour by editing `src/deps.clj`. The foreign libs are generated in [dive-networks/velocity-react](https://github.com/dive-networks/velocity-react/) which is a fork of the original [velocity-react](https://github.com/twitter-fabric/velocity-react) project. We're using the fork in order to expose a `window.VelocityReact` object and to not include the React and ReactDOM source code in the compiled files. You can use any method you want to obtain the JavaScript dependencies. Just place them in `libs` and update the `src/deps.clj` file if the version numbers changed.

## Installing Locally

Run `boot deploy-local`

## Deploying to Clojars

Run `boot deploy`

## Usage

First, some attention to your project dependencies. velocity-react-cljs depends on `Reagent` and `cljsjs/react-with-addons` because of the `TransitionGroup` component. You're responsible for choosing the React version needed for your project. You should exclude the `cljsjs/react` dependency from `reagent`, and then add the proper `cljsjs/react-with-addons` version:

```clj
  [reagent "0.6.0" :exclusions [cljsjs/react]]
  [cljsjs/react-with-addons "15.3.1-0"]
  [velocity-react-cljs "0.1.0-0"]
```

velocity-react-cljs has 2 components: `motion-component` and `motion-group`. Both accept a config map the first argument, and a child component or collection of components as its second argument.

### `motion-component`

Component to add Velocity animations to a child node or one or more of its descendants. Wraps the `VelocityComponent` component. It accepts the following properties:

* `animation`: Velocity animation config. This is passed to Velocity as is. If this property changes, the component applies the new animation to the child on the next tick.
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

Credits go to the creators of [Velocity React](https://github.com/twitter-fabric/velocity-react) and to Andre Loureiro for creating  [Phobos](https://github.com/andreloureiro/phobos), the parent of this fork.

## License

Original work Copyright © 2016 Andre Loureiro
Modified work Copyright © 2016 Dive Networks

Licensed under the MIT License: https://opensource.org/licenses/MIT

# Image Show

My implementation of ImageShow app written in Kotlin without classic libraries like Glide/Picasso/Fresco.
The most significant dependencies are enumerated below:

- Gradle 5 + Kotlin DSL for build
- Architecture driven by Android Jetpack including ViewModels and LiveData
- Toothpick for Dependency Injection
- RxJava for performing IO on background and failing gracefully
- Retrofit for loading data
- [MaterialProgressBar](https://github.com/DreaminginCodeZH/MaterialProgressBar) which offers no-padding progress bar embeddable to Toolbar
- Unit tests employ [Spek2](https://spekframework.org), [mockk](https://mockk.io) and [Kluent](https://github.com/MarkusAmshove/Kluent). 

To be able to run tests, you need to install [Spek Plugin](https://plugins.jetbrains.com/plugin/10915-spek-framework/) compatible with Spek's version **2.0.5**.
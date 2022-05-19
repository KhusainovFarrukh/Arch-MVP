# Arch-MVP
Sample app for demonstrating MVP architecture in Android

This is sample project to demonstrate MVP (Model-View-Presenter) architechture pattern in Android.
Also project uses some popular libraries/tools in separate branches. For example:

1. kotlin-flow: Kotlin Flow is used to implement reactive programming
2. rx-java: RxJava and RxAndroid is used to implement reactive programming
3. non-reactive: This branch does not implement reactive programming, but uses Kotlin Coroutines' suspend functions
4. di-koin: Koin is used for DI (Dependency Injection). Other branches (kotlin-flow and rx-java) has Dagger Hilt used for DI.

WARNING! Unit tests in project needs refactoring

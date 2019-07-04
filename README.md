# Modular web application with Play and Scala

> An attempt to create a modular web application with Playframework.

The goal is to have a functional application where we can add or remove features by adding or removing them from the classpath.

## Usage

To add a new module, create a simple scala project inside this project and reference it to the build.sbt as a simple project that depends on "playCore". And make the web module depends on it (so that you can easily test everything and don't have to mess with the classpath).

Add the fully qualified class name of your module in "reference.conf\play.modules.enabled += "your.Module"" of your module itself.

Develop your modules as you want, adding a subclass of Router will automatically use those routes under your module name (module = "test", routes will be "/test/*").

To disable a momdule, remove it form the classpath and restart your application.
 

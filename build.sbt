import Dependencies._

ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "play-scala-multimodule"
  )
  .aggregate(
    domain,
    web
  )

lazy val domain = (project in file("domain"))
  .settings(
    libraryDependencies += scalaTest % Test
  )

lazy val web = (project in file("web"))
  .enablePlugins(PlayScala)
  .dependsOn(domain)
  .settings(
    libraryDependencies += scalaTest % Test,

    libraryDependencies += guice
  )





name := """sub-form-app"""

version := "1.0-SNAPSHOT"

//lazy val root = (project in file(".")).enablePlugins(PlayJava)

//追加分
lazy val myProject = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

//libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.36"

//libraryDependencies += "com.h2database" % "h2" % "1.4.192"

//追加分
libraryDependencies += "postgresql" % "postgresql" % "9.1-901-1.jdbc4"


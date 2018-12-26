name := """SubFormApp"""

version := "1.0-SNAPSHOT"

//warプラグイン用のパッケージ
import com.github.play2war.plugin._

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

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.36"

//warプラグインの導入用s
Play2WarPlugin.play2WarSettings

Play2WarKeys.servletVersion := "3.1"

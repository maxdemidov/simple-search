name := "simple-search"

version := "0.1"

scalaVersion := "2.12.11"

val scalaVers = "2.12.11"
val scalatestVers = "3.2.0"

libraryDependencies ++= Seq(

  "org.scala-lang" % "scala-compiler" % scalaVers,
  "org.scala-lang" % "scala-library" % scalaVers,
  "org.scala-lang" % "scala-reflect" % scalaVers,

  "org.scalatest" %% "scalatest" % scalatestVers % Test
)
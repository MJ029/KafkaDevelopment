name := "KafkaDevelopment"
version := "1.0"
scalaVersion := "2.11.8"

val sparkVersion = "2.2.1"
val hadoopVersion = "2.7.1"

conflictManager := ConflictManager.latestRevision
libraryDependencies ++= Seq(
  "com.google.guava" % "guava" % "18.0",
  "log4j" % "log4j" % "1.2.17",
  "org.slf4j" % "slf4j-simple" % "1.7.25" % Test,
  "org.slf4j" % "slf4j-api" % "1.7.25",
  "org.apache.kafka" %% "kafka" % "1.1.0",
  "com.twitter" % "hbc-core" % "2.2.0")

resolvers ++= Seq(
  "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases",
  "Typesafe Ivyrepository" at "https://repo.typesafe.com/typesafe/ivy-releases",
  "Sonatype snapshots" at "https://oss.repo.sonatype.org/content/repositories/snapshots",
  "Sonatype public" at "https://oss.sonatype.org/content/repositories/public",
  "Sonatype releases" at "https://oss.sonatype.org/content/repositories/releases",
  "Maven central" at "https://repo1.maven.org/maven2/",
  Resolver.sonatypeRepo("releases")
)
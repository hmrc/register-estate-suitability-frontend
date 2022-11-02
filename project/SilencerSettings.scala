import sbt.{CrossVersion, compilerPlugin}
import sbt.Keys._
import sbt._

object SilencerSettings {
  // stop "unused import" warnings from routes files
  def apply() = Seq(
    libraryDependencies ++= Seq(
      compilerPlugin("com.github.ghik" % "silencer-plugin" % "1.7.12" cross CrossVersion.full),
      "com.github.ghik" % "silencer-lib" % "1.7.12" % Provided cross CrossVersion.full
    ),
    // silence all warnings on autogenerated files
    scalacOptions += "-P:silencer:pathFilters=target/.*",
    // Make sure you only exclude warnings for the project directories, i.e. make builds reproducible
    scalacOptions += s"-P:silencer:sourceRoots=${baseDirectory.value.getCanonicalPath}"
  )
}

import sbt.*

object AppDependencies {

  private val boostrapVersion = "8.6.0"
  private val mongoVersion    = "1.9.0"

  private val compile = Seq(
    "uk.gov.hmrc.mongo" %% "hmrc-mongo-play-30"                    % mongoVersion,
    "uk.gov.hmrc"       %% "play-frontend-hmrc-play-30"            % "8.5.0",
    "uk.gov.hmrc"       %% "play-conditional-form-mapping-play-30" % "2.0.0",
    "uk.gov.hmrc"       %% "bootstrap-frontend-play-30"            % boostrapVersion
  )

  private val test           = Seq(
    "uk.gov.hmrc.mongo"   %% "hmrc-mongo-test-play-30" % mongoVersion,
    "org.jsoup"            % "jsoup"                   % "1.17.2",
    "org.mockito"         %% "mockito-scala-scalatest" % "1.17.31",
    "org.scalatestplus"   %% "scalacheck-1-17"         % "3.2.18.0",
    "uk.gov.hmrc"         %% "bootstrap-test-play-30"  % boostrapVersion
  ).map(_ % Test)

  def apply(): Seq[ModuleID] = compile ++ test

}

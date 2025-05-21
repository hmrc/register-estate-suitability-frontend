import sbt.*

object AppDependencies {

  private val boostrapVersion = "8.6.0"
  private val boostrapVersion = "9.12.0"
  private val mongoVersion    = "2.6.0"

  private val compile = Seq(
    "uk.gov.hmrc.mongo" %% "hmrc-mongo-play-30"                    % mongoVersion,
    "uk.gov.hmrc"       %% "play-frontend-hmrc-play-30"            % "9.11.0",
    "uk.gov.hmrc"       %% "play-frontend-hmrc-play-30"            % "12.1.0",
    "uk.gov.hmrc"       %% "play-conditional-form-mapping-play-30" % "3.3.0",
    "uk.gov.hmrc"       %% "bootstrap-frontend-play-30"            % boostrapVersion
  )

  private val test           = Seq(
    "uk.gov.hmrc.mongo"   %% "hmrc-mongo-test-play-30" % mongoVersion,
    "org.jsoup"            % "jsoup"                   % "1.20.1",
    "org.scalatestplus"   %% "scalacheck-1-17"         % "3.2.18.0",
    "uk.gov.hmrc"         %% "bootstrap-test-play-30"  % boostrapVersion
  ).map(_ % Test)

  def apply(): Seq[ModuleID] = compile ++ test

}

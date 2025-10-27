import sbt.*

object AppDependencies {

  private val boostrapVersion = "10.2.0"
  private val mongoVersion    = "2.10.0"

  private val compile = Seq(
    "uk.gov.hmrc.mongo" %% "hmrc-mongo-play-30"                    % mongoVersion,
    "uk.gov.hmrc"       %% "play-frontend-hmrc-play-30"            % "12.18.0",
    "uk.gov.hmrc"       %% "play-conditional-form-mapping-play-30" % "3.3.0",
    "uk.gov.hmrc"       %% "bootstrap-frontend-play-30"            % boostrapVersion
  )

  private val test           = Seq(
    "uk.gov.hmrc.mongo"   %% "hmrc-mongo-test-play-30" % mongoVersion,
    "org.scalatestplus"   %% "scalacheck-1-18"         % "3.2.19.0",
    "uk.gov.hmrc"         %% "bootstrap-test-play-30"  % boostrapVersion
  ).map(_ % Test)

  def apply(): Seq[ModuleID] = compile ++ test

}

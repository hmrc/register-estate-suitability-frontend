import sbt.*

object AppDependencies {

  val boostrapVersion = "7.21.0"
  val mongoVersion = "1.3.0"

  private val compile = Seq(
    "uk.gov.hmrc.mongo" %% "hmrc-mongo-play-28"             % mongoVersion,
    "uk.gov.hmrc"       %% "play-frontend-hmrc"             % "7.19.0-play-28",
    "uk.gov.hmrc"       %% "play-conditional-form-mapping"  % "1.13.0-play-28",
    "uk.gov.hmrc"       %% "bootstrap-frontend-play-28"     % boostrapVersion,
  )

  private val test = Seq(
    "uk.gov.hmrc.mongo"           %% "hmrc-mongo-test-play-28"  % mongoVersion,
    "org.scalatest"               %% "scalatest"                % "3.2.16",
    "org.jsoup"                   %  "jsoup"                    % "1.16.1",
    "org.mockito"                 %% "mockito-scala-scalatest"  % "1.17.22",
    "org.scalatestplus"           %% "scalacheck-1-17"          % "3.2.16.0",
    "org.wiremock"                %  "wiremock-standalone"      % "3.0.0",
    "com.vladsch.flexmark"        %  "flexmark-all"             % "0.64.8",
    "uk.gov.hmrc"                 %% "bootstrap-test-play-28"   % boostrapVersion,

  ).map(_ % Test)

  def apply(): Seq[ModuleID] = compile ++ test

}

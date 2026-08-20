/*
 * Copyright 2026 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package connectors

import base.SpecBase
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.{badRequest, get, okJson, urlEqualTo}
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import config.FrontendAppConfig
import generators.Generators
import org.mockito.Mockito.when
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, Inside}
import org.scalatestplus.mockito.MockitoSugar.mock
import play.api.http.Status.BAD_REQUEST
import play.api.mvc.RequestHeader
import play.api.test.FakeRequest
import play.api.{Application, inject}
import repositories.SessionRepository
import uk.gov.hmrc.http.{HeaderCarrier, UpstreamErrorResponse}

import scala.concurrent.ExecutionContext.Implicits.global

class RegisterEstateConnectorSpec
    extends SpecBase
    with Generators
    with ScalaFutures
    with Inside
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with IntegrationPatience {

  implicit lazy val hc: HeaderCarrier = HeaderCarrier()

  implicit val requestHeader: RequestHeader = FakeRequest("GET", "/")

  protected val server: WireMockServer = new WireMockServer(wireMockConfig().dynamicPort())

  override def beforeAll(): Unit = {
    server.start()
    super.beforeAll()
  }

  override def beforeEach(): Unit = {
    server.resetAll()
    super.beforeEach()
  }

  override def afterAll(): Unit = {
    super.afterAll()
    server.stop()
  }

  def getApplication() = {
    val mockConfig            = mock[FrontendAppConfig]
    val mockSessionRepository = mock[SessionRepository]
    when(mockConfig.registerEstatesUrl)
      .thenReturn(
        s"http://localhost:${server.port()}"
      )

    val application =
      applicationBuilder()
        .overrides(
          inject.bind[FrontendAppConfig].toInstance(mockConfig),
          inject.bind[SessionRepository].toInstance(mockSessionRepository)
        )
        .build()
    application
  }

  "getUTRFlag" must {

    "return true when utrFlag is true" in {

      val application: Application = getApplication()

      val connector = application.injector.instanceOf[RegisterEstateConnector]

      server.stubFor(
        get(urlEqualTo("/register-an-estate/utr-flag"))
          .willReturn(
            okJson(
              """
                |{
                |  "utrFlag": true
                |}
                |""".stripMargin
            )
          )
      )

      val result = connector.getUTRFlag()

      result.futureValue mustBe true

      application.stop()
    }

    "return false when utrFlag is false" in {

      val application: Application = getApplication()

      val connector = application.injector.instanceOf[RegisterEstateConnector]

      server.stubFor(
        get(urlEqualTo("/register-an-estate/utr-flag"))
          .willReturn(
            okJson(
              """
                |{
                |  "utrFlag": false
                |}
                |""".stripMargin
            )
          )
      )

      val result = connector.getUTRFlag()

      result.futureValue mustBe false

      application.stop()
    }

    "return false when utrFlag is missing from the response" in {
      val application: Application = getApplication()

      val connector = application.injector.instanceOf[RegisterEstateConnector]

      server.stubFor(
        get(urlEqualTo("/register-an-estate/utr-flag"))
          .willReturn(
            okJson(
              """
                |{
                |  "otherField": true
                |}
                |""".stripMargin
            )
          )
      )

      val result = connector.getUTRFlag()

      result.futureValue mustBe false

      application.stop()
    }

    "return false when utrFlag is not a Boolean" in {

      val application: Application = getApplication()

      val connector = application.injector.instanceOf[RegisterEstateConnector]

      server.stubFor(
        get(urlEqualTo("/register-an-estate/utr-flag"))
          .willReturn(
            okJson(
              """
                |{
                |  "utrFlag": "true"
                |}
                |""".stripMargin
            )
          )
      )

      val result = connector.getUTRFlag()

      result.futureValue mustBe false

      application.stop()
    }

    "return BAD_REQUEST when the request is unsuccessful" in {

      val application: Application = getApplication()

      val connector = application.injector.instanceOf[RegisterEstateConnector]

      server.stubFor(
        get(urlEqualTo("/register-an-estate/utr-flag"))
          .willReturn(badRequest())
      )

      val result = connector.getUTRFlag()

      whenReady(result.failed) {
        case UpstreamErrorResponse.Upstream4xxResponse(upstream) =>
          upstream.statusCode mustBe BAD_REQUEST

        case error =>
          fail(s"Unexpected exception: $error")
      }

      application.stop()
    }
  }

}

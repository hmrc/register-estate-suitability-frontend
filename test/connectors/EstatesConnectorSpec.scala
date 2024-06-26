/*
 * Copyright 2024 HM Revenue & Customs
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
import com.github.tomakehurst.wiremock.client.WireMock.{badRequest, ok, post, urlEqualTo}
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import generators.Generators
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, Inside}
import pages.MoreThanHalfMillPage
import play.api.test.Helpers._
import uk.gov.hmrc.http.{HeaderCarrier, UpstreamErrorResponse}

import scala.concurrent.ExecutionContext.Implicits._

class EstatesConnectorSpec
    extends SpecBase
    with Generators
    with ScalaFutures
    with Inside
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with IntegrationPatience {
  implicit lazy val hc: HeaderCarrier = HeaderCarrier()

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

  "sending tax amount owed" must {

    "return OK when the request is successful" in {

      val application = applicationBuilder()
        .configure(defaultAppConfigurations ++ Map("microservice.services.estates.port" -> server.port()))
        .build()

      val connector = application.injector.instanceOf[EstatesConnector]

      server.stubFor(
        post(urlEqualTo("/estates/amount-tax-owed"))
          .willReturn(ok)
      )

      val userAnswers = emptyUserAnswers.set(MoreThanHalfMillPage, true).success.value

      val result = connector.saveTaxAmountOwed(userAnswers)

      result.futureValue.status mustBe OK

      application.stop()
    }

    "return Bad Request when the request is unsuccessful" in {

      val application = applicationBuilder()
        .configure(defaultAppConfigurations ++ Map("microservice.services.estates.port" -> server.port()))
        .build()

      val connector = application.injector.instanceOf[EstatesConnector]

      server.stubFor(
        post(urlEqualTo("/estates/amount-tax-owed"))
          .willReturn(badRequest)
      )

      val userAnswers = emptyUserAnswers.set(MoreThanHalfMillPage, true).success.value

      val result = connector.saveTaxAmountOwed(userAnswers)

      whenReady(result.failed) {
        case UpstreamErrorResponse.Upstream4xxResponse(upstream) =>
          upstream.statusCode mustBe BAD_REQUEST
        case _                                                   => fail()
      }

      application.stop()
    }

    "return an exception when adapter fails to convert" in {

      val application = applicationBuilder()
        .configure(defaultAppConfigurations ++ Map("microservice.services.estates.port" -> server.port()))
        .build()

      val connector = application.injector.instanceOf[EstatesConnector]

      server.stubFor(
        post(urlEqualTo("/estates/amount-tax-owed"))
          .willReturn(badRequest)
      )

      val userAnswers = emptyUserAnswers

      val result = connector.saveTaxAmountOwed(userAnswers)

      result.failed.futureValue mustBe a[RuntimeException]

      application.stop()
    }

  }

}

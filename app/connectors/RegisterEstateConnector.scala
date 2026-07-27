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

import config.FrontendAppConfig
import play.api.Logging
import uk.gov.hmrc.http.HttpReads.Implicits
import uk.gov.hmrc.http.HttpReads.Implicits.{readEitherOf, throwOnFailure}
import uk.gov.hmrc.http.client.HttpClientV2
import uk.gov.hmrc.http.{HeaderCarrier, HttpReads, HttpResponse, StringContextOps}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class RegisterEstateConnector @Inject()(http: HttpClientV2, config: FrontendAppConfig) extends Logging{

  implicit def httpResponse: HttpReads[HttpResponse] =
    throwOnFailure(readEitherOf[HttpResponse](Implicits.readRaw))


  def getUTRFlag()(
    implicit hc: HeaderCarrier,
    ec: ExecutionContext
  ): Future[Boolean] = {

    val utrFlagUrl =
      s"${config.loginContinueUrl}/utr-flag"

    http
      .get(url"$utrFlagUrl")
      .execute[HttpResponse]
      .map { response =>


        println("response#################### "+ response.body)
        logger.info(s"UTR URL: $utrFlagUrl")
        logger.info(s"UTR status: ${response.status}")
        logger.info(
          s"UTR content type: ${response.header("Content-Type")}"
        )
        logger.info(
          s"UTR response body: ${response.body.take(500)}"
        )

        false
      }
  }

}

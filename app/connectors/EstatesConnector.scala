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

import adapters.UserAnswersToTaxAmountOwed
import config.FrontendAppConfig
import javax.inject.Inject
import models.{AmountOfTaxOwed, UserAnswers}
import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.http.HttpReads.Implicits
import uk.gov.hmrc.http.{HeaderCarrier, HttpReads, HttpResponse}
import uk.gov.hmrc.http.HttpClient
import uk.gov.hmrc.http.HttpReads.Implicits.{readEitherOf, throwOnFailure}

import scala.concurrent.{ExecutionContext, Future}

class EstatesConnector @Inject()(http: HttpClient, config : FrontendAppConfig, adapter: UserAnswersToTaxAmountOwed) {

  implicit def httpResponse: HttpReads[HttpResponse] =
    throwOnFailure(readEitherOf[HttpResponse](Implicits.readRaw))

  private val postTaxAmountOwed = s"${config.estatesUrl}/estates/amount-tax-owed"

  def saveTaxAmountOwed(userAnswers: UserAnswers)(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[HttpResponse] = {
    adapter.convert(userAnswers) match {
      case Some(amount) =>
        http.POST[JsValue, HttpResponse](postTaxAmountOwed, Json.toJson(AmountOfTaxOwed(amount)))
      case None =>
        Future.failed(new RuntimeException("Unable to determine amount of tax owed"))
    }
  }

}

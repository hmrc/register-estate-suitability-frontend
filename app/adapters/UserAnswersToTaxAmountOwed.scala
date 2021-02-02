/*
 * Copyright 2021 HM Revenue & Customs
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

package adapters

import models.UserAnswers
import pages.{MoreThanHalfMillPage, MoreThanQuarterMillPage, MoreThanTenThousandPage, MoreThanTwoHalfMillPage}

class UserAnswersToTaxAmountOwed {

  case class TaxOwedQuestions(is500Thousand: Boolean, is250Thousand: Boolean, is10Thousand: Boolean, is2AndHalfMillion: Boolean) {

    def convert : Option[String] = {
      this match {
        case TaxOwedQuestions(true, false, false, false) => Some("03")
        case TaxOwedQuestions(false, true, false, false) => Some("02")
        case TaxOwedQuestions(false, false, true, false) => Some("01")
        case TaxOwedQuestions(false, false, false, true) => Some("04")
        case _ => None
      }
    }

  }

  def convert(userAnswers: UserAnswers): Option[String] = {

    val questions = TaxOwedQuestions(
      is500Thousand = userAnswers.get(MoreThanHalfMillPage).contains(true),
      is250Thousand = userAnswers.get(MoreThanQuarterMillPage).contains(true),
      is10Thousand = userAnswers.get(MoreThanTenThousandPage).contains(true),
      is2AndHalfMillion = userAnswers.get(MoreThanTwoHalfMillPage).contains(true)
    )

    questions.convert

  }

}

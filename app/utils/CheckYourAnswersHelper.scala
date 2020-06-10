/*
 * Copyright 2020 HM Revenue & Customs
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

package utils

import controllers.routes
import models.UserAnswers
import pages._
import play.api.i18n.Messages
import play.twirl.api.{Html, HtmlFormat}
import viewmodels.AnswerRow

class CheckYourAnswersHelper(userAnswers: UserAnswers)(implicit messages: Messages) {

  def moreThanTwoHalfMill: Option[AnswerRow] = userAnswers.get(MoreThanTwoHalfMillPage) map {
    x =>
      AnswerRow(
        HtmlFormat.escape(messages("moreThanTwoHalfMill.checkYourAnswersLabel")),
        yesOrNo(x),
        routes.MoreThanTwoHalfMillController.onPageLoad().url
      )
  }

  def moreThanTenThousand: Option[AnswerRow] = userAnswers.get(MoreThanTenThousandPage) map {
    x =>
      AnswerRow(
        HtmlFormat.escape(messages("moreThanTenThousand.checkYourAnswersLabel")),
        yesOrNo(x),
        routes.MoreThanTenThousandController.onPageLoad().url
      )
  }

  def moreThanQuarterMill: Option[AnswerRow] = userAnswers.get(MoreThanQuarterMillPage) map {
    x =>
      AnswerRow(
        HtmlFormat.escape(messages("moreThanQuarterMill.checkYourAnswersLabel")),
        yesOrNo(x),
        routes.MoreThanQuarterMillController.onPageLoad().url
      )
  }

  def moreThanHalfMill: Option[AnswerRow] = userAnswers.get(MoreThanHalfMillPage) map {
    x =>
      AnswerRow(
        HtmlFormat.escape(messages("moreThanHalfMill.checkYourAnswersLabel")),
        yesOrNo(x),
        routes.MoreThanHalfMillController.onPageLoad().url
      )
  }

  def dateOfDeathBefore: Option[AnswerRow] = userAnswers.get(DateOfDeathBeforePage) map {
    x =>
      AnswerRow(
        HtmlFormat.escape(messages("dateOfDeathBefore.checkYourAnswersLabel")),
        yesOrNo(x),
        routes.DateOfDeathBeforeController.onPageLoad().url
      )
  }

  private def yesOrNo(answer: Boolean)(implicit messages: Messages): Html =
    if (answer) {
      HtmlFormat.escape(messages("site.yes"))
    } else {
      HtmlFormat.escape(messages("site.no"))
    }
}



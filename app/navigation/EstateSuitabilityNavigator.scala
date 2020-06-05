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

package navigation

import models.UserAnswers
import pages.{DateOfDeathBeforePage, MoreThanHalfMillPage, MoreThanQuaterMillPage, MoreThanTenThousandPage, MoreThanTwoHalfMillPage, Page}
import play.api.mvc.Call

object EstateSuitabilityNavigator {

  val normalRoutes: PartialFunction[Page, UserAnswers => Call] = {

    case DateOfDeathBeforePage => saleOfEstatesAmountsRoute
    case MoreThanQuaterMillPage => moreThan250KRoute
    case MoreThanHalfMillPage => moreThan500KRoute
    case MoreThanTenThousandPage => moreThan10KRoute
    case MoreThanTwoHalfMillPage => moreThan25MillionKRoute
  }

  private def saleOfEstatesAmountsRoute(answers: UserAnswers) = answers.get(DateOfDeathBeforePage) match {
    case Some(true)  => controllers.routes.MoreThanHalfMillController.onPageLoad()
    case Some(false) => controllers.routes.MoreThanQuaterMillController.onPageLoad()
    case None        => controllers.routes.SessionExpiredController.onPageLoad()
  }

  private def moreThan250KRoute(answers: UserAnswers) = answers.get(DateOfDeathBeforePage) match {
    case Some(true)  => controllers.routes.YouNeedToRegisterController.onPageLoad()
    case Some(false) => controllers.routes.MoreThanTenThousandController.onPageLoad()
    case None        => controllers.routes.SessionExpiredController.onPageLoad()
  }

  private def moreThan500KRoute(answers: UserAnswers) = answers.get(DateOfDeathBeforePage) match {
    case Some(true)  => controllers.routes.YouNeedToRegisterController.onPageLoad()
    case Some(false) => controllers.routes.MoreThanTenThousandController.onPageLoad()
    case None        => controllers.routes.SessionExpiredController.onPageLoad()
  }

  private def moreThan10KRoute(answers: UserAnswers) = answers.get(DateOfDeathBeforePage) match {
    case Some(true)  => controllers.routes.YouNeedToRegisterController.onPageLoad()
    case Some(false) => controllers.routes.MoreThanTwoHalfMillController.onPageLoad()
    case None        => controllers.routes.SessionExpiredController.onPageLoad()
  }

  private def moreThan25MillionKRoute(answers: UserAnswers) = answers.get(DateOfDeathBeforePage) match {
    case Some(true)  => controllers.routes.YouNeedToRegisterController.onPageLoad()
    case Some(false) => controllers.routes.DoNotNeedToRegisterController.onPageLoad()
    case None        => controllers.routes.SessionExpiredController.onPageLoad()
  }
}

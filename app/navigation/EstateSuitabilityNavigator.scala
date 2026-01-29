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

package navigation

import models.UserAnswers
import pages.{
  DateOfDeathBeforePage, MoreThanHalfMillPage, MoreThanQuarterMillPage, MoreThanTenThousandPage,
  MoreThanTwoHalfMillPage, Page
}
import play.api.mvc.Call

object EstateSuitabilityNavigator {

  val normalRoutes: PartialFunction[Page, UserAnswers => Call] = {

    case DateOfDeathBeforePage   => dateOfDeathBeforeRoute
    case MoreThanQuarterMillPage => moreThanQuarterRoute
    case MoreThanHalfMillPage    => moreThanHalfMillRoute
    case MoreThanTenThousandPage => moreThanTenThousandRoute
    case MoreThanTwoHalfMillPage => moreThanTwoHalfMillRoute
  }

  private def dateOfDeathBeforeRoute(answers: UserAnswers) = answers.get(DateOfDeathBeforePage) match {
    case Some(true)  => controllers.routes.MoreThanQuarterMillController.onPageLoad()
    case Some(false) => controllers.routes.MoreThanHalfMillController.onPageLoad()
    case None        => controllers.routes.SessionExpiredController.onPageLoad
  }

  private def moreThanQuarterRoute(answers: UserAnswers) = answers.get(MoreThanQuarterMillPage) match {
    case Some(true)  => controllers.routes.YouNeedToRegisterController.onPageLoad()
    case Some(false) => controllers.routes.MoreThanTenThousandController.onPageLoad()
    case None        => controllers.routes.SessionExpiredController.onPageLoad
  }

  private def moreThanHalfMillRoute(answers: UserAnswers) = answers.get(MoreThanHalfMillPage) match {
    case Some(true)  => controllers.routes.YouNeedToRegisterController.onPageLoad()
    case Some(false) => controllers.routes.MoreThanTenThousandController.onPageLoad()
    case None        => controllers.routes.SessionExpiredController.onPageLoad
  }

  private def moreThanTenThousandRoute(answers: UserAnswers) = answers.get(MoreThanTenThousandPage) match {
    case Some(true)  => controllers.routes.YouNeedToRegisterController.onPageLoad()
    case Some(false) => controllers.routes.MoreThanTwoHalfMillController.onPageLoad()
    case None        => controllers.routes.SessionExpiredController.onPageLoad
  }

  private def moreThanTwoHalfMillRoute(answers: UserAnswers) = answers.get(MoreThanTwoHalfMillPage) match {
    case Some(true)  => controllers.routes.YouNeedToRegisterController.onPageLoad()
    case Some(false) => controllers.routes.DoNotNeedToRegisterController.onPageLoad()
    case None        => controllers.routes.SessionExpiredController.onPageLoad
  }

}

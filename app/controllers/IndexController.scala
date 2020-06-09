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

package controllers

import controllers.actions.DataRetrievalAction
import javax.inject.Inject
import models.UserAnswers
import play.api.i18n.I18nSupport
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import repositories.SessionRepository
import uk.gov.hmrc.estates.controllers.actions.IdentifierAction
import uk.gov.hmrc.play.bootstrap.controller.FrontendBaseController

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

class IndexController @Inject()(
                                 val controllerComponents: MessagesControllerComponents,
                                 identify: IdentifierAction,
                                 getData: DataRetrievalAction,
                                 repository: SessionRepository
                               ) extends FrontendBaseController with I18nSupport {

  def onPageLoad: Action[AnyContent] = (identify andThen getData).async { implicit request =>
    request.userAnswers match {
      case Some(_) =>
        Future.successful(Redirect(routes.DateOfDeathBeforeController.onPageLoad()))
      case None =>
        val userAnswers: UserAnswers = UserAnswers(request.user.internalId)
        repository.set(userAnswers).map { _ =>
          Redirect(routes.DateOfDeathBeforeController.onPageLoad())
        }
    }
  }
}

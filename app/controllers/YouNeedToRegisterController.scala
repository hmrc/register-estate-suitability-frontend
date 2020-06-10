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

import config.FrontendAppConfig
import controllers.actions._
import javax.inject.Inject
import models.requests.{AgentUser, OrganisationUser}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.play.bootstrap.controller.FrontendBaseController
import views.html.YouNeedToRegisterView

import scala.concurrent.ExecutionContext

class YouNeedToRegisterController @Inject()(
                                        override val messagesApi: MessagesApi,
                                        actions: RegisterEstateActions,
                                        val controllerComponents: MessagesControllerComponents,
                                        view: YouNeedToRegisterView,
                                        config: FrontendAppConfig
                                    )(implicit ec: ExecutionContext) extends FrontendBaseController with I18nSupport {

  def onPageLoad(): Action[AnyContent] = actions.auth {
    implicit request =>

      val continueUrl = request.user match {
        case AgentUser(_) => config.agentDetails
        case OrganisationUser(_) => config.registrationProgress
      }

      Ok(view(continueUrl))
  }
}

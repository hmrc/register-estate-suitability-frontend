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

package controllers

import controllers.actions._
import forms.YesNoFormProvider
import javax.inject.Inject
import navigation.Navigator
import pages.MoreThanTwoHalfMillPage
import play.api.data.Form
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import repositories.SessionRepository
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendBaseController
import views.html.MoreThanTwoHalfMillView

import scala.concurrent.{ExecutionContext, Future}

class MoreThanTwoHalfMillController @Inject()(
                                               override val messagesApi: MessagesApi,
                                               sessionRepository: SessionRepository,
                                               navigator: Navigator,
                                               actions: RegisterEstateActions,
                                               formProvider: YesNoFormProvider,
                                               val controllerComponents: MessagesControllerComponents,
                                               view: MoreThanTwoHalfMillView
                                 )(implicit ec: ExecutionContext) extends FrontendBaseController with I18nSupport {

  val form: Form[Boolean] = formProvider.withPrefix("moreThanTwoHalfMill")

  def onPageLoad(): Action[AnyContent] = actions.authWithData {
    implicit request =>

      val preparedForm = request.userAnswers.get(MoreThanTwoHalfMillPage) match {
        case None => form
        case Some(value) => form.fill(value)
      }

      Ok(view(preparedForm))
  }

  def onSubmit(): Action[AnyContent] = actions.authWithData.async {
    implicit request =>

      form.bindFromRequest().fold(
        formWithErrors =>
          Future.successful(BadRequest(view(formWithErrors))),

        value =>
          for {
            updatedAnswers <- Future.fromTry(request.userAnswers.set(MoreThanTwoHalfMillPage, value))
            _              <- sessionRepository.set(updatedAnswers)
          } yield Redirect(navigator.nextPage(MoreThanTwoHalfMillPage, updatedAnswers))
      )
  }
}

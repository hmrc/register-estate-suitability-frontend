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

package controllers

import com.google.inject.Inject
import config.FrontendAppConfig
import connectors.RegisterEstateConnector
import controllers.actions.RegisterEstateActions
import pages._
import play.api.Logging
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import repositories.SessionRepository
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendBaseController
import uk.gov.hmrc.play.http.HeaderCarrierConverter
import utils.{CheckYourAnswersHelper, Session}
import views.html.CheckYourAnswersView

import scala.concurrent.ExecutionContext


class CheckYourAnswersController @Inject()(
                                            override val messagesApi: MessagesApi,
                                            val controllerComponents: MessagesControllerComponents,
                                            view: CheckYourAnswersView,
                                            checkYourAnswersHelper: CheckYourAnswersHelper,
                                            sessionRepository: SessionRepository,
                                            actions: RegisterEstateActions,
                                            registerEstateConnector: RegisterEstateConnector,
                                            val appConfig: FrontendAppConfig)(implicit ec: ExecutionContext)
  extends FrontendBaseController with I18nSupport with Logging {

  def onPageLoad(): Action[AnyContent] =
    actions.authWithData.async { implicit request =>


      implicit val hc: HeaderCarrier =
        HeaderCarrierConverter.fromRequestAndSession(
          request,
          request.session
        )

      registerEstateConnector.getUTRFlag().map { utrFlag =>


        val utrPage = checkYourAnswersHelper.pageAnswers(request.userAnswers, EstateRegisteredOnlineYesNoPage.toString, Some(utrFlag))

        val dateOfDeathBeforePage = checkYourAnswersHelper.pageAnswers(request.userAnswers, DateOfDeathBeforePage.toString)

        val moreThanHalfMillPage = checkYourAnswersHelper.pageAnswers(request.userAnswers, MoreThanHalfMillPage.toString)

        val moreThanQuarterMillPage = checkYourAnswersHelper.pageAnswers(request.userAnswers, MoreThanQuarterMillPage.toString)

        val moreThanTenThousandPage = checkYourAnswersHelper.pageAnswers(request.userAnswers, MoreThanTenThousandPage.toString)

        val moreThanTwoHalfMillPage = checkYourAnswersHelper.pageAnswers(request.userAnswers, MoreThanTwoHalfMillPage.toString)

        val sections = Seq(
          utrPage,
          dateOfDeathBeforePage,
          moreThanHalfMillPage,
          moreThanQuarterMillPage,
          moreThanTenThousandPage,
          moreThanTwoHalfMillPage
        ).flatten

        Ok(view(sections))
      }
    }


  def onSubmit(): Action[AnyContent] = actions.authWithData.async { implicit request =>
    for {
      _ <- sessionRepository.set(request.userAnswers)
    } yield {
      logger.info(
        s"[Session ID: ${Session.id(hc)}]" +
          s" user redirected for registration"
      )
      Redirect(appConfig.registrationProgress)
    }
  }
}

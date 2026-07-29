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

import base.SpecBase
import config.FrontendAppConfig
import connectors.RegisterEstateConnector
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import org.scalatestplus.mockito.MockitoSugar.mock
import pages.EstateRegisteredOnlineYesNoPage
import play.api.inject
import play.api.test.FakeRequest
import play.api.test.Helpers._
import repositories.SessionRepository
import utils.CheckYourAnswersHelper
import views.html.CheckYourAnswersView

import scala.concurrent.Future

class CheckYourAnswersControllerSpec extends SpecBase {

  "Check Your Answers Controller" must {

    "return OK and the correct view for a GET" in {

      val mockConnector = mock[RegisterEstateConnector]

      when(mockConnector.getUTRFlag()(any(), any())).thenReturn(Future.successful(true))

      val application = applicationBuilder(userAnswers = Some(emptyUserAnswers))
        .overrides(
          inject
            .bind[RegisterEstateConnector]
            .toInstance(mockConnector)
        )
        .build()

      val request =
        FakeRequest(
          GET,
          routes.CheckYourAnswersController.onPageLoad.url
        )

      val result = route(application, request).value

      val view = application.injector.instanceOf[CheckYourAnswersView]

      val helper = application.injector.instanceOf[CheckYourAnswersHelper]

      val expectedSections =
        Seq(helper.pageAnswers(emptyUserAnswers, EstateRegisteredOnlineYesNoPage.toString, Some(true))).flatten

      status(result) mustEqual OK

      contentAsString(result) mustEqual view(expectedSections)(request, messages).toString

      application.stop()
    }

    "redirect to Session Expired for a GET if no existing data is found" in {

      val application = applicationBuilder(userAnswers = None).build()

      val request = FakeRequest(GET, routes.CheckYourAnswersController.onPageLoad.url)

      val result = route(application, request).value

      status(result) mustEqual SEE_OTHER

      redirectLocation(result).value mustEqual routes.SessionExpiredController.onPageLoad.url

      application.stop()
    }

    "onSubmit" must {

      "save the user answers and redirect to registration progress" in {

        val sessionRepository = mock[SessionRepository]
        val mockConfig        = mock[FrontendAppConfig]

        val registrationProgressUrl = "/registration-progress"

        when(sessionRepository.set(any())).thenReturn(Future.successful(true))

        when(mockConfig.registrationProgress).thenReturn(registrationProgressUrl)

        val application = applicationBuilder(userAnswers = Some(emptyUserAnswers))
          .overrides(
            inject.bind[SessionRepository].toInstance(sessionRepository),
            inject.bind[FrontendAppConfig].toInstance(mockConfig)
          )
          .build()

        val request = FakeRequest(
          POST,
          routes.CheckYourAnswersController.onSubmit.url
        )

        val result = route(application, request).value

        status(result) mustEqual SEE_OTHER

        redirectLocation(result).value mustEqual
          registrationProgressUrl

        application.stop()
      }
    }

  }

}

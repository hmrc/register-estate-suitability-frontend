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

import base.SpecBase
import connectors.EstatesConnector
import controllers.actions.{IdentifierAction, _}
import navigation.{FakeNavigator, Navigator}
import org.mockito.Matchers.any
import org.mockito.Mockito.when
import org.scalatestplus.mockito.MockitoSugar
import pages.MoreThanHalfMillPage
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.Call
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.http.HttpResponse
import views.html.YouNeedToRegisterView

import scala.concurrent.Future

class YouNeedToRegisterControllerSpec extends SpecBase with MockitoSugar {

  def onwardRoute = Call("GET", "/foo")

  lazy val youNeedToRegisterRoute = routes.YouNeedToRegisterController.onPageLoad().url

  "YouNeedToRegister Controller" must {

    "return OK and the correct view for a GET" in {

      val application = applicationBuilder(userAnswers = Some(emptyUserAnswers)).build()

      val request = FakeRequest(GET, youNeedToRegisterRoute)

      val result = route(application, request).value

      val view = application.injector.instanceOf[YouNeedToRegisterView]

      status(result) mustEqual OK

      contentAsString(result) mustEqual
        view()(request, messages).toString

      application.stop()
    }

    "redirect to the estate hub service when user is an organisation" in {

      val mockConnector = mock[EstatesConnector]

      when(mockConnector.saveTaxAmountOwed(any())(any(), any()))
        .thenReturn(Future.successful(HttpResponse.apply(OK, "")))

      val application =
        applicationBuilder(userAnswers = Some(emptyUserAnswers))
          .overrides(
            bind[Navigator].toInstance(new FakeNavigator(onwardRoute)),
            bind[EstatesConnector].toInstance(mockConnector)
          )
          .build()

      val request =
        FakeRequest(POST, youNeedToRegisterRoute)
          .withFormUrlEncodedBody()

      val result = route(application, request).value

      status(result) mustEqual SEE_OTHER

      redirectLocation(result).value mustEqual "http://localhost:8822/register-an-estate/registration-progress"

      application.stop()
    }

    "redirect to the agent details service when user is an agent" in {

      val mockConnector = mock[EstatesConnector]

      when(mockConnector.saveTaxAmountOwed(any())(any(), any()))
        .thenReturn(Future.successful(HttpResponse.apply(OK, "")))

      val userAnswers = emptyUserAnswers.set(MoreThanHalfMillPage, true).success.value

      val application = new GuiceApplicationBuilder().overrides(
        bind[DataRequiredAction].to[DataRequiredActionImpl],
        bind[IdentifierAction].to[FakeIdentifierActionAsAgent],
        bind[DataRetrievalAction].toInstance(new FakeDataRetrievalAction(Some(userAnswers))),
        bind[EstatesConnector].toInstance(mockConnector)
      ).build()

      val request =
        FakeRequest(POST, youNeedToRegisterRoute)
          .withFormUrlEncodedBody()

      val result = route(application, request).value

      status(result) mustEqual SEE_OTHER

      redirectLocation(result).value mustEqual "http://localhost:8826/register-an-estate/agent-details"

      application.stop()
    }

    "redirect to Session Expired for a GET if no existing data is found" in {

      val application = applicationBuilder(userAnswers = None).build()

      val request = FakeRequest(GET, youNeedToRegisterRoute)

      val result = route(application, request).value

      status(result) mustEqual SEE_OTHER

      redirectLocation(result).value mustEqual routes.SessionExpiredController.onPageLoad().url

      application.stop()
    }

    "redirect to Session Expired for a POST if no existing data is found" in {

      val application = applicationBuilder(userAnswers = None).build()

      val request =
        FakeRequest(POST, youNeedToRegisterRoute)
          .withFormUrlEncodedBody()

      val result = route(application, request).value

      status(result) mustEqual SEE_OTHER

      redirectLocation(result).value mustEqual routes.SessionExpiredController.onPageLoad().url

      application.stop()
    }
  }
}

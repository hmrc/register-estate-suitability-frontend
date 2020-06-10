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
import org.scalatestplus.mockito.MockitoSugar
import play.api.mvc.Call
import play.api.test.FakeRequest
import play.api.test.Helpers._
import views.html.YouNeedToRegisterView

class YouNeedToRegisterControllerSpec extends SpecBase with MockitoSugar {

  def onwardRoute = Call("GET", "/foo")

  lazy val continueUrl = "http://localhost:8822/register-an-estate/registration-progress"

  lazy val youNeedToRegisterRoute = routes.YouNeedToRegisterController.onPageLoad().url

  "YouNeedToRegister Controller" must {

    "return OK and the correct view for a GET" in {

      val application = applicationBuilder(userAnswers = Some(emptyUserAnswers)).build()

      val request = FakeRequest(GET, youNeedToRegisterRoute)

      val result = route(application, request).value

      val view = application.injector.instanceOf[YouNeedToRegisterView]

      status(result) mustEqual OK

      contentAsString(result) mustEqual
        view(continueUrl)(fakeRequest, messages).toString

      application.stop()
    }
  }
}

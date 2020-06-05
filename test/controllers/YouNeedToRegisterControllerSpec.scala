package controllers

import base.SpecBase
import org.scalatestplus.mockito.MockitoSugar
import play.api.mvc.Call
import play.api.test.FakeRequest
import play.api.test.Helpers._
import views.html.{DoNotNeedToRegisterView, YouNeedToRegisterView}

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
        view()(fakeRequest, messages).toString

      application.stop()
    }
  }
}

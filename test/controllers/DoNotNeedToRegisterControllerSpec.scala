package controllers

import base.SpecBase
import models.{NormalMode, UserAnswers}
import navigation.{FakeNavigator, Navigator}
import org.mockito.Matchers.any
import org.mockito.Mockito.when
import org.scalatestplus.mockito.MockitoSugar
import play.api.inject.bind
import play.api.libs.json.{JsString, Json}
import play.api.mvc.Call
import play.api.test.FakeRequest
import play.api.test.Helpers._
import repositories.SessionRepository
import views.html.{DoNotNeedToRegisterView, MyNewPageView}
import views.html.helper.form

import scala.concurrent.Future

class DoNotNeedToRegisterControllerSpec extends SpecBase with MockitoSugar {

  def onwardRoute = Call("GET", "/foo")

  lazy val doNotNeedToRegisterRoute = routes.DoNotNeedToRegisterController.onPageLoad().url

  "DoNotNeedToRegister Controller" must {

    "return OK and the correct view for a GET" in {

      val application = applicationBuilder(userAnswers = Some(emptyUserAnswers)).build()

      val request = FakeRequest(GET, doNotNeedToRegisterRoute)

      val result = route(application, request).value

      val view = application.injector.instanceOf[DoNotNeedToRegisterView]

      status(result) mustEqual OK

      contentAsString(result) mustEqual
        view()(fakeRequest, messages).toString

      application.stop()
    }
  }
}

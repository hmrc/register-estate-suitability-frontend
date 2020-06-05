package views

import controllers.routes
import models.NormalMode
import play.api.data.Form
import play.twirl.api.HtmlFormat
import views.behaviours.StringViewBehaviours
import views.html.{DoNotNeedToRegisterView, MyNewPageView}

class DoNotNeedToRegisterViewSpec extends StringViewBehaviours {

  val messageKeyPrefix = "doNotNeedToRegister"

  "DoNotNeedToRegisterView view" must {

    val view = viewFor[DoNotNeedToRegisterView](Some(emptyUserAnswers))

    val applyView = view.apply()(fakeRequest, messages)

    behave like normalPage(applyView, messageKeyPrefix)

    behave like pageWithBackLink(applyView)
  }
}

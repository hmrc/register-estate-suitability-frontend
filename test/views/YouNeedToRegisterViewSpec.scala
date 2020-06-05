package views

import views.behaviours.StringViewBehaviours
import views.html.{DoNotNeedToRegisterView, YouNeedToRegisterView}

class YouNeedToRegisterViewSpec extends StringViewBehaviours {

  val messageKeyPrefix = "youNeedToRegister"

  "YouNeedToRegisterViewSpec view" must {

    val view = viewFor[YouNeedToRegisterView](Some(emptyUserAnswers))

    val applyView = view.apply()(fakeRequest, messages)

    behave like normalPage(applyView, messageKeyPrefix)

    behave like pageWithBackLink(applyView)
  }
}

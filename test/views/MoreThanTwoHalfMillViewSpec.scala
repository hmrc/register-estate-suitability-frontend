package views

import controllers.routes
import forms.YesNoFormProvider
import models.NormalMode
import play.api.data.Form
import play.twirl.api.HtmlFormat
import views.behaviours.YesNoViewBehaviours
import views.html.{MoreThanTwoHalfMillView, MyNewPageView}

class MoreThanTwoHalfMillViewSpec extends YesNoViewBehaviours {

  val messageKeyPrefix = "myNewPage"

  val form = new YesNoFormProvider()()

  "MyNewPage view" must {

    val view = viewFor[MoreThanTwoHalfMillView](Some(emptyUserAnswers))

    def applyView(form: Form[_]): HtmlFormat.Appendable =
      view.apply(form)(fakeRequest, messages)

    behave like normalPage(applyView(form), messageKeyPrefix)

    behave like pageWithBackLink(applyView(form))

    behave like yesNoPage(form, applyView, messageKeyPrefix, routes.MoreThanTwoHalfMillController.onSubmit().url)
  }
}

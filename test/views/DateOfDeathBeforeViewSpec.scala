package views

import controllers.routes
import forms.YesNoFormProvider
import play.api.data.Form
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.play.health.routes
import views.behaviours.YesNoViewBehaviours
import views.html.DateOfDeathBeforeView

class DateOfDeathBeforeViewSpec extends YesNoViewBehaviours {

  val messageKeyPrefix = "dateOfDeathBefore"

  val form = new YesNoFormProvider()()

  "dateOfDeathBefore view" must {

    val view = viewFor[DateOfDeathBeforeView](Some(emptyUserAnswers))

    def applyView(form: Form[_]): HtmlFormat.Appendable =
      view.apply(form)(fakeRequest, messages)

    behave like normalPage(applyView(form), messageKeyPrefix)

    behave like pageWithBackLink(applyView(form))

    behave like yesNoPage(form, applyView, messageKeyPrefix, routes.DateOfDeathBeforeController.onSubmit().url)
  }
}

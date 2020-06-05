package navigation

import base.SpecBase
import controllers.routes
import models.UserAnswers
import org.scalacheck.Arbitrary.arbitrary
import pages.DateOfDeathBeforePage

class EstateSuitabilityNavigatorSpec extends SpecBase {

  val navigator = new Navigator

  "Navigator" when {

    "in Normal mode" must {

      "navigate from DateOfDeathBeforePage" in {

        val page = DateOfDeathBeforePage

        forAll(arbitrary[UserAnswers]) {
          userAnswers =>
            navigator.nextPage(page, userAnswers)(userAnswers)
              .mustBe(routes.DateOfDeathBeforeController.onPageLoad())
        }
      }

    }
  }

}
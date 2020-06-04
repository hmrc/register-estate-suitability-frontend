package pages

import pages.behaviours.PageBehaviours

class DateOfDeathBeforePageSpec extends PageBehaviours {

  "DateOfDeathBeforePage" must {

    beRetrievable[Boolean](DateOfDeathBeforePage)

    beSettable[Boolean](DateOfDeathBeforePage)

    beRemovable[Boolean](DateOfDeathBeforePage)
  }
}

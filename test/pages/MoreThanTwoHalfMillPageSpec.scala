package pages

import pages.behaviours.PageBehaviours

class MoreThanTwoHalfMillPageSpec extends PageBehaviours {

  "MoreThanTwoHalfMillPage" must {

    beRetrievable[Boolean](MoreThanTwoHalfMillPage)

    beSettable[Boolean](MoreThanTwoHalfMillPage)

    beRemovable[Boolean](MoreThanTwoHalfMillPage)
  }
}

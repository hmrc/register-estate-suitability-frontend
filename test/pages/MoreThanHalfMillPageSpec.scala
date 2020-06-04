package pages

import pages.behaviours.PageBehaviours

class MoreThanHalfMillPageSpec extends PageBehaviours {

  "MoreThanHalfMillPage" must {

    beRetrievable[Boolean](MoreThanHalfMillPage)

    beSettable[Boolean](MoreThanHalfMillPage)

    beRemovable[Boolean](MoreThanHalfMillPage)
  }
}

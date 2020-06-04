package pages

import pages.behaviours.PageBehaviours

class MoreThanQuaterMillPageSpec extends PageBehaviours {

  "MoreThanQuaterMillPage" must {

    beRetrievable[Boolean](MoreThanQuaterMillPage)

    beSettable[Boolean](MoreThanQuaterMillPage)

    beRemovable[Boolean](MoreThanQuaterMillPage)
  }
}

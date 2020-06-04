package pages

import pages.behaviours.PageBehaviours

class MoreThanTenThousandPageSpec extends PageBehaviours {

  "MoreThanTenThousandPage" must {

    beRetrievable[Boolean](MoreThanTenThousandPage)

    beSettable[Boolean](MoreThanTenThousandPage)

    beRemovable[Boolean](MoreThanTenThousandPage)
  }
}

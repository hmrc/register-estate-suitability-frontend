/*
 * Copyright 2025 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pages

import pages.behaviours.PageBehaviours

class DateOfDeathBeforePageSpec extends PageBehaviours {

  "DateOfDeathBeforePage" must {

    beRetrievable[Boolean](DateOfDeathBeforePage)

    beSettable[Boolean](DateOfDeathBeforePage)

    beRemovable[Boolean](DateOfDeathBeforePage)
  }

  "implement cleanup logic when NO selected" in {
    val userAnswers = emptyUserAnswers
      .set(MoreThanQuarterMillPage, true).success.value
      .set(MoreThanTenThousandPage, false).success.value
      .set(MoreThanTwoHalfMillPage, false).success.value

    val cleaned = userAnswers.set(DateOfDeathBeforePage, false).success.value

    cleaned.get(MoreThanQuarterMillPage) mustNot be(defined)
    cleaned.get(MoreThanTenThousandPage) mustNot be(defined)
    cleaned.get(MoreThanTwoHalfMillPage) mustNot be(defined)
  }

  "implement cleanup logic when YES selected" in {
    val userAnswers = emptyUserAnswers
      .set(MoreThanHalfMillPage, false).success.value
      .set(MoreThanTenThousandPage, false).success.value
      .set(MoreThanTwoHalfMillPage, false).success.value

    val cleaned = userAnswers.set(DateOfDeathBeforePage, true).success.value

    cleaned.get(MoreThanHalfMillPage) mustNot be(defined)
    cleaned.get(MoreThanTenThousandPage) mustNot be(defined)
    cleaned.get(MoreThanTwoHalfMillPage) mustNot be(defined)
  }
}

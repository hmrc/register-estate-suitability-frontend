/*
 * Copyright 2020 HM Revenue & Customs
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
      .set(DateOfDeathBeforePage, true)
      .flatMap(_.set(MoreThanHalfMillPage, false))

    userAnswers.get.get(MoreThanHalfMillPage) mustNot be(defined)
  }

  "implement cleanup logic when YES selected" in {
    val userAnswers = emptyUserAnswers
      .set(DateOfDeathBeforePage, false)
      .flatMap(_.set(MoreThanQuarterMillPage, false))

    userAnswers.get.get(MoreThanQuarterMillPage) mustNot be(defined)
  }
}

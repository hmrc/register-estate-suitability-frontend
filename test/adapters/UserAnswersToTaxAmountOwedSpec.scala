/*
 * Copyright 2026 HM Revenue & Customs
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

package adapters

import base.SpecBase
import pages._

class UserAnswersToTaxAmountOwedSpec extends SpecBase {

  "death was before 6 April 2016" must {

    "when user answers more than 250 thousand" in {

      val userAnswers = emptyUserAnswers
        .set(DateOfDeathBeforePage, true).success.value
        .set(MoreThanQuarterMillPage, true).success.value

      val result = new UserAnswersToTaxAmountOwed().convert(userAnswers)

      result.value mustBe "02"
    }

    "when user answers more than 10 thousand" in {

      val userAnswers = emptyUserAnswers
        .set(DateOfDeathBeforePage, true).success.value
        .set(MoreThanQuarterMillPage, false).success.value
        .set(MoreThanTenThousandPage, true).success.value


      val result = new UserAnswersToTaxAmountOwed().convert(userAnswers)

      result.value mustBe "01"
    }

    "when user answers more than 2.5 million" in {

      val userAnswers = emptyUserAnswers
        .set(DateOfDeathBeforePage, true).success.value
        .set(MoreThanQuarterMillPage, false).success.value
        .set(MoreThanTenThousandPage, false).success.value
        .set(MoreThanTwoHalfMillPage, true).success.value

      val result = new UserAnswersToTaxAmountOwed().convert(userAnswers)

      result.value mustBe "04"
    }

    "when user has not answered enough questions" in {

      val userAnswers = emptyUserAnswers

      val result = new UserAnswersToTaxAmountOwed().convert(userAnswers)

      result mustBe None
    }

  }

  "death was after 6 April 2016" must {

    "when user answers more than 500 thousand" in {

      val userAnswers = emptyUserAnswers
        .set(DateOfDeathBeforePage, false).success.value
        .set(MoreThanHalfMillPage, true).success.value

      val result = new UserAnswersToTaxAmountOwed().convert(userAnswers)

      result.value mustBe "03"
    }

    "when user answers more than 10 thousand" in {

      val userAnswers = emptyUserAnswers
        .set(DateOfDeathBeforePage, false).success.value
        .set(MoreThanTenThousandPage, true).success.value

      val result = new UserAnswersToTaxAmountOwed().convert(userAnswers)

      result.value mustBe "01"
    }

    "when user answers more than 2.5 million" in {

      val userAnswers = emptyUserAnswers
        .set(DateOfDeathBeforePage, false).success.value
        .set(MoreThanHalfMillPage, false).success.value
        .set(MoreThanTenThousandPage, false).success.value
        .set(MoreThanTwoHalfMillPage, true).success.value

      val result = new UserAnswersToTaxAmountOwed().convert(userAnswers)

      result.value mustBe "04"
    }

    "when user has not answered enough questions" in {

      val userAnswers = emptyUserAnswers

      val result = new UserAnswersToTaxAmountOwed().convert(userAnswers)

      result mustBe None
    }

  }


}

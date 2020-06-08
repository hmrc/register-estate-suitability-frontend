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

package navigation

import base.SpecBase
import controllers.routes
import pages.{DateOfDeathBeforePage, MoreThanHalfMillPage, MoreThanQuaterMillPage, MoreThanTenThousandPage, MoreThanTwoHalfMillPage}

class EstateSuitabilityNavigatorSpec extends SpecBase {

  val navigator = new Navigator

  "Navigator" when {

    "DateOfDeathBeforePage -> yes -> MoreThanQuaterMillPage" in {

      val page = DateOfDeathBeforePage

      val userAnswers = emptyUserAnswers
         .set(DateOfDeathBeforePage, true).success.value

      navigator.nextPage(page, userAnswers)
         .mustBe(routes.MoreThanQuaterMillController.onPageLoad())
    }

    "DateOfDeathBeforePage -> no -> MoreThanHalfMillPage" in {

      val page = DateOfDeathBeforePage

      val userAnswers = emptyUserAnswers
        .set(DateOfDeathBeforePage, false).success.value

      navigator.nextPage(page, userAnswers)
        .mustBe(routes.MoreThanHalfMillController.onPageLoad())
    }

    "MoreThanQuaterMillPage -> yes -> YouNeedToRegister" in {

      val page = MoreThanQuaterMillPage

      val userAnswers = emptyUserAnswers
        .set(DateOfDeathBeforePage,true).success.value
        .set(MoreThanQuaterMillPage, true).success.value

      navigator.nextPage(page, userAnswers)
        .mustBe(routes.YouNeedToRegisterController.onPageLoad())
    }

    "MoreThanQuaterMillPage -> No -> MoreThan10K" in {

      val page = MoreThanQuaterMillPage

      val userAnswers = emptyUserAnswers
        .set(DateOfDeathBeforePage,true).success.value
        .set(MoreThanQuaterMillPage, false).success.value

      navigator.nextPage(page, userAnswers)
        .mustBe(routes.MoreThanTenThousandController.onPageLoad())
    }

    "MoreThanHalfMillPage -> Yes -> YouNeedToRegister" in {

      val page = MoreThanHalfMillPage

      val userAnswers = emptyUserAnswers
        .set(DateOfDeathBeforePage,false).success.value
        .set(MoreThanHalfMillPage, true).success.value

      navigator.nextPage(page, userAnswers)
        .mustBe(routes.YouNeedToRegisterController.onPageLoad())
    }

    "MoreThanHalfMillPage -> no -> MoreThan10K" in {

      val page = MoreThanHalfMillPage

      val userAnswers = emptyUserAnswers
        .set(DateOfDeathBeforePage,false).success.value
        .set(MoreThanHalfMillPage, false).success.value

      navigator.nextPage(page, userAnswers)
        .mustBe(routes.MoreThanTenThousandController.onPageLoad())
    }

    "MoreThanTenThousand -> Yes -> YouNeedToRegister" in {

      val page = MoreThanTenThousandPage

      val userAnswers = emptyUserAnswers
        .set(DateOfDeathBeforePage,true).success.value
        .set(MoreThanQuaterMillPage,false).success.value
        .set(MoreThanTenThousandPage, true).success.value

      navigator.nextPage(page, userAnswers)
        .mustBe(routes.YouNeedToRegisterController.onPageLoad())
    }

    "MoreThanTenThousand -> no -> MoreThanTwoHalfMill" in {

      val page = MoreThanTenThousandPage

      val userAnswers = emptyUserAnswers
        .set(DateOfDeathBeforePage,false).success.value
        .set(MoreThanHalfMillPage,false).success.value
        .set(MoreThanTenThousandPage, false).success.value

      navigator.nextPage(page, userAnswers)
        .mustBe(routes.MoreThanTwoHalfMillController.onPageLoad())
    }


    "MoreThanTwoHalfMill -> Yes -> YouNeedToRegister" in {

      val page = MoreThanTwoHalfMillPage

      val userAnswers = emptyUserAnswers
        .set(DateOfDeathBeforePage,true).success.value
        .set(MoreThanQuaterMillPage,false).success.value
        .set(MoreThanTenThousandPage,false).success.value
        .set(MoreThanTwoHalfMillPage, true).success.value

      navigator.nextPage(page, userAnswers)
        .mustBe(routes.YouNeedToRegisterController.onPageLoad())
    }

    "MoreThanTwoHalfMill -> no -> MoreThanTwoHalfMill" in {

      val page = MoreThanTwoHalfMillPage

      val userAnswers = emptyUserAnswers
        .set(DateOfDeathBeforePage,false).success.value
        .set(MoreThanQuaterMillPage,false).success.value
        .set(MoreThanTenThousandPage,false).success.value
        .set(MoreThanTwoHalfMillPage, false).success.value

      navigator.nextPage(page, userAnswers)
        .mustBe(routes.DoNotNeedToRegisterController.onPageLoad())
    }

  }
}
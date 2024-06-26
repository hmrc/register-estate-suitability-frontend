/*
 * Copyright 2024 HM Revenue & Customs
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

package generators

import org.scalacheck.Arbitrary
import org.scalacheck.Arbitrary.arbitrary
import pages._
import play.api.libs.json.{JsValue, Json}

trait UserAnswersEntryGenerators extends PageGenerators with ModelGenerators {

  implicit lazy val arbitraryMoreThanTwoHalfMillUserAnswersEntry: Arbitrary[(MoreThanTwoHalfMillPage.type, JsValue)] =
    Arbitrary {
      for {
        page  <- arbitrary[MoreThanTwoHalfMillPage.type]
        value <- arbitrary[Boolean].map(Json.toJson(_))
      } yield (page, value)
    }

  implicit lazy val arbitraryMoreThanTenThousandUserAnswersEntry: Arbitrary[(MoreThanTenThousandPage.type, JsValue)] =
    Arbitrary {
      for {
        page  <- arbitrary[MoreThanTenThousandPage.type]
        value <- arbitrary[Boolean].map(Json.toJson(_))
      } yield (page, value)
    }

  implicit lazy val arbitraryMoreThanQuarterMillPageUserAnswersEntry: Arbitrary[(MoreThanQuarterMillPage.type, JsValue)] =
    Arbitrary {
      for {
        page  <- arbitrary[MoreThanQuarterMillPage.type]
        value <- arbitrary[Boolean].map(Json.toJson(_))
      } yield (page, value)
    }

  implicit lazy val arbitraryMoreThanHalfMillUserAnswersEntry: Arbitrary[(MoreThanHalfMillPage.type, JsValue)] =
    Arbitrary {
      for {
        page  <- arbitrary[MoreThanHalfMillPage.type]
        value <- arbitrary[Boolean].map(Json.toJson(_))
      } yield (page, value)
    }

  implicit lazy val arbitraryDateOfDeathBeforeUserAnswersEntry: Arbitrary[(DateOfDeathBeforePage.type, JsValue)] =
    Arbitrary {
      for {
        page  <- arbitrary[DateOfDeathBeforePage.type]
        value <- arbitrary[Boolean].map(Json.toJson(_))
      } yield (page, value)
    }
}

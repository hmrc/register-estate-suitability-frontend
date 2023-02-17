/*
 * Copyright 2023 HM Revenue & Customs
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

package repositories

import base.SpecBase
import models.UserAnswers
import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.model.Filters
import org.scalatest.BeforeAndAfterEach
import play.api.libs.json.Json
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import uk.gov.hmrc.mongo.test.MongoSupport

import scala.concurrent.ExecutionContext.Implicits.global

class SessionRepositorySpec extends SpecBase with MongoSupport with BeforeAndAfterEach {

  private val repository = new SessionRepository(mongoComponent, frontendAppConfig)

  private val internalId1: String = "Int-074d0597107e-557e-4559-96ba-328969d0"
  private val internalId2: String = "Int-074d0597107e-557e-4559-96ba-328969d1"
  private val user1 = UserAnswers(internalId1, Json.obj("test" -> "123"))
  private val user2 = UserAnswers(internalId2, Json.obj("test" -> "456"))
  private val user1Updated = UserAnswers(internalId1, Json.obj("test" -> "321"))
  private val user2Updated = UserAnswers(internalId2, Json.obj("test" -> "654"))

  private def checkAnswers(actual: UserAnswers, expected: UserAnswers): Unit = {
    actual.id mustBe expected.id
    actual.data mustBe expected.data
  }

  override def beforeEach(): Unit = {
    super.beforeEach()
    await(repository.collection.deleteMany(BsonDocument()).toFuture())
  }

  "SessionRepository" must {

    "return None when no data exists" in {
      repository.get(internalId1).futureValue mustBe None
    }

    "must be able to store data" in {
      //store
      repository.collection.countDocuments().toFuture().futureValue mustBe 0
      repository.set(user1).futureValue mustBe true
      repository.set(user2).futureValue mustBe true

      //retrieve
      val selector1 = Filters.equal("_id", internalId1)
      val selector2 = Filters.equal("_id", internalId2)
      checkAnswers(repository.collection.find(selector1).headOption().futureValue.value, user1)
      checkAnswers(repository.collection.find(selector2).headOption().futureValue.value, user2)
      repository.collection.countDocuments().toFuture().futureValue mustBe 2
    }

    "must be able to retrieve data" in {
      //store
      repository.collection.countDocuments().toFuture().futureValue mustBe 0
      await(repository.collection.insertOne(user1).toFuture())
      await(repository.collection.insertOne(user2).toFuture())

      //retrieve
      checkAnswers(repository.get(internalId1).futureValue.value, user1)
      checkAnswers(repository.get(internalId2).futureValue.value, user2)
      repository.collection.countDocuments().toFuture().futureValue mustBe 2
    }

    "must be able to update data" in {
      //store
      repository.collection.countDocuments().toFuture().futureValue mustBe 0
      repository.set(user1).futureValue mustBe true
      repository.set(user2).futureValue mustBe true

      //retrieve
      checkAnswers(repository.get(internalId1).futureValue.value, user1)
      checkAnswers(repository.get(internalId2).futureValue.value, user2)
      repository.collection.countDocuments().toFuture().futureValue mustBe 2

      //update
      repository.set(user1Updated).futureValue mustBe true
      repository.set(user2Updated).futureValue mustBe true

      //retrieve updated
      checkAnswers(repository.get(internalId1).futureValue.value, user1Updated)
      checkAnswers(repository.get(internalId2).futureValue.value, user2Updated)
      repository.collection.countDocuments().toFuture().futureValue mustBe 2
    }

  }

}

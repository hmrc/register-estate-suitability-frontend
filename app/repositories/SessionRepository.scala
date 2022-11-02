/*
 * Copyright 2022 HM Revenue & Customs
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

import config.FrontendAppConfig

import java.time.LocalDateTime
import javax.inject.Inject
import models.UserAnswers
import org.mongodb.scala.model.{Filters, IndexModel, IndexOptions, Indexes, ReplaceOptions}
import uk.gov.hmrc.mongo.MongoComponent
import uk.gov.hmrc.mongo.play.json.PlayMongoRepository

import java.util.concurrent.TimeUnit
import scala.concurrent.{ExecutionContext, Future}

class SessionRepository @Inject()(
                                          mongo: MongoComponent,
                                          config: FrontendAppConfig
                                        )(implicit val ec: ExecutionContext)
  extends PlayMongoRepository[UserAnswers](
    mongoComponent = mongo,
    collectionName = "user-answers",
    domainFormat = UserAnswers.format,
    indexes = Seq(
      IndexModel(
        Indexes.ascending("lastUpdated"),
        IndexOptions()
          .name("user-answers-last-updated-index")
          .expireAfter(config.cachettlSessionInSeconds, TimeUnit.SECONDS))
    ), replaceIndexes = config.dropIndexes
  ){

  def get(id: String): Future[Option[UserAnswers]] = {
    val selector = Filters.equal("_id", id)

    collection.find(selector).headOption()
  }

  def set(userAnswers: UserAnswers): Future[Boolean] = {

    val selector = Filters.equal("_id", userAnswers.id)

    val newUser = userAnswers.copy(lastUpdated = LocalDateTime.now)

    val replaceOptions = new ReplaceOptions().upsert(true)

    collection.replaceOne(selector, newUser, replaceOptions).headOption().map(_.exists(_.wasAcknowledged()))
  }
}

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

package controllers.actions

import com.google.inject.Inject
import models.requests.{AgentUser, IdentifierRequest, OrganisationUser}
import play.api.Logging
import play.api.mvc.Results._
import play.api.mvc.{Request, Result, _}
import uk.gov.hmrc.auth.core.AffinityGroup.{Agent, Organisation}
import uk.gov.hmrc.auth.core.retrieve.v2.Retrievals
import uk.gov.hmrc.auth.core.retrieve.~
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.play.http.HeaderCarrierConverter
import utils.Session

import scala.concurrent.{ExecutionContext, Future}

class AuthenticatedIdentifierAction @Inject()(val trustsAuth: TrustsAuthorisedFunctions,
                                              val parser: BodyParsers.Default
                                             )(implicit val executionContext: ExecutionContext) extends IdentifierAction with Logging {

  def invokeBlock[A](request: Request[A],
                     block: IdentifierRequest[A] => Future[Result]) : Future[Result] = {

    val retrievals = Retrievals.internalId and
      Retrievals.affinityGroup

    implicit val hc : HeaderCarrier = HeaderCarrierConverter.fromRequestAndSession(request, request.session)

    trustsAuth.authorised().retrieve(retrievals) {
      case Some(internalId) ~ Some(Agent) =>
        block(IdentifierRequest(request, AgentUser(internalId)))
      case Some(internalId) ~ Some(Organisation) =>
        block(IdentifierRequest(request, OrganisationUser(internalId)))
      case _ =>
        logger.info(s"[Session ID: ${Session.id(hc)}] Insufficient enrolment")
        Future.successful(Redirect(controllers.routes.UnauthorisedController.onPageLoad))
    } recover trustsAuth.recoverFromAuthorisation
  }

}

trait IdentifierAction extends ActionBuilder[IdentifierRequest, AnyContent]

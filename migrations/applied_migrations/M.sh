#!/bin/bash

echo ""
echo "Applying migration M"

echo "Adding routes to conf/app.routes"

echo "" >> ../conf/app.routes
echo "GET        /m                        controllers.MController.onPageLoad(mode: Mode = NormalMode)" >> ../conf/app.routes
echo "POST       /m                        controllers.MController.onSubmit(mode: Mode = NormalMode)" >> ../conf/app.routes

echo "GET        /changeM                  controllers.MController.onPageLoad(mode: Mode = CheckMode)" >> ../conf/app.routes
echo "POST       /changeM                  controllers.MController.onSubmit(mode: Mode = CheckMode)" >> ../conf/app.routes

echo "Adding messages to conf.messages"
echo "" >> ../conf/messages.en
echo "m.title = m" >> ../conf/messages.en
echo "m.heading = m" >> ../conf/messages.en
echo "m.checkYourAnswersLabel = m" >> ../conf/messages.en
echo "m.error.required = Select yes if m" >> ../conf/messages.en

echo "Adding to UserAnswersEntryGenerators"
awk '/trait UserAnswersEntryGenerators/ {\
    print;\
    print "";\
    print "  implicit lazy val arbitraryMUserAnswersEntry: Arbitrary[(MPage.type, JsValue)] =";\
    print "    Arbitrary {";\
    print "      for {";\
    print "        page  <- arbitrary[MPage.type]";\
    print "        value <- arbitrary[Boolean].map(Json.toJson(_))";\
    print "      } yield (page, value)";\
    print "    }";\
    next }1' ../test/generators/UserAnswersEntryGenerators.scala > tmp && mv tmp ../test/generators/UserAnswersEntryGenerators.scala

echo "Adding to PageGenerators"
awk '/trait PageGenerators/ {\
    print;\
    print "";\
    print "  implicit lazy val arbitraryMPage: Arbitrary[MPage.type] =";\
    print "    Arbitrary(MPage)";\
    next }1' ../test/generators/PageGenerators.scala > tmp && mv tmp ../test/generators/PageGenerators.scala

echo "Adding to UserAnswersGenerator"
awk '/val generators/ {\
    print;\
    print "    arbitrary[(MPage.type, JsValue)] ::";\
    next }1' ../test/generators/UserAnswersGenerator.scala > tmp && mv tmp ../test/generators/UserAnswersGenerator.scala

echo "Adding helper method to CheckYourAnswersHelper"
awk '/class/ {\
     print;\
     print "";\
     print "  def m: Option[AnswerRow] = userAnswers.get(MPage) map {";\
     print "    x =>";\
     print "      AnswerRow(";\
     print "        HtmlFormat.escape(messages(\"m.checkYourAnswersLabel\")),";\
     print "        yesOrNo(x),";\
     print "        routes.MController.onPageLoad(CheckMode).url";\
     print "      )"
     print "  }";\
     next }1' ../app/utils/CheckYourAnswersHelper.scala > tmp && mv tmp ../app/utils/CheckYourAnswersHelper.scala

echo "Migration M completed"

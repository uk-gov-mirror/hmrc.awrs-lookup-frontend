/*
 * Copyright 2018 HM Revenue & Customs
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

package uk.gov.hmrc.awrslookup.controllers

import javax.inject.Inject

import play.api.Play.current
import play.api.i18n.{Lang, MessagesApi}
import play.api.mvc.Call
import uk.gov.hmrc.play.config.RunMode
import uk.gov.hmrc.play.language.LanguageController


class AwrsLanguageController @Inject()(implicit val messagesApi: MessagesApi) extends LanguageController with RunMode {
  val English = Lang("en")
  val Welsh = Lang("cy")

  def langToCall(lang: String): Call = routes.AwrsLanguageController.switchToLanguage(lang)

  override def languageMap: Map[String, Lang] = Map("English" -> English,
    "Cymraeg" -> Welsh)

  override protected def fallbackURL: String = current.configuration.getString(s"$env.language.fallbackUrl").getOrElse("/")
}

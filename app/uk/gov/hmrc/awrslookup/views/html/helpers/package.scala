/*
 * Copyright 2016 HM Revenue & Customs
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

package uk.gov.hmrc.awrslookup.views.html

import org.joda.time.DateTime
import play.twirl.api.Html


package object helpers {

  implicit def argConv[T](arg: T): Option[T] = Some(arg)

  val emptyHtml = Html("")

  implicit def htmlUtil(html: Option[Html]): Html = html.fold(emptyHtml)(x => x)

  def theTime: String = {
    val now = DateTime.now()
    now.toString("dd MMMM yyyy HH:mm ") + now.toString("a").toLowerCase
  }

}

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

package uk.gov.hmrc.awrslookup.forms

import uk.gov.hmrc.awrslookup._
import forms.validation.util.ConstraintUtil.{CompulsoryTextFieldMappingParameter, FieldFormatConstraintParameter, MaxLengthConstraintIsHandledByTheRegEx, OptionalTextFieldMappingParameter}
import forms.validation.util.ErrorMessagesUtilAPI._
import forms.validation.util.MappingUtilAPI._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Valid
import uk.gov.hmrc.awrslookup.models.Query
import prevalidation._
import uk.gov.hmrc.awrslookup.forms.validation.util.ErrorMessageFactory._
import uk.gov.hmrc.awrslookup.forms.validation.util.{FieldErrorConfig, MessageArguments, SummaryErrorConfig, TargetFieldIds}

object SearchForm {

  val query = "query"
  val awrsRefRegEx = "^X[A-Z]AW00000[0-9]{6}$"
  private lazy val leading4CharRegex = "^[a-zA-Z]{4}.{11}$"
  private lazy val leadingXRegex = "^X.{14}$"
  private lazy val zerosRegex = "^[a-zA-Z]{4}00000.{6}"

  private lazy val queryTargetId = TargetFieldIds(query)
  private lazy val invalidFormatSummaryError = SummaryErrorConfig("awrs.generic.error.character_invalid.summary", MessageArguments("search field"))

  private lazy val invalidQueryFieldError =
    (fieldErr: String) => createErrorMessage(
      queryTargetId,
      FieldErrorConfig(fieldErr),
      invalidFormatSummaryError)

  private lazy val formatRules =
    FieldFormatConstraintParameter(
      (name: String) => {
        name match {
          case _ if name.matches(awrsRefRegEx) => Valid
          case _ if name.length != 15 => invalidQueryFieldError("awrs.search.query.string_length_mismatch")
          case _ if !name.matches(leading4CharRegex) => invalidQueryFieldError("awrs.search.query.leading_character_Length_mismatch")
          case _ if !name.matches(leadingXRegex) => invalidQueryFieldError("awrs.search.query.leading_x_mismatch")
          case _ if !name.matches(zerosRegex) => invalidQueryFieldError("awrs.search.query.zeros_mismatch")
          case _ => invalidQueryFieldError("awrs.search.query.default_invalid_urn")
        }
      }
    )

  private lazy val compulsoryQueryField = compulsoryText(
    CompulsoryTextFieldMappingParameter(
      empty = simpleFieldIsEmptyConstraintParameter(query, "awrs.search.query.empty"),
      maxLengthValidation = MaxLengthConstraintIsHandledByTheRegEx(),
      formatValidations = Seq(formatRules)
    ))

  lazy val searchValidationForm = Form(mapping(
    query -> compulsoryQueryField.toStringFormatter
  )(Query.apply)(Query.unapply))

  lazy val searchForm = PreprocessedForm(searchValidationForm)

}
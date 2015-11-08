package models

import java.util.Date

import play.api.libs.json.{JsPath, Reads, Json}
import play.api.libs.json.Reads._

/**
 * Class that represents information recieved from webhook
 */
case class Event(
  eventName: String,
  data: String,
  published_at: Date,
  coreid: String,
  ttl: Int,
  name: String
)

object Event {
  import play.api.libs.functional.syntax._

  // Generates Writes and Reads for Feed and User thanks to Json Macros
  implicit val EventFormat = Json.format[Event]
}
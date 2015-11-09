package controllers

import javax.inject.Inject

import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import play.modules.reactivemongo.json.collection.JSONCollection
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import play.api.libs.concurrent.Execution.Implicits.defaultContext

/**
 * Model for tests
 */
case class Event(
  eventName: String
)

/**
 * Companion object for implicits
 */
object Event {
  // Generates Writes and Reads for Feed and User thanks to Json Macros
  implicit val EventFormat = Json.format[Event]
}

/**
 * Test controller
 * @param reactiveMongoApi
 */
class Application @Inject() (val reactiveMongoApi: ReactiveMongoApi)
  extends Controller with MongoController with ReactiveMongoComponents {

  implicit val EventFormat = Json.format[Event]

  val collection = reactiveMongoApi.db.collection[JSONCollection]("mongotest")

  def index = Action.async {
    val event = Event(
      "test"
    )
    Logger.debug("Create event")
    collection.insert(event).map{
      case res if res.ok =>
        Logger.debug("Succesfully saved in database")
        Ok("Succesfully saved in database")
      case res =>
        Logger.debug("Could not save in database"+res.getCause)
        Ok("Could not save in database"+res.getCause)
    }
  }

}

package services

import javax.inject.Inject

import models.Event
import play.api.{Logger, db}
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json.collection.JSONCollection
import play.api.Play.current

import scala.util.{Failure, Success}
import play.api.libs.concurrent.Execution.Implicits.defaultContext

/**
 * Implementation of event service for MongoDb
 * TODO check singleton
 */
class MongoEventService extends IEventService{

  lazy val reactiveMongoApi = current.injector.instanceOf[ReactiveMongoApi]

  /**
   * Collection to store events
   * @return
   */
  def collection = reactiveMongoApi.db.collection[JSONCollection]("events")

  /**
   * Create new event in collection
   * @param event event to save in mongo
   */
  def create(event: Event) = {
    Logger.debug("MONGO_URL = " + current.configuration.getString("mongodb.uri"))
    collection.insert(event).map{
      case res if res.ok => Success(event)
      case res => Failure(res.getCause)
    }
  }
}

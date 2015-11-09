package controllers

import java.util.Date
import javax.inject.Inject

import models.Event
import play.api.Play._
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import reactivemongo.api.MongoConnection
import services.IEventService
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
 * Controller to receive webhooks messages.
 */
class Webhook @Inject() (eventService: IEventService) extends Controller{

  import models.Event._

  /**
   * Action to receive events from webhooks
   * @return return json k or error message
   */
  def event = Action.async(parse.json) { request =>
    request.body.validate[Event].map{event =>
      //save to db event
      eventService.create(event).map{error =>
        Created(Json.toJson(Map("status" -> "ok")))
      }
    }.getOrElse(Future.successful(BadRequest(Json.toJson(Map("status" -> "error")))))
  }

  /**
   * Test format of Event json
   * @return
   */
  def test = Action{
    val dateFormatter = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX")
    //"data":"3587 , 3677 , 3544 , 3635, 3674, 3619, 3658","ttl":"60","published_at":"2015-11-07T04:15:49.980Z","coreid":"440027000747343232363230","name":"Sensors"}
    val event = Event(
      "test",
      "3587 , 3677 , 3544 , 3635, 3674, 3619, 3658" ,
//      new Date(),
      dateFormatter.parse("2015-11-07T04:15:49.979Z"),
//      "",
      "440027000747343232363230",
      60,
      "Sensors"
    )
    Ok(Json.toJson(event))
  }

  def testMongoConnection = Action{
    current.configuration.getString("mongodb.uri") match {
      case Some(uri) => MongoConnection.parseURI(uri) match {
        case Success(parsedURI) if parsedURI.db.isDefined =>
          parsedURI.authenticate.map{auth =>
            Ok("Connected and authenticated " + auth)
          }
          Ok("Connected to " + uri)
        case Success(_) =>
          throw configuration.globalError(s"Missing database name in mongodb.uri '$uri'")
        case Failure(e) => throw configuration.globalError(s"Invalid mongodb.uri '$uri'", Some(e))
      }

      case _ => Ok("error")
    }
  }

}

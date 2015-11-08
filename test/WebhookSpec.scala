import java.util.Date

import models.Event
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._

/**
 * Tests for webhooks API
 */
@RunWith(classOf[JUnitRunner])
class WebhookSpec extends Specification{

  private def generateEvent(eventName: String) = {
    Event(
      "test",
      "3587 , 3677 , 3544 , 3635, 3674, 3619, 3658" ,
      new Date(),
      "440027000747343232363230",
      60,
      "Sensors"
    )
  }

  "Webhook" should {
    "send test event " in new WithApplication {
      val res = route(
        FakeRequest(POST, "/event").withJsonBody(Json.toJson(generateEvent("leaning forward")))
      ).get // must beSome.which (status(_) == NOT_FOUND)
      contentAsJson(res) must_== Map("status" -> "ok")
    }

//    "send leaning forward event " in new WithApplication {
//
//    }
//
//    "send leaning backward event " in new WithApplication {
//
//    }
//
//    "send leaning left event " in new WithApplication {
//
//    }
//
//    "send leaning right event " in new WithApplication {
//
//    }
//
//    "send Sat event " in new WithApplication {
//
//    }
//
//    "send Stud event " in new WithApplication {
//
//    }
  }

}

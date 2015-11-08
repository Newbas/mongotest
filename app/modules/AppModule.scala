package modules

import com.google.inject.AbstractModule
import play.modules.reactivemongo.DefaultReactiveMongoApi
import reactivemongo.api.DB
import services.{IEventService, MongoEventService}

/**
 * Application module used to define DI components
 */
class AppModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[IEventService]).to(classOf[MongoEventService])
//    bind(classOf[DB]).to(classOf[DefaultReactiveMongoApi])
  }
}

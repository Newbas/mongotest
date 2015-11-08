package services

import models.Event

import scala.concurrent.Future
import scala.util.Try

/**
 * Interface for event service
 */
trait IEventService {

  /**
   * Create new event in data store
   * @param event event to create
   * @return Future that tries to return event
   */
  def create(event: Event): Future[Try[Event]]

}

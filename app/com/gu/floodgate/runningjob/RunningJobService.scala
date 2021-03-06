package com.gu.floodgate.runningjob

import cats.syntax.either._
import com.gu.floodgate.{ ContentSourceNotFound, RunningJobNotFound, CustomError, DynamoDBTable }
import com.gu.scanamo.DynamoFormat
import org.joda.time.{ DateTimeZone, DateTime }
import scala.concurrent.Future

class RunningJobService(runningJobTable: DynamoDBTable[RunningJob]) {

  implicit val dateFormat = DynamoFormat.coercedXmap[DateTime, String, IllegalArgumentException](DateTime.parse(_).withZone(DateTimeZone.UTC))(_.toString)

  def createRunningJob(runningJob: RunningJob): Unit = runningJobTable.saveItem(runningJob)
  def updateRunningJob(id: String, environment: String, runningJob: RunningJob): Unit = runningJobTable.updateItem(id, environment, runningJob)
  def removeRunningJob(id: String, environment: String): Unit = runningJobTable.deleteItem(id, environment)
  def getAllRunningJobs(): Future[Seq[RunningJob]] = runningJobTable.getAll()
  def getRunningJobsForContentSource(contentSourceId: String): Future[List[RunningJob]] = runningJobTable.getItems(contentSourceId)

  def getRunningJob(contentSourceId: String, contentSourceEnvironment: String): Either[CustomError, RunningJob] =
    runningJobTable.getItem[RunningJob](contentSourceId, contentSourceEnvironment) match {
      case Some(result) => result.leftMap(err => RunningJobNotFound(s"A running job for content source with id: $contentSourceId and environment: $contentSourceEnvironment does not exist."))
      case None => Left(ContentSourceNotFound(s"A running job for content source with id: $contentSourceId and environment: $contentSourceEnvironment does not exist."))
    }
}
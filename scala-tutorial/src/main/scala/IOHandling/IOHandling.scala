package IOHandling
import schedulingmeetings.SchedulingMeetingsAPI.calendarEntriesApiCall
import schedulingmeetings.SchedulingMeetingsAPI
import castingdieimpure.CastingDieImpure.castTheDieImpureNofailures
import castingdieimpure.CastingDieImpure.castTheDieImpureWithFailures
import schedulingmeetingsimpure.SchedulingMeetingsImpure



import scala.util.Random
import cats.effect.IO

case class MeetingTime(startHour: Int, endHour: Int)
// The method calendarEntriesApiCall(name) is returning a java.util.List[schedulingmeetingsimpure.SchedulingMeetingsImpure.MeetingTime], while your Scala code is expecting a IO[List[MeetingTime]]
// To resolve this issue, you'll need to convert the returned Java List to a Scala List of MeetingTime objects in your calendarEntries method. Here's an example of how you might do that using Scala's JavaConverters:
def calendarEntries(name: String): IO[List[MeetingTime]] = {
  // the .asScala here is something called Implicit Conversion
  import scala.jdk.CollectionConverters._
  IO.delay(calendarEntriesApiCall(name).asScala.toList.map { mt =>
    MeetingTime(mt.startHour, mt.endHour)
  })
}

def castTheDie(): IO[Int] = IO.delay(castTheDieImpureWithFailures())

def castTheDieTwice(): IO[Int] = {
  for {
    firstCast <- castTheDie()
    secondCast <- castTheDie()
  } yield firstCast + secondCast
}

def createMeetingApiCall(names: List[String], meetingTime: MeetingTime): Unit = {
  import scala.collection.JavaConverters._
  SchedulingMeetingsAPI.createMeetingApiCall(names.asJava, SchedulingMeetingsImpure.MeetingTime(meetingTime.startHour, meetingTime.endHour))
}

def createMeeting(names: List[String], meetingTime: MeetingTime): IO[Unit] = {
  IO.delay(createMeetingApiCall(names, meetingTime))
}

def scheduledMeetings(person1: String, person2: String): IO[List[MeetingTime]] = {
  for {
    person1Entries <- calendarEntries(person1)
    person2Entries <- calendarEntries(person2)
  } yield person1Entries.appendedAll(person2Entries)
}
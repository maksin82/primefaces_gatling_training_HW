package perf.load.scenarios

import io.gatling.core.Predef.scenario
import io.gatling.core.structure.ScenarioBuilder
import perf.load.cases._
import scala.util.Random
import scala.concurrent.duration._

object HttpDummyapiScenario {
  def apply(): ScenarioBuilder = new HttpDummyapiScenario().scn
}

class HttpDummyapiScenario {
  val feeder = Iterator.continually(
    Map("mail" -> s"Jakayla${Random.nextInt(100000)}@hotmail.com")
  )
  val scn: ScenarioBuilder = scenario("Http Dummyapi Scenario")
    .feed(feeder)
    .pace(2 seconds)
    .exec(HttpDummyapi.postCreate)
    .exec(HttpDummyapi.getSingleUser)
    .pause(3 second, 5 seconds)
    .exec(HttpDummyapi.putUser)
}

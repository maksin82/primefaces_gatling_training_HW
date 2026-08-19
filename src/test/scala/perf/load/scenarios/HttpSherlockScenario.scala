package perf.load.scenarios

import io.gatling.core.Predef.scenario
import io.gatling.core.structure.ScenarioBuilder
import perf.load.cases._
import io.gatling.core.Predef._

object HttpSherlockScenario {
  def apply(): ScenarioBuilder = new HttpSherlockScenario().scn
}

class HttpSherlockScenario {
  val users = csv("users.csv").circular

  val scn: ScenarioBuilder = scenario("Http Sherlock Scenario")
    .feed(users)
    .exec(HttpSherlock.postCreate)
    .exec(HttpSherlock.getSingleUser)

}

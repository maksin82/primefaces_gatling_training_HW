package perf.load.scenarios

import io.gatling.core.Predef.scenario
import io.gatling.core.structure.ScenarioBuilder
import perf.load.cases._

object HttpSherlockScenario {
  def apply(): ScenarioBuilder = new HttpSherlockScenario().scn
}

class HttpSherlockScenario {
  val scn: ScenarioBuilder = scenario("Http Sherlock Scenario")
    .exec(HttpSherlock.postCreate)
}

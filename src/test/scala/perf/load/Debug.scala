package perf.load

import io.gatling.core.Predef._
import perf.load.scenarios._

import scala.concurrent.duration.DurationInt

class Debug extends Simulation {
  setUp(
    HttpSherlockScenario()
      .inject(
        rampUsersPerSec(1).to(5).during(10 seconds),
        constantUsersPerSec(0).during(10),
        rampUsersPerSec(0).to(0).during(5)
      )
  ).protocols(
    httpProtocol,
  ).maxDuration(25)
}

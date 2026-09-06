package perf.load.scenarios

import io.gatling.commons.validation.{Failure, Success}
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import perf.load.cases._
/*
 *

 */
object HttpRickandmortyScenario {
  def apply(): ScenarioBuilder = new HttpRickandmortyScenario().scn
}

class HttpRickandmortyScenario {
  val randomId = scala.util.Random.nextInt(126) + 1

  val scn: ScenarioBuilder = scenario("Http Rickandmorty Scenario")
    .exec(HttpRickandmorty.getUser)
    .foreach("#{characterIds}", "charId") {
      exec(HttpRickandmorty.getUserById)
        .exec(session => {
          session("characterName").validate[String] match {
            case Success(name) =>
              println(s"[INFO] Character: $name")
              session
            case Failure(_)    =>
              println(s"[WARN] Character name not found")
              session.set("characterName", "Unknown")
          }
        })
    }

    .exec { session =>
      val locationIds = Map("locationId" -> randomId.toString)
      session.set("locData", locationIds)
    }

    .exec(HttpRickandmorty.getLocationById)
    .exec(session => {
      session("locationName").asOption[String] match {
        case Some(name) => println(s"[INFO] Location: $name")
        case None       => println(s"[WARN] Location name not found")
      }
      session
    })
}

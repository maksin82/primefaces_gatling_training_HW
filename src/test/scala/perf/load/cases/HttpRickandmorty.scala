package perf.load.cases

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

/*

 */

object HttpRickandmorty {
  val getUser: HttpRequestBuilder = http("GET character")
    .get("/api/character/1,183")
    .header("Accept", "application/json")
    .check(status is 200)
    .check(jsonPath("$[*].id").ofType[Int].findAll.saveAs("characterIds"))

  val getUserById: HttpRequestBuilder = http("GET character by id")
    .get("/api/character/#{charId}")
    .header("Accept", "application/json")
    .check(status is 200)
    .check(jsonPath("$.name").find.optional.saveAs("characterName"))

  val getLocationById: HttpRequestBuilder = http("GET location by id")
    .get(session =>
      s"/api/location/${session("locData").as[Map[String, String]].getOrElse("locationId", "1")}"
    )
    .check(status is 200)
    .check(jsonPath("$.name").find.optional.saveAs("locationName"))
}
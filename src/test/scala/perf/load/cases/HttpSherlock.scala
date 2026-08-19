package perf.load.cases

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object HttpSherlock {
  val postCreate: HttpRequestBuilder = http("POST user")
    .post("/api/token/")
    .header("Content-Type", "application/json")
    .header("Accept", "application/json")
    .body(
      StringBody(
        """
          {
            "username": "sherlock",
            "password":  "password1"
          }
        """
      ),
    )
    .asJson
    .check(status is 200)
    .check(jsonPath("$.user_id").saveAs("userId"))
    .check(jsonPath("$.access").saveAs("access"))

  val getSingleUser: HttpRequestBuilder = http("GET user")
    .get("/profile/#{userId}/")
    .header("Authorization", "Bearer #{access}")
    .header("Accept", "application/json")
    .check(status is 200)
    .check(jsonPath("$.flag").isNull)
    .check(jsonPath("$.is_privileged").is("false"))
}

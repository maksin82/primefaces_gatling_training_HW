package perf.load.cases

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object HttpDummyapi { // Взять 3 шага (GET + POST + PUT)
  val postCreate: HttpRequestBuilder = http("POST User")
    .post("/data/v1/user/create")
    .header("Content-Type", "application/x-www-form-urlencoded")
    .header("Accept", "application/json")
    .formParam("email", "Jakayla251@hotmail.com")
    .formParam("firstName", "Aurore")
    .formParam("lastName", "Abbott")
    .formParam("dateOfBirth", "Thu May 14 2026 13:04:34 GMT+0300 (Moscow Standard Time)")
    .formParam("phone", "612-548-4506")
    .formParam("gender", "female")
    .check(status is 200)
    .check(jsonPath("$.id").saveAs("userId"))


  val getSingleUser: HttpRequestBuilder = http("User Check")
    .get("/data/v1/user/#{userId}")
    .header("Accept", "application/json")
    .check(status is 200)

  val putUser: HttpRequestBuilder = http("Update User")
    .put("/data/v1/user/6a85afadd764c6d57fa7194b")
    .header("Accept", "application/json")
    .header("Content-Type", "application/json")
    .body(
      StringBody(
        """
          {
            "lastName": "NewLastName",
            "gender":  "male"
          }
        """
      )
    )
    .check(status is 200)
}

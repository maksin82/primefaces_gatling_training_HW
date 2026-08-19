package perf

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import perf.load.Utility.debugMemoryAndOpts
import org.galaxio.gatling.amqp.Predef._
import org.galaxio.gatling.amqp.protocol.AmqpProtocolBuilder
import org.galaxio.gatling.config.SimulationConfig._
import org.galaxio.gatling.jdbc.Predef._
import org.galaxio.gatling.jdbc.protocol.JdbcProtocolBuilder

import scala.concurrent.duration.DurationInt

package object load {

  if (sys.env.get("DEBUG").exists(_.equalsIgnoreCase("true")))
    debugMemoryAndOpts()

  // common http protocol params (eg headers, checks)
  val httpProtocol = http
    .baseUrl(
      baseUrl,
    )                                                                                // Here is the root for all relative URLs, located in simulation.conf file, or -DbaseUrl="" passed to test param
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
    .disableFollowRedirect

  val jdbcProtocol: JdbcProtocolBuilder = DB
    .url(getStringParam("dbUrl"))
    .username(getStringParam("dbUser"))
    .password(getStringParam("dbPassword"))
    .connectionTimeout(2.minute)

  val amqpHost: String     = getStringParam("amqpHost")
  val amqpPort: Int        = getIntParam("amqpPort")
  val amqpLogin: String    = getStringParam("amqpLogin")
  val amqpPassword: String = getStringParam("amqpPassword")

  val amqpProtocol: AmqpProtocolBuilder   = amqp
    .connectionFactory(
      rabbitmq
        .host(amqpHost)
        .port(amqpPort)
        .username(amqpLogin)
        .password(amqpPassword)
        .vhost("/"),
    )
    .replyTimeout(60000)
    .consumerThreadsCount(8)
    .usePersistentDeliveryMode
}

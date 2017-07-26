package se.bitly.webby.test

import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.unit.Async
import io.vertx.ext.unit.TestContext
import io.vertx.ext.unit.junit.VertxUnitRunner
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.net.ServerSocket

@RunWith(VertxUnitRunner::class)
class WebTest {
    // Make a random port available to start my verticle with, and also make the port variable available in the unit test
    val port: Int by lazy {
        val serverSocket = ServerSocket(0)
        val n = serverSocket.localPort
        serverSocket.close()
        n
    }
    lateinit var vertx: Vertx
    @Before fun setUp(ctx: TestContext) {
        vertx = Vertx.vertx()
        val options: DeploymentOptions = DeploymentOptions().setConfig(JsonObject().put("http.port", port))
        vertx.deployVerticle("se.bitly.webby.verticle.Web", options, ctx.asyncAssertSuccess())
    }

    @After fun tearDown(ctx: TestContext) {
        vertx.close()
    }

    @Test fun testGetHome(ctx: TestContext) {
        val async: Async = ctx.async()
        vertx.createHttpClient().getNow(port, "localhost", "/") { resp ->
            ctx.assertEquals(resp.statusCode(), 200, "GET / should be 200")
            async.complete()
        }
    }
}
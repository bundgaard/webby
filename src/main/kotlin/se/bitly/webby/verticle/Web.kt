package se.bitly.webby.verticle

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.ext.web.Router

class Web : AbstractVerticle() {

    override fun start(startFuture: Future<Void>?) {
        val httpServer = vertx.createHttpServer()
        val router = Router.router(vertx)
        router.get("/").handler { ctx ->
            ctx.response().end("Home")
        }
        httpServer.requestHandler({router.accept(it)}).listen(config().getInteger("http.port", 5000))
        startFuture?.complete()
    }
}
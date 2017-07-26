package se.bitly.webby

import io.vertx.core.Vertx

fun main(args: Array<String>){
    println("Webby")
    val vertx = Vertx.vertx()

    vertx.deployVerticle("se.bitly.webby.verticle.Web") {
        when {
            it.succeeded() -> {
                println("Deployment id ${it.result()}")
            }
            it.failed() -> {
                println("Failed to deploy; ${it.cause()}")
            }
        }
    }


}
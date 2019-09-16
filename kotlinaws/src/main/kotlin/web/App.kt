/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package web

import domain.billing.CreditCard
import domain.customer.Address
import domain.customer.Customer
import domain.order.Order
import domain.product.Product
import domain.product.ProductType
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.io.File


fun main(args: Array<String>) {
    val port = System.getenv("PORT")?.toInt() ?: 8080
    embeddedServer(Netty, port = port, module = Application::mainModule).start(wait = true)
}

fun Application.mainModule() {
    routing {
        get("/") {
            call.respondText(setup(), contentType = ContentType.Text.Plain)
        }

        get("/version") {
            val version = File("version.txt").readText()
            call.respondText(version, contentType = ContentType.Text.Plain)
        }
    }
}

fun setup() : String {
    orderSetup()
    return "Output can be viewed on console application"
}

fun orderSetup() {
    val address = Address(1234567)
    val customer = Customer("Mauricio", address)

    val shirt = Product("Flowered t-shirt", ProductType.PHYSICAL, 35.00)
    val netflix = Product("Familiar plan", ProductType.MEMBERSHIP, 29.90)
    val book = Product("The Hitchhiker's Guide to the Galaxy", ProductType.BOOK, 120.00)
    val music = Product("Stairway to Heaven", ProductType.DIGITAL, 5.00)

    val order = Order(1, customer, address)

    order.addProduct(shirt, 2)
    order.addProduct(netflix, 1)
    order.addProduct(book, 1)
    order.addProduct(music, 1)

    order.pay(CreditCard("43567890-987654367"))
}



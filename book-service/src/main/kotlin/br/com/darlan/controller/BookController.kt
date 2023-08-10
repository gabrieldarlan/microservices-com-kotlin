package br.com.darlan.controller

import br.com.darlan.model.Book
import br.com.darlan.proxy.CambioProxy
import br.com.darlan.repository.BookRepository
import br.com.darlan.response.Cambio
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import java.util.*

@RestController
@RequestMapping("book")
class BookController(
    private val environment: Environment,
    private val bookRepository: BookRepository,
    private val cambioProxy: CambioProxy
) {

    @GetMapping(value = ["v1/{id}/{currency}"])
    fun findV1(
        @PathVariable("id") id: Long,
        @PathVariable("currency") currency: String
    ): Book {
        val book = bookRepository.findById(id).orElseThrow { RuntimeException("Book not found") }

        val params = HashMap<String, String>()
        params["amount"] = book.price.toString()
        params["from"] = "USD"
        params["to"] = currency

        val response = RestTemplate()
            .getForEntity(
                "http://localhost:8001/cambio-service/{amount}/{from}/{to}",
                Cambio::class.java,
                params
            )

        val cambio = response.body

        val port = environment.getProperty("local.server.port")
        book.currency = currency
        book.enviroment = port
        book.price = cambio!!.convertedValue
        return book
    }

    @GetMapping(value = ["/{id}/{currency}"])
    fun find(
        @PathVariable("id") id: Long,
        @PathVariable("currency") currency: String
    ): Book {
        val book = bookRepository.findById(id).orElseThrow { RuntimeException("Book not found") }

        val cambio = cambioProxy.get(amount = book.price!!.toDouble(), from = "USD", to = currency)

        val port = environment.getProperty("local.server.port")
        book.currency = currency
        book.enviroment = "$port FEIGN"
        book.price = cambio!!.convertedValue
        return book
    }
}
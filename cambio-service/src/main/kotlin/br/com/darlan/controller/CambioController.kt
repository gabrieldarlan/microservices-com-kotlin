package br.com.darlan.controller

import br.com.darlan.model.Cambio
import br.com.darlan.repository.CambioRepository
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.math.RoundingMode

@RestController
@RequestMapping("cambio-service")
class CambioController(
    private var enviroment: Environment,
    private val repository: CambioRepository
) {

    @GetMapping(value = ["/{amount}/{from}/{to}"])
    fun get(
        @PathVariable("amount") amount: BigDecimal,
        @PathVariable("from") from: String,
        @PathVariable("to") to: String
    ): Cambio {
        val cambio = repository.findByFromAndTo(from, to) ?: throw RuntimeException("Currency Unsupported")
        val port = enviroment.getProperty("local.server.port")
        val conversionFactor = cambio.conversionFactor
        val convertedValue = conversionFactor.multiply(amount)
        cambio.convertedValue = convertedValue.setScale(2, RoundingMode.CEILING)
        cambio.enviroment = port

        return cambio
    }
}
package br.com.darlan.proxy

import br.com.darlan.response.Cambio
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "cambio-service", url = "localhost:8001")
interface CambioProxy {
    @GetMapping(value = ["/cambio-service/{amount}/{from}/{to}"])
    fun get(
        @PathVariable("amount") amount: Double,
        @PathVariable("from") from: String,
        @PathVariable("to") to: String
    ): Cambio?
}
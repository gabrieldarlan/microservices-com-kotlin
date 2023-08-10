package br.com.darlan.controller

import br.com.darlan.config.GreetingConfiguration
import br.com.darlan.model.Greeting
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
@RequestMapping
class GreetingController {
    @Autowired
    private lateinit var configuration: GreetingConfiguration
    val counter: AtomicLong = AtomicLong()

    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "") name: String?): Greeting {
        var valueName = name
        if (valueName!!.isEmpty()) valueName = configuration.defaultValue
        return Greeting(id = counter.getAndIncrement(), content = "${configuration.greeting}, $valueName")
    }
}
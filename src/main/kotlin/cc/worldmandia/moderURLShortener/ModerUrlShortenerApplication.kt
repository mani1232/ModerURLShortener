package cc.worldmandia.moderURLShortener

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(info = Info(title = "Url Shortener", version = "1.0", description = "Documentation APIs v1.0"))
class ModerUrlShortenerApplication : CommandLineRunner {
    override fun run(vararg args: String?) {

    }

}

fun main(args: Array<String>) {
    runApplication<ModerUrlShortenerApplication>(*args)
}
package cc.worldmandia.moderURLShortener

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity

@SpringBootApplication
@EnableWebFluxSecurity
class ModerUrlShortenerApplication : CommandLineRunner {
    override fun run(vararg args: String?) {

    }

}

fun main(args: Array<String>) {
    runApplication<ModerUrlShortenerApplication>(*args)
}
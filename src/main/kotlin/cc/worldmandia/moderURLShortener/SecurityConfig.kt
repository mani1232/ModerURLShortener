package cc.worldmandia.moderURLShortener


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher
import org.springframework.stereotype.Service
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.net.URI


@Configuration
@EnableWebFluxSecurity
class SecurityConfig(private val userRepo: UserServiceImpl) {
    @Order(1)
    @Bean
    fun apiHttpSecurity(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http {
            addFilterBefore(MyWebFilter(), SecurityWebFiltersOrder.SECURITY_CONTEXT_SERVER_WEB_EXCHANGE)
            securityMatcher(PathPatternParserServerWebExchangeMatcher("/api/v1/**"))
            csrf { disable() }
            authorizeExchange {
                authorize(anyExchange, permitAll)
            }
        }
    }

    @Bean
    @Order(2)
    fun webHttpSecurity(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http {
            authenticationManager = authenticationManager()
            authorizeExchange {
                authorize(antPattern = "/panel/**", authenticated)
                authorize(antPattern = "/**", permitAll)
            }
            formLogin {
                authenticationSuccessHandler = ServerAuthenticationSuccessHandler { webFilterExchange, _ ->
                    webFilterExchange.exchange.response.statusCode = HttpStatus.PERMANENT_REDIRECT
                    webFilterExchange.exchange.response.headers.location = URI.create("/panel/")
                    Mono.empty()
                }
            }
            logout {
                logoutSuccessHandler = ServerLogoutSuccessHandler { exchange, _ ->
                    exchange.exchange.response.statusCode = HttpStatus.PERMANENT_REDIRECT
                    exchange.exchange.response.headers.location = URI.create("/")
                    Mono.empty()
                }
            }
        }
    }

    @Bean
    fun authenticationManager(): ReactiveAuthenticationManager {
        val manager = UserDetailsRepositoryReactiveAuthenticationManager(DefaultReactiveAuthenticationManager(userRepo))
        manager.setPasswordEncoder(passwordEncoder())
        return manager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(10)
    }
}

@Service
class DefaultReactiveAuthenticationManager(private val userRepo: UserServiceImpl) : ReactiveUserDetailsService {
    override fun findByUsername(username: String?): Mono<UserDetails> {
        return UserDetailsImpl(
            userRepo.findByUserName(
                username ?: throw UsernameNotFoundException("User not found")
            )
        ).toMono()
    }

}

class UserDetailsImpl(private val user: UserDTO?) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableSetOf()
    }

    override fun getPassword(): String? {
        return user?.password
    }

    override fun getUsername(): String? {
        return user?.username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}

class MyWebFilter: WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {

        // return chain.filter(exchange) if valid

        exchange.response.statusCode = HttpStatus.UNAUTHORIZED
        return exchange.response.writeWith(Mono.just(exchange.response.bufferFactory().wrap("Wrong token".encodeToByteArray())))
    }
}
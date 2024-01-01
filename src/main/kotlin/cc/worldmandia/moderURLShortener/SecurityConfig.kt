package cc.worldmandia.moderURLShortener


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono


@Configuration
@EnableWebFluxSecurity
class SecurityConfig(private val userRepo: UserServiceImpl) {
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    fun apiHttpSecurity(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http {
            csrf { disable() }
            securityMatcher(PathPatternParserServerWebExchangeMatcher("/api/**"))
            authorizeExchange {
                authorize(anyExchange, permitAll)
            }
        }
    }

    @Bean
    fun webHttpSecurity(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http {
            authenticationManager = authManager()
            authorizeExchange {
                authorize(anyExchange, authenticated)
            }
            httpBasic { }
            formLogin {  }
        }
    }

    fun authManager(): ReactiveAuthenticationManager {
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
class DefaultReactiveAuthenticationManager(private val userRepo: UserServiceImpl): ReactiveUserDetailsService {
    override fun findByUsername(username: String?): Mono<UserDetails> {
        return UserDetailsImpl(userRepo.findByUserName(username!!)!!).toMono()
    }

}

class UserDetailsImpl(private val user: UserDTO): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableSetOf()
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.username
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
package ru.silonov.bing.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import ru.silonov.bing.security.JwtTokenFilter

@Configuration
class SecurityConfig(
    private val jwtTokenFilter: JwtTokenFilter // Custom JWT filter
) {

    companion object {
        val SWAGGER_WHITELIST = arrayOf(
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
        )
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder() // Use BCrypt for encoding passwords
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // Disable CSRF since JWTs are used
            .sessionManagement { sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No session
            }
            .authorizeHttpRequests { authRequests ->
                authRequests
                    .requestMatchers("/auth/**").permitAll()  // Open access to authentication endpoints
                    .requestMatchers(*SWAGGER_WHITELIST).permitAll()
                    .anyRequest().authenticated()  // Protect all other endpoints
            }
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java) // Add JWT filter

        return http.build() // Use 'http.build()' instead of deprecated 'http.getOrBuild()'
    }

    // AuthenticationManager bean is required to handle authentication
    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }
}

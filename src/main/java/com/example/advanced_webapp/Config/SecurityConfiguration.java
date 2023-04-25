package com.example.advanced_webapp.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    // final : automatically injected by spring
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    // responsible for configuring all http security of our application

    // because JWT tokens are passed in the header of the request, rather than in a cookie.
    // Since JWT tokens are stored in the header,
    // they are not susceptible to being intercepted by a malicious website.
    // Thus, CSRF protection is not needed. - disable csrf

    // session state is not stored, and each request needs to be authenticated
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers("/v1/app/auth/", "/v1/app/auth/confirm*","/v1/app/auth/register*", "/v1/app/auth/authenticate*", "/v1/app/auth/verify**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterAfter(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}

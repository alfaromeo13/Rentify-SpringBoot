package com.example.rentify.security.config;

import com.example.rentify.security.exception.HttpUnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final HttpUnauthorizedException httpUnauthorizedException;

    @Override
    @SneakyThrows
    protected void configure(HttpSecurity http) {
        http
                .exceptionHandling().authenticationEntryPoint(httpUnauthorizedException)
                .and()
                .headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/messages/**").hasAnyRole("registered user", "admin") //Users with these roles hava access
                .antMatchers(HttpMethod.DELETE, "/api/country/**").hasRole("admin")//Delete on this api can oly do ADMIN
                .antMatchers("/api/**").permitAll() //any other api is available! (We won't define rules for all apis)
                .anyRequest().authenticated();//apis not beginning with /api can be accessed only with authenticated users
    }
}
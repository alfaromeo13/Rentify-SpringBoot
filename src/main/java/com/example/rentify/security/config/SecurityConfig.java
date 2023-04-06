package com.example.rentify.security.config;

import com.example.rentify.security.exception.HttpUnauthorizedEntryPoint;
import com.example.rentify.security.jwt.JwtFilter;
import com.example.rentify.security.jwt.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtFilter jwtFilter;
    private final UserDetailsServiceImpl userDetailsService;
    private final HttpUnauthorizedEntryPoint httpUnauthorizedException;

    @Override
    @SneakyThrows //when users send login information
    public void configure(AuthenticationManagerBuilder auth) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        //with this we defined which password encoding will be used
    }

    @Override
    @SneakyThrows
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.OPTIONS);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    @SneakyThrows
    public AuthenticationManager authenticationManagerBean() {
        return super.authenticationManagerBean();
    }

    @Override
    @SneakyThrows
    protected void configure(HttpSecurity http) {
        http
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                //if user isn't authenticated we return our custom httpUnauthorizedException exception
                .exceptionHandling().authenticationEntryPoint(httpUnauthorizedException)
                .and()
                .headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/messages/**").hasAnyRole("registered user", "admin") //Users with these roles have access
                .antMatchers(HttpMethod.DELETE, "/api/country/**").hasRole("admin")//Delete on this api can oly do ADMIN
                .antMatchers("/api/authenticate/**").permitAll()
                .antMatchers("/api/**").authenticated() //with permitAll() any other api is available! (We won't define rules for all apis)
                .anyRequest().authenticated();
    }
}
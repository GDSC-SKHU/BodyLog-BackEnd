package com.solutionchallenge.bodylog.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final RedisTemplate redisTemplate;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/join","/").permitAll()
                .antMatchers("/log-out").authenticated()
//               .antMatchers("/user/**","/@**/add","/**/**/update", "/api/**","/").hasAnyRole("USER", "ADMIN")
               .antMatchers("/user/**","/**/add","/**/**/update","/**/**/delete", "/").hasAnyRole("USER", "ADMIN")

                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthFilter(tokenProvider, redisTemplate), UsernamePasswordAuthenticationFilter.class)
                .cors();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}

/*
    antMatchers : ?????? ???????????? ????????? ????????? ??????
    permitAll : antMatchers?????? ????????? ???????????? ????????? ???????????? ?????? ??????
    hasRole : ???????????? ????????? ?????? ????????? ?????? ???????????? ?????? ??????
    authenticated : ????????? ???????????? ????????? ??????
 */
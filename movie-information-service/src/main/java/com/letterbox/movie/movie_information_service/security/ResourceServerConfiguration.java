package com.letterbox.movie.movie_information_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.authorization.AuthorizationManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableWebSecurity
public class ResourceServerConfiguration {

    @Value("${user.ratings.service.ip:127.0.0.1}")
    private String userRatingsServiceIp;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authz -> {
                    RequestMatcher putRatingMatcher = AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/movies/{movieId}/rating");

                    AuthorizationManager<RequestAuthorizationContext> putRatingAuthorizationManager =
                            (authorization, context) -> {
                                HttpServletRequest request = context.getRequest();
                                String sourceIp = request.getRemoteAddr();
                                return new AuthorizationDecision(userRatingsServiceIp.equals(sourceIp));
                            };
                    authz.requestMatchers(putRatingMatcher).access(putRatingAuthorizationManager);
//                    authz.anyRequest().authenticated(); // For other requests but KIV
                })
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}

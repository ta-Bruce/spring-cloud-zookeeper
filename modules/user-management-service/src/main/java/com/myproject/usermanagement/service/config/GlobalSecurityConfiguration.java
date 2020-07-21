package com.myproject.usermanagement.service.config;

import com.myproject.common.security.api.exception.handler.JwtAccessDeniedHandler;
import com.myproject.common.security.api.filter.JwtAuthFilter;
import com.myproject.common.security.api.security.JwtAuthenticationEntryPoint;
import com.myproject.common.security.api.security.JwtAuthenticationProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@Getter
public class GlobalSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationProvider authenticationProvider;
    private final JwtAuthFilter authFilter;
    private final JwtAccessDeniedHandler accessDeniedHandler;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;


    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.eraseCredentials(true);
        auth.authenticationProvider(getAuthenticationProvider());
    }

    @Bean
    public FilterRegistrationBean registration(JwtAuthFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers( "/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(getAuthFilter(), SecurityContextHolderAwareRequestFilter.class)
                .exceptionHandling().accessDeniedHandler(getAccessDeniedHandler())
                .authenticationEntryPoint(getAuthenticationEntryPoint()).and().headers().cacheControl()
                .disable().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable().logout().disable().cors().configurationSource(source);
        httpSecurity.headers().frameOptions().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.POST, "/users/validate");
    }
}

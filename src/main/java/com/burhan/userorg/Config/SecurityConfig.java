
package com.burhan.userorg.Config;

import com.burhan.userorg.Security.JwtAuthEntryPoint;
import com.burhan.userorg.Security.JwtAuthenticationFilter;
import com.burhan.userorg.Security.LoginDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginDetailService loginDetailService;

    @Autowired
    private JwtAuthEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

/**
     * Here now spring security is fetching the data from the database.
     * By using the method loadbyUserName which is implemented in UserServiceImp class
     */

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

                http.csrf().disable()
                        .authorizeRequests()
                        .antMatchers("/api/v1/auth/login","/swagger-resources/**", "/swagger-ui/**", "/webjars/**").permitAll()
                        .antMatchers("/v3/api-docs").permitAll()
                        .antMatchers(HttpMethod.GET).permitAll()
                        // Configuration for UserController
                        .antMatchers(HttpMethod.DELETE, "/users/**").hasAnyRole("ADMIN")
                        .antMatchers(HttpMethod.POST, "/users/**").hasAnyRole("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/users/**").hasAnyRole("ADMIN")
                        .antMatchers(HttpMethod.GET,"/users/{id}").access("@userSecurity.hasUserId(authentication,#id)")
                        // Configuration for OrganizationController
                        .antMatchers(HttpMethod.DELETE, "/organization/**").hasAnyRole("ADMIN")
                        .antMatchers(HttpMethod.POST, "/organization/**").hasAnyRole("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/organization/**").hasAnyRole("ADMIN")

//                        .anyRequest().authenticated()
                .and()
                        .exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
                .and()
                        .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}


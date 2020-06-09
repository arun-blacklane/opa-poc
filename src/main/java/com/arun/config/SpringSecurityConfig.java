package com.arun.config;

import com.arun.voter.minute.MinuteBasedVoter;
import com.arun.voter.opa.OPAVoter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    // Create 2 users for demo
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER")
                .and()
                .withUser("admin").password("{noop}password").roles("ADMIN");

    }

    // Secure the endpoins with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/books/**").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST, "/books").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.PUT, "/books/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/books/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/books/**").hasRole("ADMIN")
                .accessDecisionManager(accessDecisionManager())
                .and()
                .csrf().disable()
                .formLogin().disable();
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        // @formatter: off
        List<AccessDecisionVoter<? extends Object>> decisionVoters = Arrays.asList(
                new WebExpressionVoter(), new RoleVoter(), new AuthenticatedVoter(),
        new OPAVoter("http://localhost:8181/v1/data/myapi/policy/allow"));
        // @formatter: on
        return new UnanimousBased(decisionVoters);
    }

}

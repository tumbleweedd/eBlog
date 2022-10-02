package com.example.eblog.config;

import com.example.eblog.enums.Role;
import com.example.eblog.exception.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint = new RestAuthenticationEntryPoint();

    public WebSecurityConfigurerImpl(@Lazy UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getEncoder());
    }

    //    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .mvcMatchers("/actuator/shutdown").permitAll()
//                .mvcMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
//                .mvcMatchers(HttpMethod.POST, "/api/auth/changepass").authenticated()
//                .mvcMatchers(HttpMethod.GET, "/api/empl/payment").hasAnyAuthority(Role.USER.name(), Role.ACCOUNTANT.name())
//                .mvcMatchers("/api/acct/payments").hasAuthority(Role.ACCOUNTANT.name())
//                .mvcMatchers("/api/admin/**").hasAuthority(Role.ADMINISTRATOR.name())
//                .mvcMatchers("/").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .csrf().disable().headers().frameOptions().disable()
//                .and()
//                .httpBasic()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .csrf().disable().headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                .mvcMatchers(HttpMethod.GET,"/api/users/userList").hasAuthority(Role.ADMIN.name())
                .mvcMatchers(HttpMethod.GET, "/api/users/me").authenticated()
                .mvcMatchers(HttpMethod.PUT,"/api/users/giveRole").hasAuthority(Role.ADMIN.name())
                .mvcMatchers(HttpMethod.DELETE,"/api/users/**").hasAnyAuthority(Role.ADMIN.name(),Role.USER.name())
                .mvcMatchers(HttpMethod.PUT,"/api/users/**").hasAnyAuthority(Role.ADMIN.name(),Role.USER.name())

                .mvcMatchers("/").permitAll()
                .and().exceptionHandling().accessDeniedPage("/access-denied")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}

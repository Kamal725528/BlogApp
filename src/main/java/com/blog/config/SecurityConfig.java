package com.blog.config;

import com.blog.security.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


    @Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public class SecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private CustomUserDetailService userDetailsService;
        @Bean
        PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }
        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception
        {
            return super.authenticationManagerBean();
        }
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                    .antMatchers("/api/auth/**").permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .httpBasic();
        }
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws  Exception {

            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        }



    //In memory Authentication

//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService(){
//        UserDetails kamal= User.builder().username("kamal").password(passwordEncoder().encode("test"))
//                           .roles("USER").build();
//
//        UserDetails admin= User.builder().username("admin").password(passwordEncoder().encode("test"))
//                .roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(kamal,admin);

//    }

}

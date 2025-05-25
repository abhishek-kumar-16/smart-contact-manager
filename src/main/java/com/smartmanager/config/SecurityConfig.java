package com.smartmanager.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.smartmanager.services.impl.SecurityCustomUserDetailServices;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration

public class SecurityConfig {
    // user creation and login using in memory authentication

    // @Bean
    // public UserDetailsService userDetailsService() {

    //     UserDetails user = User.withDefaultPasswordEncoder().username("admin").password("admin").roles("ADMIN", "USER").build();

    //     var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user);
    //     return inMemoryUserDetailsManager;
    // }

    @Autowired
    private SecurityCustomUserDetailServices securityCustomUserDetailServices;

    @Autowired
    private OauthSuccessHandler oauthSuccessHandler;
   
    //  security configuration of authentication provider
    @Bean
    public AuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider daoAuthenticationProvider=  new DaoAuthenticationProvider();
      // object of user details service needed
      daoAuthenticationProvider.setUserDetailsService(securityCustomUserDetailServices);
      // object of password encoder needed
      daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
      return daoAuthenticationProvider;
    }
   
   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

      

    httpSecurity.authorizeHttpRequests(authorize ->{
      // authorize.requestMatchers("/home","/signup").permitAll();
      authorize.requestMatchers("/user/**").authenticated(); // you need to login to access this url
      authorize.anyRequest().permitAll();
    });
    
    //form default login page
    httpSecurity.formLogin(formLogin ->{
      formLogin.loginPage("/login");
      formLogin.loginProcessingUrl("/authenticate"); // here the form will be submitted
      formLogin.successForwardUrl("/user/dashboard");
      formLogin.failureUrl("/login?error=true");
      formLogin.usernameParameter("email");
      formLogin.passwordParameter("password");

    });
  
    httpSecurity.csrf(csrf ->{
      csrf.disable(); // disable csrf for testing purpose
    });


    //  Oauth configuration
  httpSecurity.oauth2Login(oauth -> {
    oauth.loginPage("/login");
    oauth.successHandler(oauthSuccessHandler) ;
  });


  System.out.println("hey");

    httpSecurity.logout(logout ->{
      logout.logoutUrl("/logout");
      logout.logoutSuccessUrl("/login?logout=true");
    
    });
    
  

    return httpSecurity.build();
   }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

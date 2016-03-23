package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import data.entities.Role;
import business.api.Uris;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()//
                .antMatchers(Uris.SERVLET_MAP + Uris.TOKENS + "/**").authenticated()//
                .antMatchers(Uris.SERVLET_MAP + Uris.COURTS + "/**").hasRole(Role.ADMIN.name())//
                .antMatchers(HttpMethod.POST, Uris.SERVLET_MAP + Uris.RESERVES + "/**").hasRole(Role.PLAYER.name())//
                .antMatchers(HttpMethod.POST, Uris.SERVLET_MAP + Uris.TRAININGS).hasRole(Role.TRAINER.name())
                .antMatchers(HttpMethod.DELETE, Uris.SERVLET_MAP + Uris.TRAININGS ).hasRole(Role.TRAINER.name())
                .antMatchers(HttpMethod.GET, Uris.SERVLET_MAP + Uris.TRAININGS).hasRole(Role.PLAYER.name())
                .antMatchers(HttpMethod.PUT, Uris.SERVLET_MAP + Uris.TRAININGS + Uris.ID + Uris.PLAYERS).hasRole(Role.PLAYER.name())
                .antMatchers(HttpMethod.DELETE, Uris.SERVLET_MAP + Uris.TRAININGS + Uris.ID + Uris.PLAYERS).hasRole(Role.TRAINER.name())
                .and().httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

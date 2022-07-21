package ua.jupiter.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ua.jupiter.service.implementation.user.OAuth2UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    OAuth2UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/","/auth", "/login**", "/js/**", "/error**", "/oauth2/**").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and()
                .oauth2Login()
                .userInfoEndpoint().oidcUserService(userService)
                .and()
                .and()
                .csrf().disable();
    }
}
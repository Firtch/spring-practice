package dev.firtch.myloginapp.config;

import dev.firtch.myloginapp.service.ProfileService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final ProfileService profileService;

    public SpringSecurityConfig(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(profileService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login", "/signup", "/user/register/**", "/user/activate/**").anonymous()
                .antMatchers("/css/**", "/img/**").permitAll()
                .anyRequest().authenticated()
                .and()

                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/doLogin")
                    .defaultSuccessUrl("/user/info")


                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/doLogout", "POST"))

                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied")

                .and()
                .csrf().disable();
    }
}

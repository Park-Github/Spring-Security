package udemy.springsecurityapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import udemy.springsecurityapp.exception_handling.CustomAccessDeniedHandler;
import udemy.springsecurityapp.exception_handling.CustomBasicAuthenticationEntryPoint;

import static org.springframework.security.config.Customizer.withDefaults;

@Profile("prod")
@Configuration
public class ProdSecurityConfig {

    @Bean
    SecurityFilterChain prodSecurityFilterChain(HttpSecurity http) throws Exception {
        http    .sessionManagement(smc -> smc.invalidSessionUrl("/invalidSession")
                        .maximumSessions(1))
                .requiresChannel(rcc -> rcc.anyRequest().requiresSecure())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) ->
                requests.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
                        .requestMatchers("/notices", "contact" ,"/error", "/register").permitAll());
        http.formLogin(withDefaults());
        http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

}
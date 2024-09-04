package com.stockmanagement.StockManagement.config;

import com.stockmanagement.StockManagement.security.JwtAuthFilter;
import com.stockmanagement.StockManagement.service.impl.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final UserService userService;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserService userService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userService = userService;
    }

    // SecurityFilterChain bean'i, HTTP güvenlik yapılandırmalarını içerir.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(x -> x.disable())  // CSRF koruması devre dışı bırakılır.
                .authorizeHttpRequests(x -> x
                        .requestMatchers("/auth/**").permitAll()  // '/auth/**' altındaki URL'ler herkese açık.
                        .requestMatchers("/dashboards/index").hasRole("ADMIN")  // '/dashboards/index' sadece ADMIN rolüne sahip kullanıcılar için erişilebilir.
                        .anyRequest().authenticated()  // Diğer tüm istekler kimlik doğrulama gerektirir.
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/dashboards/index", true)
                        .permitAll()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/access-denied")  // Erişim engellendi sayfasına yönlendirme
                )
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authenticationProvider(authenticationProvider())  // AuthenticationProvider tanımlanır.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)  // JWT filtreleme, UsernamePasswordAuthenticationFilter'dan önce uygulanır.
                .build();  // Konfigürasyon tamamlanır ve SecurityFilterChain döndürülür.
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);  // Kullanıcı detay servisi olarak UserService kullanılır.
        authProvider.setPasswordEncoder(passwordEncoder());  // Şifre kodlayıcı olarak PasswordEncoder kullanılır.
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}

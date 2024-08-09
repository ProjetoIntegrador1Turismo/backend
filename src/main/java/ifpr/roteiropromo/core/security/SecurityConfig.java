package ifpr.roteiropromo.core.security;


import ifpr.roteiropromo.core.utils.LoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Profile(value = {"!application-test"})
class SecurityConfig {

    //Configuração da cadeia de segurança. Foi desabilitada a exigencia de authenticação de forma geral.
    //A necessidade de autenticação, bem como, a permissão, será realizada com a anotação @PreAuthorize("hasRole('ADMIN')")
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers("/file/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/interestpoint").permitAll()
                        .requestMatchers(HttpMethod.GET, "/paginated/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/page-source/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/uploads/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(new JwtConverter()
                                )
                        )
                )
                .addFilterBefore(new LoggingFilter(), org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }






}



//http.authorizeHttpRequests(auth -> auth
//        .requestMatchers(new AntPathRequestMatcher("/itinerary"))
//        .authenticated()
//        .requestMatchers(new AntPathRequestMatcher("/authentication"))
//        .permitAll());
//
//        http.oauth2ResourceServer((oauth2) -> oauth2
//        .jwt(Customizer.withDefaults()));
//
//        return http.build();


/* Metodo original
* public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(
                        jwt -> jwt.jwtAuthenticationConverter(new JwtConverter())
                        )
                );
        return http.build();
    }
*
*
*
*
* */
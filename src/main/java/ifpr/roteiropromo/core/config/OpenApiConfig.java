package ifpr.roteiropromo.core.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(name = "bearerAuth", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT")
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TourLink API")
                        .version("v1")
                        .description("Resource server from TourLink"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .servers(List.of(
                        new Server().url("https://api2.tourlink.com.br").description("Servidor seguro (HTTPS)"),
                        new Server().url("http://api2.tourlink.com.br").description("Servidor HTTP (não seguro)"),
                        new Server().url("http://localhost:8081").description("Ambiente local")
                ));
    }
}

//@Bean
//public OpenAPI customOpenAPI() {
//    return new OpenAPI()
//            .info(new Info()
//                    .title("TourLink API")
//                    .version("v1")
//                    .description("Resource server from TourLink"))
//            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
//}
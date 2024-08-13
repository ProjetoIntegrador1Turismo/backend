package ifpr.roteiropromo;

import ifpr.roteiropromo.core.config.EnvConfig;
import ifpr.roteiropromo.core.utils.JwtTokenHandler;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RoteiropromoApplication {

	public static void main(String[] args) {
		EnvConfig.loadEnvFile();
		SpringApplication.run(RoteiropromoApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return mapper;
	}



}

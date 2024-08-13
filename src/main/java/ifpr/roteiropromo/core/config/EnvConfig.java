package ifpr.roteiropromo.core.config;


import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {


    public static void loadEnvFile(){
        Dotenv dotenv = Dotenv.configure().load();

        String dataBaseUrl = dotenv.get("DB_URL");
        String dbUser = dotenv.get("DB_USERNAME");
        String dbPassword = dotenv.get("DB_PASSWORD");
        String keycloackClientId = dotenv.get("CLIENT_ID");
        String keycloackAdminName = dotenv.get("KC_ADMIN_NAME");
        String keycloackAdminPassword = dotenv.get("KC_ADMIN_PASSWORD");


        System.setProperty("DB_USERNAME", dbUser);
        System.setProperty("DB_PASSWORD", dbPassword);
        System.setProperty("DB_URL", dataBaseUrl);
        System.setProperty("CLIENT_ID", keycloackClientId);
        System.setProperty("KC_ADMIN_NAME", keycloackAdminName);
        System.setProperty("KC_ADMIN_PASSWORD", keycloackAdminPassword);

    }


}

package technikum.at.tourplanner_swen2_team5.util;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.PropertyBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import technikum.at.tourplanner_swen2_team5.View.controllers.HomeScreenController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
@Component
public class ApplicationContext {

    // Basispfad zu den FXML-Dateien im Ressourcenverzeichnis
    public static String FXML_BASE_PATH = "/technikum/at/tourplanner_swen2_team5/";

    @Getter
    private static String apiKeyOrs;
    @Getter
    private static String apiKeyMb;

    @Autowired
    private ConfigHandler configHandler;

    @Getter
    @Setter
    private static HomeScreenController homeScreenController;

    @PostConstruct
    public void init() {
        apiKeyOrs = loadApiKey(configHandler.getApiKeyOrsPath());
        apiKeyMb = loadApiKey(configHandler.getApiKeyMbPath());
    }

    private String loadApiKey(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.readLine().trim();
        } catch (IOException e) {
            log.error("Failed to read API key file: {}", filePath, e);
            return null;
        }
    }
}

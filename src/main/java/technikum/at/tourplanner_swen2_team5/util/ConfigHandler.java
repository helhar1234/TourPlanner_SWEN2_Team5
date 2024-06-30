package technikum.at.tourplanner_swen2_team5.util;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class ConfigHandler {

    @Value("${api.key.ors.path}")
    private String apiKeyOrsPath;

    @Value("${api.key.mb.path}")
    private String apiKeyMbPath;

    private Properties configProps;

    @PostConstruct
    public void init() {
        configProps = new Properties();
        configProps.setProperty("api.key.ors.path", apiKeyOrsPath);
        configProps.setProperty("api.key.mb.path", apiKeyMbPath);

        log.info("API Key paths initialized.");
    }

    public String getApiKeyOrsPath() {
        log.info("API Key ors path initialized: {}", apiKeyOrsPath);
        return configProps.getProperty("api.key.ors.path");
    }

    public String getApiKeyMbPath() {
        return configProps.getProperty("api.key.mb.path");
    }
}

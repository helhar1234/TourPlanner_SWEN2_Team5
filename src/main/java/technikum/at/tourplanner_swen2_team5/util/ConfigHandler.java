package technikum.at.tourplanner_swen2_team5.util;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.List;

@Slf4j
public class ConfigHandler {
    private Properties configProps;

    public ConfigHandler() {
        configProps = new Properties();
    }

    public boolean checkConfig(String fileName, List<String> requiredParams) {
        try {
            // Laden der Properties-Datei aus den Ressourcen
            InputStream inputStream = this.getClass().getResourceAsStream(fileName);
            if (inputStream == null) {
                log.error("Konfigurationsdatei '" + fileName + "' nicht gefunden.");
                return false;
            }

            configProps.load(inputStream);
            inputStream.close();  // Wichtig: Schließen des InputStreams nach Gebrauch

            // Überprüfung, ob alle notwendigen Schlüssel vorhanden sind
            for (String key : requiredParams) {
                if (!configProps.containsKey(key)) {
                    log.error("Fehlender erforderlicher Konfigurationsschlüssel: " + key);
                    return false;
                }
            }

            log.info("Konfigurationsdatei '" + fileName + "' und alle Keys gefunden.");
            return true;
        } catch (Exception e) {
            log.error("Fehler beim Laden der Konfiguration: " + e.getMessage());
            return false;
        }
    }

    public String getConfigValue(String key) {
        return configProps.getProperty(key);
    }
}


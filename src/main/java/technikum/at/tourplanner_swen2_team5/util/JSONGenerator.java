package technikum.at.tourplanner_swen2_team5.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.BL.validation.TourLogValidationService;
import technikum.at.tourplanner_swen2_team5.BL.validation.TourValidationService;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.TourLogViewModel;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.TourViewModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Slf4j
public class JSONGenerator {
    private ObjectMapper mapper;

    public JSONGenerator() {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void generateTourExportsJSON(TourModel tour, List<TourLogModel> tourLogs) {
        String jsonContent = generateJSON(tour, tourLogs);
        if (jsonContent != null) {
            File file = chooseFileLocation(tour.getId());
            if (file != null) {
                saveJSONToFile(jsonContent, file);
            }
        }
    }

    private String generateJSON(TourModel tour, List<TourLogModel> tourLogs) {
        try {
            TourExport export = new TourExport(tour, tourLogs);
            return mapper.writeValueAsString(export);
        } catch (IOException e) {
            log.error("Error generating JSON: {}", e.getMessage());
            return null;
        }
    }

    private File chooseFileLocation(String id) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(id + "-tour.json");
        FileChooser.ExtensionFilter jsonFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(jsonFilter);
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            return file;
        } else {
            log.warn("User did not select a save location for the JSON");
            return null;
        }
    }

    private void saveJSONToFile(String jsonContent, File file) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jsonContent);
            log.info("Saved JSON to {}", file.getPath());
        } catch (IOException e) {
            log.error("Error saving JSON to file: {}", e.getMessage());
        }
    }

    public static class TourExport {
        public TourModel tour;
        public List<TourLogModel> logs;

        public TourExport() {}

        public TourExport(TourModel tour, List<TourLogModel> logs) {
            this.tour = tour;
            this.logs = logs;
        }
    }

    private static ObjectMapper objectMapper = new ObjectMapper();

<<<<<<< Updated upstream
    // Methode, um den File Explorer zu öffnen und JSON hochzuladen
    public static void uploadJSON(Stage stage) throws IOException {
=======
    public void uploadJSON(Stage stage) throws IOException {
>>>>>>> Stashed changes
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            TourExport tourExport = objectMapper.readValue(selectedFile, TourExport.class);
            TourViewModel tourViewModel = TourViewModel.getInstance();
            TourValidationService tourValidationService = new TourValidationService();
            if (tourValidationService.validateTour(tourExport.tour).isEmpty() && tourValidationService.validateNameExists(tourExport.tour.getName(), null).isEmpty()){
                String id = tourViewModel.addTour(tourExport.tour);
                TourLogViewModel tourLogViewModel = new TourLogViewModel();
                TourLogValidationService tourLogValidationService = new TourLogValidationService();
                for (TourLogModel log : tourExport.logs) {
                    if (tourLogValidationService.validateTourLog(log).isEmpty()){
                        log.setTour(tourViewModel.getTourById(id));
                        tourLogViewModel.addTourLogFromUpload(log);
                    }
                }

            } else {
                throw new IOException();
            }

        }
    }
}

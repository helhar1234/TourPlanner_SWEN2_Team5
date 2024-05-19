package technikum.at.tourplanner_swen2_team5.controller;

import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;
import technikum.at.tourplanner_swen2_team5.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.util.Formatter;
import technikum.at.tourplanner_swen2_team5.viewmodels.TourLogViewModel;

import java.io.IOException;
import java.net.URL;

public class TourLogListController {
    @FXML
    private TableView<TourLogModel> tourLogsTable;

    @FXML
    private TableColumn<TourLogModel, String> colDate;
    @FXML
    private TableColumn<TourLogModel, String> colTime;
    @FXML
    private TableColumn<TourLogModel, String> colComment;
    @FXML
    private TableColumn<TourLogModel, String> colDifficulty;
    @FXML
    private TableColumn<TourLogModel, Double> colDistance;
    @FXML
    private TableColumn<TourLogModel, String> colTotalTime;
    @FXML
    private TableColumn<TourLogModel, Integer> colRating;
    @FXML
    private TableColumn<TourLogModel, String> colTransportType;
    @FXML
    private TableColumn<TourLogModel, Void> colButtons;

    private TourLogViewModel tourLogViewModel;
    private Formatter formatter;
    private String tourId;

    public void initialize() {
        tourLogViewModel = TourLogViewModel.getInstance();
        formatter = new Formatter();

        colTransportType.setCellValueFactory(new PropertyValueFactory<>("transportType"));
        colTransportType.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    String imageName = "img/icons/" + item.toLowerCase() + "-icon.png";
                    URL resource = MainTourPlaner.class.getResource(imageName);

                    Image image = new Image(resource.toString());
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(20);
                    imageView.setFitHeight(20);
                    imageView.setPreserveRatio(true);
                    setGraphic(imageView);
                }
            }
        });

        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colComment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        colDifficulty.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        colDistance.setCellValueFactory(new PropertyValueFactory<>("distance"));
        colTotalTime.setCellValueFactory(new PropertyValueFactory<>("totalTime"));
        colRating.setCellValueFactory(new PropertyValueFactory<>("rating"));

        colButtons.setCellFactory(column -> new TableCell<TourLogModel, Void>() {
            private final HBox buttonContainer = new HBox(10);
            private Button editButton, downloadButton, trashButton;

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    TourLogModel tourLog = getTableRow().getItem();
                    editButton = createButton("edit", "#A4D65E", "#395C37", false, tourLog.getId());
                    downloadButton = createButton("download", "#A4D65E", "#395C37", false, tourLog.getId());
                    trashButton = createButton("trash", "#F44336", "#BB1B11", true, tourLog.getId());

                    buttonContainer.getChildren().setAll(editButton, downloadButton, trashButton);
                    buttonContainer.setAlignment(Pos.CENTER_RIGHT);
                    setGraphic(buttonContainer);
                }
            }
        });
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
        loadTourLogs();
    }

    private void loadTourLogs() {
        FilteredList<TourLogModel> filteredList = new FilteredList<>(tourLogViewModel.getTourLogs());
        filteredList.setPredicate(tourLog -> tourLog.getTourId().equals(tourId));
        tourLogsTable.setItems(filteredList);
    }

    private Button createButton(String iconName, String baseColor, String hoverColor, boolean isTrashButton, String tourLogId) {
        Button button = new Button();
        button.getStyleClass().add("icon-button-small");
        button.setUserData(tourLogId);

        String imageName = "img/icons/" + iconName.toLowerCase() + "-icon.png";
        URL resource = MainTourPlaner.class.getResource(imageName);
        Image icon = new Image(resource.toString());
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(20);
        iconView.setFitHeight(20);
        button.setGraphic(iconView);

        String borderColor = "black";
        String radius = "60";
        button.setStyle(String.format("-fx-background-color: %s; -fx-border-color: #202020; -fx-border-radius: %s; -fx-background-radius: %s;", baseColor, radius, radius));

        button.setOnMouseEntered(e -> button.setStyle(String.format("-fx-background-color: %s; -fx-border-color: %s; -fx-border-radius: %s; -fx-background-radius: %s;", hoverColor, borderColor, radius, radius)));
        button.setOnMouseExited(e -> button.setStyle(String.format("-fx-background-color: %s; -fx-border-color: %s; -fx-border-radius: %s; -fx-background-radius: %s;", baseColor, borderColor, radius, radius)));

        button.setOnAction(event -> {
            System.out.println("Button clicked for tourlog ID: " + button.getUserData());
            switch (iconName) {
                case "edit":
                    Platform.runLater(() -> {
                        onEditTourLogClicked(tourLogId);
                    });
                    break;
                case "download":
                    // TODO: Tour wird downgeloaded
                    break;
                case "trash":
                    Platform.runLater(() -> {
                        tourLogViewModel.deleteTourLogById(tourLogId);
                    });
                    break;
            }
        });

        return button;
    }

    private void onEditTourLogClicked(String id) {
        try {
            TourLogModel tourLog = tourLogViewModel.getTourLogById(id);
            FXMLLoader fxmlLoader = new FXMLLoader(MainTourPlaner.class.getResource("edit_tour_log.fxml"));
            Parent root = fxmlLoader.load(); // Lade die FXML und initialisiere den Controller

            EditTourLogController controller = fxmlLoader.getController();
            controller.setTourLog(tourLog);

            // Bildschirmgröße ermitteln
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double width = screenBounds.getWidth() * 0.8;
            double height = screenBounds.getHeight() * 0.8;

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Tour Log");
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Fenstergröße relativ zur Bildschirmgröße setzen
            stage.setWidth(width);
            stage.setHeight(height);

            stage.showAndWait(); // Zeige das Fenster und warte, bis es geschlossen wird
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

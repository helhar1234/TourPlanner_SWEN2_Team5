package technikum.at.tourplanner_swen2_team5.controller;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;
import technikum.at.tourplanner_swen2_team5.models.TourModel;
import technikum.at.tourplanner_swen2_team5.viewmodels.TourViewModel;

import java.io.IOException;
import java.net.URL;

public class TourListController {

    @FXML private TableView<TourModel> toursTable;

    @FXML private TableColumn<TourModel, String> colType;
    @FXML private TableColumn<TourModel, String> colName;
    @FXML private TableColumn<TourModel, String> colStart;
    @FXML private TableColumn<TourModel, String> colDestination;
    @FXML private TableColumn<TourModel, Double> colDistance;
    @FXML private TableColumn<TourModel, Integer> colTime;
    @FXML private TableColumn<TourModel, Void> colButtons;

    @FXML private ImageView reloadIcon;

    private TourViewModel viewModel;

    public void initialize() {
        viewModel = TourViewModel.getInstance();

        colType.setCellValueFactory(new PropertyValueFactory<>("transportType"));
        colType.setCellFactory(column -> new TableCell<>() {
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

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        colDistance.setCellValueFactory(new PropertyValueFactory<>("distance"));
        colDistance.setCellFactory(column -> new TableCell<TourModel, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(viewModel.formatDistance(item));
                }
            }
        });

        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colTime.setCellFactory(column -> new TableCell<TourModel, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(viewModel.formatTime(item)); // Nutze die formatTime Methode, um das Format zu definieren
                }
            }
        });

        colButtons.setCellFactory(column -> new TableCell<TourModel, Void>() {
            private final HBox buttonContainer = new HBox(10);
            private Button editButton, detailButton, downloadButton, trashButton;

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    TourModel tour = getTableRow().getItem();
                    editButton = createButton("edit", "#A4D65E", "#395C37", false, tour.getId());
                    detailButton = createButton("detail", "#A4D65E", "#395C37", false, tour.getId());
                    downloadButton = createButton("download", "#A4D65E", "#395C37", false, tour.getId());
                    trashButton = createButton("trash", "#F44336", "#BB1B11", true, tour.getId());

                    buttonContainer.getChildren().setAll(editButton, detailButton, downloadButton, trashButton);
                    buttonContainer.setAlignment(Pos.CENTER_RIGHT);
                    setGraphic(buttonContainer);
                }
            }
        });

        toursTable.setItems(viewModel.getTours());
    }

    private Button createButton(String iconName, String baseColor, String hoverColor, boolean isTrashButton, String tourId) {
        Button button = new Button();
        button.getStyleClass().add("icon-button-small");
        button.setUserData(tourId);

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
            System.out.println("Button clicked for tour ID: " + button.getUserData());
            switch (iconName){
                case "edit":
                    Platform.runLater(() -> {
                        onEditTourClicked(tourId);
                        });
                    break;
                case "detail":
                    // TODO: detail ansicht öffnet sich
                    break;
                case "download":
                    // TODO: Tour wird downgeloaded
                    break;
                case "trash":
                    Platform.runLater(() -> {
                        viewModel.deleteTourById(tourId);
                    });
                    break;
            }
        });

        return button;
    }

    private void onEditTourClicked(String id) {
        try {
            TourModel tour = viewModel.getTourById(id);
            FXMLLoader fxmlLoader = new FXMLLoader(MainTourPlaner.class.getResource("edit_tour.fxml"));
            Parent root = fxmlLoader.load(); // Lade die FXML und initialisiere den Controller

            EditTourController controller = fxmlLoader.getController();
            controller.setTour(tour);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Tour");
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Zeige das Fenster und warte, bis es geschlossen wird
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void onAddButtonClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainTourPlaner.class.getResource("add_tour.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add New Tour");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onRefreshButtonClicked(ActionEvent actionEvent) {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), reloadIcon);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(1);
        rotateTransition.play();

        initialize();

    }
}




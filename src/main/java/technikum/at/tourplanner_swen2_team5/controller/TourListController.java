package technikum.at.tourplanner_swen2_team5.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;
import technikum.at.tourplanner_swen2_team5.models.TourModel;
import technikum.at.tourplanner_swen2_team5.viewmodels.TourViewModel;

import java.net.URL;

public class TourListController {

    @FXML private TableView<TourModel> toursTable;

    @FXML private TableColumn<TourModel, String> colType;
    @FXML private TableColumn<TourModel, String> colName;
    @FXML private TableColumn<TourModel, String> colStart;
    @FXML private TableColumn<TourModel, String> colDestination;
    @FXML private TableColumn<TourModel, String> colDistance;
    @FXML private TableColumn<TourModel, String> colTime;
    @FXML private TableColumn<TourModel, Void> colButtons;


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
        colStart.setCellValueFactory(new PropertyValueFactory<>("from"));
        colDestination.setCellValueFactory(new PropertyValueFactory<>("to"));
        // colDistance.setCellValueFactory(new PropertyValueFactory<>("distance"));
        // colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        colButtons.setCellFactory(column -> new TableCell<TourModel, Void>() {
            private final HBox buttonContainer = new HBox(10);
            private final Button editButton = createButton("edit", "#A4D65E", "#395C37", false);
            private final Button detailButton = createButton("detail", "#A4D65E", "#395C37", false);
            private final Button downloadButton = createButton("download", "#A4D65E", "#395C37", false);
            private final Button trashButton = createButton("trash", "#F44336", "#BB1B11", true);

            {
                buttonContainer.setAlignment(Pos.CENTER_RIGHT);
                buttonContainer.getChildren().addAll(editButton, detailButton, downloadButton, trashButton);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buttonContainer);
                }
            }
        });


        toursTable.setItems(viewModel.getTours());
    }

    private Button createButton(String iconName, String baseColor, String hoverColor, boolean isTrashButton) {
        Button button = new Button();
        button.getStyleClass().add("icon-button-small"); // Standard CSS-Klasse

        String imageName = "img/icons/" + iconName.toLowerCase() + "-icon.png";
        URL resource = MainTourPlaner.class.getResource(imageName);
        Image icon = new Image(resource.toString());
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(20);
        iconView.setFitHeight(20);
        button.setGraphic(iconView);

        String borderColor = isTrashButton ? "#F44336" : "black";
        String radius = "50";
        button.setStyle(String.format("-fx-background-color: %s; -fx-border-color: #202020; -fx-border-radius: %s; -fx-background-radius: %s;", baseColor, radius, radius));

        button.setOnMouseEntered(e -> button.setStyle(String.format("-fx-background-color: %s; -fx-border-color: %s; -fx-border-radius: %s; -fx-background-radius: %s;", hoverColor, borderColor, radius, radius)));
        button.setOnMouseExited(e -> button.setStyle(String.format("-fx-background-color: %s; -fx-border-color: %s; -fx-border-radius: %s; -fx-background-radius: %s;", baseColor, borderColor, radius, radius)));

        return button;
    }

}




package technikum.at.tourplanner_swen2_team5.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.cell.PropertyValueFactory;
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

        toursTable.setItems(viewModel.getTours());
    }
}




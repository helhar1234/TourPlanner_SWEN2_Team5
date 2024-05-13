package technikum.at.tourplanner_swen2_team5.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;
import technikum.at.tourplanner_swen2_team5.models.MapModel;
import technikum.at.tourplanner_swen2_team5.models.TourModel;
import technikum.at.tourplanner_swen2_team5.util.ApplicationContext;
import technikum.at.tourplanner_swen2_team5.viewmodels.MapViewModel;
import technikum.at.tourplanner_swen2_team5.viewmodels.TourViewModel;

import java.io.IOException;
import java.net.URL;

public class TourDetailController {

    @FXML private Label titleLabel;
    @FXML private Label tourDescription;
    @FXML private Label tourName;
    @FXML private Label tourStart;
    @FXML private Label tourDestination;
    @FXML private Label tourTransportationType;
    @FXML private Label tourDistance;
    @FXML private Label tourTime;

    @FXML private ImageView mapImage;


    private TourViewModel tourViewModel;
    private MapViewModel mapViewModel;

    private TourModel currentTour;

    public void initialize (){
        tourViewModel = TourViewModel.getInstance();
        mapViewModel = MapViewModel.getInstance();
    }

    public void setTourDetails(TourModel tour) {
        this.currentTour = tour;
        titleLabel.setText(currentTour.getName());
        tourDescription.setText(currentTour.getDescription());
        tourStart.setText("Start: " + currentTour.getStart());
        tourDestination.setText("Destination: " + currentTour.getDestination());
        tourTransportationType.setText("Transportation Type: " + currentTour.getTransportationType());
        tourDistance.setText("Distance: " + tourViewModel.formatDistance(currentTour.getDistance()));
        tourTime.setText("Time: " + tourViewModel.formatTime(currentTour.getTime()));

        String filename = (mapViewModel.getMapById(currentTour.getId())).getFilename();
        String imageName = "img/maps/" + filename.toLowerCase();
        URL resource = MainTourPlaner.class.getResource(imageName);

        Image map = new Image(resource.toString());
        mapImage.setPreserveRatio(true);
        mapImage.setImage(map);

    }


    public void onAddLogButtonClicked(ActionEvent actionEvent) {

    }

    public void onEditButtonClicked(ActionEvent actionEvent) {
        try {
            TourModel tour = tourViewModel.getTourById(currentTour.getId());
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

    public void onBackButtonClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(MainTourPlaner.class.getResource("tour_list.fxml"));
            Node listView = loader.load();

            HomeScreenController homeController = ApplicationContext.getHomeScreenController();
            homeController.changeMainContent(listView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

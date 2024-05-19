package technikum.at.tourplanner_swen2_team5.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;
import technikum.at.tourplanner_swen2_team5.models.TourModel;
import technikum.at.tourplanner_swen2_team5.util.ApplicationContext;
import technikum.at.tourplanner_swen2_team5.util.Formatter;
import technikum.at.tourplanner_swen2_team5.viewmodels.MapViewModel;
import technikum.at.tourplanner_swen2_team5.viewmodels.TourViewModel;

import java.io.IOException;
import java.net.URL;

public class TourDetailController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label tourDescription;
    @FXML
    private Label tourName;
    @FXML
    private Label tourStart;
    @FXML
    private Label tourDestination;
    @FXML
    private Label tourTransportationType;
    @FXML
    private Label tourDistance;
    @FXML
    private Label tourTime;
    @FXML
    private ImageView mapImage;

    @FXML
    private TourLogListController tourLogListViewController;

    private TourViewModel tourViewModel;
    private MapViewModel mapViewModel;
    private Formatter formatter;

    private TourModel currentTour;

    public void initialize() {
        tourViewModel = TourViewModel.getInstance();
        mapViewModel = MapViewModel.getInstance();
        formatter = new Formatter();
    }

    public void setTourDetails(TourModel tour) {
        this.currentTour = tour;
        titleLabel.setText(currentTour.getName());
        tourDescription.setText(currentTour.getDescription());
        tourStart.setText("Start: " + currentTour.getStart());
        tourDestination.setText("Destination: " + currentTour.getDestination());
        tourTransportationType.setText("Transportation Type: " + currentTour.getTransportationType());
        tourDistance.setText("Distance: " + Formatter.formatDistance(currentTour.getDistance()));
        tourTime.setText("Time: " + formatter.formatTime(currentTour.getTime()));

        String filename = (mapViewModel.getMapById(currentTour.getId())).getFilename();
        String imageName = "img/maps/" + filename.toLowerCase();
        URL resource = MainTourPlaner.class.getResource(imageName);

        Image map = new Image(resource.toString());
        mapImage.setPreserveRatio(true);
        mapImage.setImage(map);

        // Set the tourId in the TourLogListController
        tourLogListViewController.setTourId(currentTour.getId());
    }

    public void onAddLogButtonClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainTourPlaner.class.getResource("add_tour_log.fxml"));
            Parent root = fxmlLoader.load();

            AddTourLogController addTourLogController = fxmlLoader.getController();
            TourModel currentTour = this.currentTour;
            addTourLogController.setTourLogTourId(currentTour.getId());

            // Bildschirmgröße ermitteln
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double width = screenBounds.getWidth() * 0.8;
            double height = screenBounds.getHeight() * 0.8;

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add New Tour Log");
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Fenstergröße relativ zur Bildschirmgröße setzen
            stage.setWidth(width);
            stage.setHeight(height);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

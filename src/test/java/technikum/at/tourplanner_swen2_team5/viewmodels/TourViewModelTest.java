package technikum.at.tourplanner_swen2_team5.viewmodels;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import technikum.at.tourplanner_swen2_team5.models.TourModel;

import static org.junit.jupiter.api.Assertions.*;

public class TourViewModelTest {

    private TourViewModel tourViewModel;

    @BeforeEach
    void setUp() {
        tourViewModel = TourViewModel.getInstance();
    }

    @Test
    void testAddTourSuccess() {
        // Arrange
        TourModel newTour = new TourModel("New Tour", "Description...", "Start", "Destination", "Transport", 10, 1);

        // Act
        tourViewModel.addTour(newTour);
        ObservableList<TourModel> tours = tourViewModel.getTours();

        // Assert
        assertTrue(tours.contains(newTour));
    }

    @Test
    void testAddTourFailure() {
        // Arrange
        TourModel newTour = new TourModel("New Tour", "Description...", "Start", "Destination", "Transport", 10, 1);
        TourModel newTourDupe = new TourModel("New Tour", "Description...", "Start", "Destination", "Transport", 10, 1);
        TourModel newTourNoName = new TourModel("", "Description...", "Start", "Destination", "Transport", 10, 1);
        TourModel newTourNoDescription = new TourModel("New Tour", "", "Start", "Destination", "Transport", 10, 1);
        TourModel newTourNoStart = new TourModel("New Tour", "Description...", "", "Destination", "Transport", 10, 1);
        TourModel newTourNoDestination = new TourModel("New Tour", "Description...", "Start", "", "Transport", 10, 1);
        TourModel newTourNoTransportType = new TourModel("New Tour", "Description...", "Start", "Destination", "", 10, 1);

        // Act
        tourViewModel.addTour(newTour);
        ObservableList<TourModel> tours = tourViewModel.getTours();

        // Assert
        assertFalse(tours.contains(newTourDupe));
        assertFalse(tours.contains(newTourNoName));
        assertFalse(tours.contains(newTourNoDescription));
        assertFalse(tours.contains(newTourNoStart));
        assertFalse(tours.contains(newTourNoDestination));
        assertFalse(tours.contains(newTourNoTransportType));
    }

    @Test
    void testUpdateTour() {
        // Arrange
        TourModel newTour = new TourModel("New Tour", "Description...", "Start", "Destination", "Transport", 10, 1);
        tourViewModel.addTour(newTour);

        String updatedName = "Updated Name";
        String updatedDescription = "Updated description";
        String updatedStart = "Updated Start";
        String updatedDestination = "Updated Destination";
        String updatedTransportType = "Updated TransportType";
        double updatedDistance = 20.0;
        int updatedTime = 2;

        // Act
        TourModel tourToUpdate = tourViewModel.getTours().get(0);

        tourToUpdate.setName(updatedName);
        tourToUpdate.setDescription(updatedDescription);
        tourToUpdate.setStart(updatedStart);
        tourToUpdate.setDestination(updatedDestination);
        tourToUpdate.setTransportType(updatedTransportType);
        tourToUpdate.setDistance(updatedDistance);
        tourToUpdate.setTime(updatedTime);

        tourViewModel.updateTour(tourToUpdate);
        TourModel updatedTour = tourViewModel.getTourById(tourToUpdate.getId());

        // Assert
        assertEquals(tourToUpdate, updatedTour);
    }

    @Test
    void testDeleteTourById() {
        // Arrange
        TourModel tourToDelete = tourViewModel.getTours().get(0);
        String tourId = tourToDelete.getId();

        // Act
        tourViewModel.deleteTourById(tourId);
        ObservableList<TourModel> tours = tourViewModel.getTours();

        // Assert
        assertFalse(tours.contains(tourToDelete));
    }

    @Test
    void testValidateNameSuccess() {
        // Arrange
        String validName = "Valid Tour Name";

        // Assert
        assertNull(tourViewModel.validateName(validName, null));
    }

    @Test
    void testValidateNameFailure() {
        // Arrange
        TourModel newTour = new TourModel("New Tour", "Description...", "Start", "Destination", "Transport", 10, 1);
        tourViewModel.addTour(newTour);

        String emptyName = "";
        String longName = "This is a very long tour name that exceeds the maximum allowed characters";
        String existingName = tourViewModel.getTourById(newTour.getId()).getName();
        String validName = "Valid Tour Name";

        // Assert
        assertEquals("Name cannot be empty", tourViewModel.validateName(emptyName, null));
        assertEquals("Name is too long (max 50 characters)", tourViewModel.validateName(longName, null));
        assertNotNull(existingName);
        assertEquals("Name already exists", tourViewModel.validateName(existingName, null));
        assertNull(tourViewModel.validateName(validName, null));
    }
}

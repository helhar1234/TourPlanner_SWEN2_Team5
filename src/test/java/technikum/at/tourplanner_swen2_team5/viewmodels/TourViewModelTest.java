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
    void testAddTour() {
        TourModel newTour = new TourModel("New Tour", "Description...", "Start", "Destination", "Transport");
        tourViewModel.addTour(newTour);
        ObservableList<TourModel> tours = tourViewModel.getTours();
        assertTrue(tours.contains(newTour));
    }

    @Test
    void testUpdateTour() {
        TourModel tourToUpdate = tourViewModel.getTours().get(0);
        String updatedDescription = "Updated description";
        tourToUpdate.setDescription(updatedDescription);
        tourViewModel.updateTour(tourToUpdate);
        TourModel updatedTour = tourViewModel.getTourById(tourToUpdate.getId());
        assertEquals(updatedDescription, updatedTour.getDescription());
    }

    @Test
    void testDeleteTourById() {
        TourModel tourToDelete = tourViewModel.getTours().get(0);
        String tourId = tourToDelete.getId();
        tourViewModel.deleteTourById(tourId);
        assertNull(tourViewModel.getTourById(tourId));
    }

    @Test
    void testValidateName() {
        // Test empty name
        String emptyName = "";
        assertEquals("Name cannot be empty", tourViewModel.validateName(emptyName, null));

        // Test name length
        String longName = "This is a very long tour name that exceeds the maximum allowed characters";
        assertEquals("Name is too long (max 50 characters)", tourViewModel.validateName(longName, null));

        // Test existing name
        String existingName = tourViewModel.getTours().get(0).getName();
        assertNotNull(existingName);
        assertEquals("Name already exists", tourViewModel.validateName(existingName, null));

        // Test valid name
        String validName = "Valid Tour Name";
        assertNull(tourViewModel.validateName(validName, null));
    }

}

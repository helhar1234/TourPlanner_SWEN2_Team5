package technikum.at.tourplanner_swen2_team5.BL.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TransportTypeModel;
import technikum.at.tourplanner_swen2_team5.BL.services.TourService;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TourValidationServiceTest {

    private TourValidationService tourValidationService;

    @BeforeEach
    void setUp() {
        TourService tourService = mock(TourService.class);
        tourValidationService = new TourValidationService(tourService);
    }

    @Test
    void testValidTour() {
        TourModel tour = new TourModel();
        TransportTypeModel transportType = mock(TransportTypeModel.class);

        tour.setName("New Tour");
        tour.setDescription("New Tour Description");
        tour.setStart("Frankfurt");
        tour.setDestination("Berlin");
        tour.setTransportType(transportType);

        Map<String, String> errors = tourValidationService.validateTour(tour);
        assertTrue(errors.isEmpty());
    }

    @Test
    void testEmptyTour() {
        TourModel tour = new TourModel();

        Map<String, String> errors = tourValidationService.validateTour(tour);
        assertEquals("Name cannot be empty.", errors.get("name"));
        assertEquals("Description cannot be empty.", errors.get("description"));
        assertEquals("Start location cannot be empty.", errors.get("start"));
        assertEquals("Destination cannot be empty.", errors.get("destination"));
        assertEquals("Transportation type must be selected.", errors.get("transportType"));
    }

    @Test
    void invalidTourName() {
        TourModel tour = new TourModel();

        tour.setName("New Tour with a name that is way too long and contains over 50 characters...");
        tour.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi " +
                "ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit " +
                "in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur " +
                "sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt " +
                "mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing " +
                "elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut " +
                "aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint " +
                "occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit " +
                "anim id est laborum.");
        tour.setStart("Tour Start that has more than 100 characters and is not even a location..............................");
        tour.setDestination("Tour Destination that has more than 100 characters and is not even a location........................");
        tour.setTransportType(null);

        Map<String, String> errors = tourValidationService.validateTour(tour);
        assertEquals("Name is too long (max 50 characters).", errors.get("name"));
        assertEquals("Description is too long (max 500 characters).", errors.get("description"));
        assertEquals("Start is too long (max 100 characters).", errors.get("start"));
        assertEquals("Destination is too long (max 100 characters).", errors.get("destination"));
        assertEquals("Transportation type must be selected.", errors.get("transportType"));
    }
}
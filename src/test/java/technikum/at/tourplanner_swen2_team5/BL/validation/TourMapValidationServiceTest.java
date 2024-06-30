package technikum.at.tourplanner_swen2_team5.BL.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import technikum.at.tourplanner_swen2_team5.util.MapRequester;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TourMapValidationServiceTest {

    private TourMapValidationService tourMapValidationService;
    private MapRequester mapRequester;

    @BeforeEach
    void setUp() {
        mapRequester = mock(MapRequester.class);
        tourMapValidationService = new TourMapValidationService(mapRequester);
    }

    @Test
    void testIsValidLocationSuccess() throws IOException {
        // Arrange
        String validLocation = "Vienna";
        when(mapRequester.geocode(validLocation)).thenReturn("someGeocodeData");

        // Act
        boolean result = tourMapValidationService.isValidLocation(validLocation);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsValidLocationFailure() throws IOException {
        // Arrange
        String invalidLocation = "InvalidLocation";
        when(mapRequester.geocode(invalidLocation)).thenReturn(null);

        // Act
        boolean result = tourMapValidationService.isValidLocation(invalidLocation);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsValidLocationThrowsIOException() throws IOException {
        // Arrange
        String location = "SomeLocation";
        when(mapRequester.geocode(location)).thenThrow(new IOException("Some IO exception"));

        // Act
        boolean result = tourMapValidationService.isValidLocation(location);

        // Assert
        assertFalse(result);
    }
}

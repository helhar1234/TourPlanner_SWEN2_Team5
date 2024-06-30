package technikum.at.tourplanner_swen2_team5.BL.services;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.DAL.repositories.TourDAO;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class TourServiceTest {

    @Mock
    private TourDAO tourDAO;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private TourService tourService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTours() {
        // Arrange
        TourModel tour1 = new TourModel();
        TourModel tour2 = new TourModel();
        List<TourModel> expectedTours = Arrays.asList(tour1, tour2);

        when(tourDAO.findAll()).thenReturn(expectedTours);

        // Act
        List<TourModel> actualTours = tourService.getAllTours();

        // Assert
        assertEquals(expectedTours, actualTours);
    }

    @Test
    void testGetTourById() {
        // Arrange
        TourModel expectedTour = new TourModel();
        String id = "some-uuid";
        when(tourDAO.findById(id)).thenReturn(Optional.of(expectedTour));

        // Act
        TourModel actualTour = tourService.getTourById(id);

        // Assert
        assertEquals(expectedTour, actualTour);
    }

    @Test
    void testGetTourById_NotFound() {
        // Arrange
        String id = "some-uuid";
        when(tourDAO.findById(id)).thenReturn(Optional.empty());

        // Act
        TourModel actualTour = tourService.getTourById(id);

        // Assert
        assertNull(actualTour);
    }

    @Test
    void testGetTourByName() {
        // Arrange
        TourModel expectedTour = new TourModel();
        String name = "some-name";
        when(tourDAO.findByName(name)).thenReturn(expectedTour);

        // Act
        TourModel actualTour = tourService.getTourByName(name);

        // Assert
        assertEquals(expectedTour, actualTour);
    }
}

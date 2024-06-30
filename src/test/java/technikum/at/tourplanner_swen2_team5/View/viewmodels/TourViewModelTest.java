package technikum.at.tourplanner_swen2_team5.View.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TransportTypeModel;
import technikum.at.tourplanner_swen2_team5.BL.services.MapService;
import technikum.at.tourplanner_swen2_team5.BL.services.TourLogService;
import technikum.at.tourplanner_swen2_team5.BL.services.TourService;
import technikum.at.tourplanner_swen2_team5.util.ChildFriendlinessCalculator;
import technikum.at.tourplanner_swen2_team5.util.MapRequester;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TourViewModelTest {

    @Mock
    private TourService tourService;

    @Mock
    private MapService mapService;

    @Mock
    private TourLogViewModel tourLogViewModel;

    @Mock
    private TourLogService tourLogService;

    @Mock
    private MapRequester mapRequester;

    @InjectMocks
    private TourViewModel tourViewModel;

    private TourModel tour;
    private TourLogModel tourLog;
    private TransportTypeModel transportType;

    @BeforeEach
    void setUp() {
        transportType = new TransportTypeModel();
        transportType.setName("Hike");

        tour = new TourModel();
        tour.setId(UUID.randomUUID().toString());
        tour.setName("Sample Tour");
        tour.setStart("Start Location");
        tour.setDestination("End Location");
        tour.setTransportType(transportType);

        tourLog = new TourLogModel();
        tourLog.setId(UUID.randomUUID().toString());
        tourLog.setTour(tour);

        when(tourService.getAllTours()).thenReturn(Collections.singletonList(tour));
    }

    @Test
    void testLoadTours() {
        ObservableList<TourModel> tours = tourViewModel.getTours();
        assertFalse(tours.isEmpty());
        assertEquals(1, tours.size());
        assertEquals("Sample Tour", tours.get(0).getName());
    }

    @Test
    void testAddTour() throws IOException {
        TourModel newTour = new TourModel();
        newTour.setName("New Tour");
        newTour.setStart("Start Location");
        newTour.setDestination("End Location");
        newTour.setTransportType(transportType);

        String tourId = tourViewModel.addTour(newTour);

        assertNotNull(tourId);
        verify(tourService, times(1)).addTour(any(TourModel.class));
        assertEquals(2, tourViewModel.getTours().size());
    }

    @Test
    void testDeleteTour() throws IOException {
        tourViewModel.deleteTour(tour);

        verify(mapService, times(1)).deleteExistingMaps(anyString());
        verify(mapService, times(1)).deleteMapById(anyString());
        verify(tourLogService, times(1)).deleteTourLog(any(TourLogModel.class));
        verify(tourService, times(1)).deleteTour(tour);
        assertTrue(tourViewModel.getTours().isEmpty());
    }

    @Test
    void testUpdateTour() throws IOException {
        tour.setStart("New Start Location");

        tourViewModel.updateTour(tour);

        verify(tourService, times(1)).updateTour(tour);
        assertEquals("New Start Location", tourViewModel.getTourById(tour.getId()).getStart());
    }
}

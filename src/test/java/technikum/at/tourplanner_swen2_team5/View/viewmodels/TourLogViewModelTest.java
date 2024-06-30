package technikum.at.tourplanner_swen2_team5.View.viewmodels;

import javafx.collections.transformation.FilteredList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.BL.services.TourLogService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TourLogViewModelTest {

    @Mock
    private TourLogService tourLogService;

    @InjectMocks
    private TourLogViewModel tourLogViewModel;

    private TourModel tour;
    private TourLogModel tourLog;

    @BeforeEach
    void setUp() {
        tour = new TourModel();
        tour.setId("tour1");

        tourLog = new TourLogModel();
        tourLog.setId("log1");
        tourLog.setTour(tour);

        when(tourLogService.getAllTourLogs()).thenReturn(Collections.singletonList(tourLog));
    }

    @Test
    void testAddTourLog() {
        doNothing().when(tourLogService).addTourLog(tourLog);
        when(tourLogService.getAllTourLogs()).thenReturn(Collections.singletonList(tourLog));

        tourLogViewModel.addTourLog(tourLog);

        verify(tourLogService, times(1)).addTourLog(tourLog);
        assertEquals(1, tourLogViewModel.getTourLogCountForTour("tour1"));
    }

    @Test
    void testDeleteTourLogById() {
        when(tourLogService.getTourLogById("log1")).thenReturn(tourLog);
        doNothing().when(tourLogService).deleteTourLog(tourLog);
        when(tourLogService.getAllTourLogs()).thenReturn(Collections.emptyList());

        tourLogViewModel.deleteTourLogById("log1");

        verify(tourLogService, times(1)).deleteTourLog(tourLog);
        assertEquals(0, tourLogViewModel.getTourLogCountForTour("tour1"));
    }

    @Test
    void testUpdateTourLog() {
        doNothing().when(tourLogService).updateTourLog(tourLog);
        when(tourLogService.getAllTourLogs()).thenReturn(Collections.singletonList(tourLog));

        tourLogViewModel.updateTourLog(tourLog);

        verify(tourLogService, times(1)).updateTourLog(tourLog);
        assertEquals(1, tourLogViewModel.getTourLogCountForTour("tour1"));
    }
}

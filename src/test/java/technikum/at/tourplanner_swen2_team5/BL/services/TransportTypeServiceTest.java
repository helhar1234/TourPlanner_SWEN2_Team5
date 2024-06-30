package technikum.at.tourplanner_swen2_team5.BL.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import technikum.at.tourplanner_swen2_team5.BL.models.TransportTypeModel;
import technikum.at.tourplanner_swen2_team5.DAL.repositories.TransportTypeDAO;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TransportTypeServiceTest {

    @Mock
    private TransportTypeDAO transportTypeDAO;

    @InjectMocks
    private TransportTypeService transportTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTransportTypes() {
        // Arrange
        TransportTypeModel type1 = new TransportTypeModel();
        TransportTypeModel type2 = new TransportTypeModel();
        List<TransportTypeModel> expectedTransportTypes = Arrays.asList(type1, type2);

        when(transportTypeDAO.findAll()).thenReturn(expectedTransportTypes);

        // Act
        List<TransportTypeModel> actualTransportTypes = transportTypeService.getAllTransportTypes();

        // Assert
        assertEquals(expectedTransportTypes, actualTransportTypes);
    }
}

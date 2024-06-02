package technikum.at.tourplanner_swen2_team5.BL.services;

import technikum.at.tourplanner_swen2_team5.BL.models.TransportTypeModel;
import technikum.at.tourplanner_swen2_team5.DAL.repositories.TransportTypeDAO;

import java.util.List;

public class TransportTypeService {
    private final TransportTypeDAO transportTypeDAO;

    public TransportTypeService() {
        this.transportTypeDAO = new TransportTypeDAO();
    }

    public List<TransportTypeModel> getAllTransportTypes() {
        return transportTypeDAO.findAll();
    }
}



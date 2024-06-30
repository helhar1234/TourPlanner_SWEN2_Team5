package technikum.at.tourplanner_swen2_team5.BL.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import technikum.at.tourplanner_swen2_team5.BL.models.TransportTypeModel;
import technikum.at.tourplanner_swen2_team5.DAL.repositories.TransportTypeDAO;

import java.util.List;

@Service
public class TransportTypeService implements ITransportTypeService {
    private TransportTypeDAO transportTypeDAO;

    @Autowired
    public TransportTypeService(TransportTypeDAO transportTypeDAO) {
        this.transportTypeDAO = transportTypeDAO;
    }

    @Override
    public List<TransportTypeModel> getAllTransportTypes() {
        return transportTypeDAO.findAll();
    }
}

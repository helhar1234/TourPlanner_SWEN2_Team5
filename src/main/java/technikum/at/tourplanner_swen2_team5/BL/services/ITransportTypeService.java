package technikum.at.tourplanner_swen2_team5.BL.services;

import technikum.at.tourplanner_swen2_team5.BL.models.TransportTypeModel;

import java.util.List;

public interface ITransportTypeService {
    List<TransportTypeModel> getAllTransportTypes();
}

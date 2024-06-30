package technikum.at.tourplanner_swen2_team5.search.bridge;

import org.hibernate.search.mapper.pojo.bridge.ValueBridge;
import org.hibernate.search.mapper.pojo.bridge.runtime.ValueBridgeFromIndexedValueContext;
import org.hibernate.search.mapper.pojo.bridge.runtime.ValueBridgeToIndexedValueContext;
import technikum.at.tourplanner_swen2_team5.BL.models.DifficultyModel;

public class DifficultyModelValueBridge implements ValueBridge<DifficultyModel, String> {

    @Override
    public String toIndexedValue(DifficultyModel value, ValueBridgeToIndexedValueContext context) {
        return value == null ? null : value.getDifficulty();
    }

    @Override
    public DifficultyModel fromIndexedValue(String value, ValueBridgeFromIndexedValueContext context) {
        DifficultyModel difficultyModel = new DifficultyModel();
        difficultyModel.setDifficulty(value);
        return difficultyModel;
    }
}

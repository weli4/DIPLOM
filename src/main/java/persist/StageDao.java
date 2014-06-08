package persist;

import entity.Stage;
import java.util.List;

public class StageDao extends AbstractDAO<Stage>{
    public Stage getById(Integer id) {
        return super.getById(id, Stage.class);
    }

    public List getAll() {
        return super.getAll(Stage.class);
    }
}

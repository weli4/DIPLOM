package persist;

import entity.Stage;
import java.util.List;
import javax.transaction.Transactional;

public class StageDao extends AbstractDAO<Stage>{
    @Transactional
    public Stage getById(Integer id) {
        return super.getById(id, Stage.class);
    }
@Transactional
    public List getAll() {
        return super.getAll(Stage.class);
    }
}

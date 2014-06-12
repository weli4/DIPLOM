package persist;

import entity.Project;
import java.util.List;
import javax.transaction.Transactional;

public class ProjectDao extends AbstractDAO<Project> {

    @Transactional
    public Project getById(Integer id) {
        return super.getById(id, Project.class);
    }

    @Transactional
    public List getAll() {
        return super.getAll(Project.class);
    }
}

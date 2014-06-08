package persist;

import entity.Project;
import java.util.List;

public class ProjectDao extends AbstractDAO<Project>{
    
    public Project getById(Integer id) {
        return super.getById(id, Project.class);
    }
    
    public List getAll(){
        return super.getAll(Project.class);
    }
}

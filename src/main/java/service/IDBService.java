package service;

import entity.Project;
import java.util.List;

public interface IDBService {
    public List<Project> getAllProjects();
    public Project getProject(Integer id);
    public Boolean addProject();
    public void updateProject(Project pr);
    public void deleteProject(Project pr);
}

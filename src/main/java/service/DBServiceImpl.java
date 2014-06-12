package service;

import entity.Project;
import entity.Stage;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import persist.ProjectDao;


@Service
public class DBServiceImpl{



    public List<Project> getAllProjects() {
        ProjectDao prjDao = new ProjectDao();
        List<Project> projects = prjDao.getAll(); // инициализация проектов  
        return  projects;
    }

   
    public Project getProject(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    public Boolean addProject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    public void updateProject(Project pr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    public void deleteProject(Project pr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

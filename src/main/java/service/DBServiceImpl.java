package service;

import entity.Project;
import entity.Stage;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import persist.ProjectDao;


@Service
public class DBServiceImpl implements IDBService{

    @Override
    public List<Project> getAllProjects() {
        ProjectDao prjDao = new ProjectDao();
        List<Project> projects = prjDao.getAll(); // инициализация проектов
        for (Project pr:projects){
            Hibernate.initialize(pr.getStages()); // инициализация стадий текущего проекта
            List<Stage> stages = pr.getStages();
            pr.setStages(stages);
            System.out.println("stages:"+stages.size()+"\t"+stages);
            for (Stage st:stages){
                Hibernate.initialize(st.getProcess()); // инициализация процессов текущей стадии
                List<entity.Process> procs = st.getProcess(); 
                st.setProcess(procs);
            }
        }
        return  projects;
    }

    @Override
    public Project getProject(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean addProject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateProject(Project pr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteProject(Project pr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

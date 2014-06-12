package service;

import entity.Project;
import entity.Process;
import entity.Stage;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import persist.ProcessDao;
import persist.ProjectDao;
import persist.StageDao;
import util.HibernateUtil;

@Service
public class DBServiceImpl {

    public List<Project> getAllProjects() {
        return new ProjectDao().getAll();
    }

    public Project getProject(Integer id) {
        return new ProjectDao().getById(id);
    }

    public Boolean addProject(Project pr) {

        try {
            ProjectDao pDao = new ProjectDao();
            pDao.add(pr);

            ProcessDao prcDao = new ProcessDao();
            StageDao dao = new StageDao();
            for (Stage stage : pr.getStages()) {
                stage.setProject(pr);
                dao.add(stage);
                for (Process prc : stage.getProcess()) {
                    prc.setStage(stage);
                    prcDao.add(prc);
                }
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public void updateProject(Project pr) {
        if (deleteProject(pr.getProject_id()))
            addProject(pr);
    }

    public Boolean deleteProject(Integer id) {
        Session session = null;
        Boolean deleted = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            try {
                String hql = "delete from entity.Project where project_id=" + id;
                Query query = session.createQuery(hql);
                query.executeUpdate();
                transaction.commit();
                deleted = true;
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
            return deleted;
        }
    }

}

package persist;

import entity.Project;
import entity.Stage;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

public class ProjectDao extends AbstractDAO<Project> {

    @Override
    public void add(Project object) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
            session.update(object);  
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Project getById(Integer id) {
        Session session = null;
        List projects = new ArrayList<Stage>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            String q = "from entity.Project where project_id = " + id;
            Query query = session.createQuery(q);
            projects = (List<Project>) query.list();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        if (projects.size() > 0) {
            StageDao stDao = new StageDao();
            ProcessDao prcDao = new ProcessDao();
            Project project = (Project) projects.get(0);
            project.setStages(stDao.getById(project.getProject_id())); // инициализация стадий
            for (Stage st : project.getStages()) {
                st.setProcess(prcDao.getById(st.getStage_id())); // инициализация процессов
            }
            return project;
        }
        return null;
    }

    public List getAll() {
        Session session = null;
        List<Project> objects = new ArrayList<Project>();
        try {
            StageDao stDao = new StageDao();
            ProcessDao prcDao = new ProcessDao();
            session = HibernateUtil.getSessionFactory().openSession();
            objects = (List<Project>) session.createCriteria(Project.class).list();
            for (Project pr : objects) {

                pr.setStages(stDao.getById(pr.getProject_id())); // инициализация стадий
                for (Stage st : pr.getStages()) {
                    st.setProcess(prcDao.getById(st.getStage_id())); // инициализация процессов
                }

            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return objects;
    }
}

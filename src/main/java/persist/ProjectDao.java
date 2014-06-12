package persist;

import entity.Project;
import entity.Stage;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import util.HibernateUtil;

public class ProjectDao extends AbstractDAO<Project> {

    public Project getById(Integer id) {
        Session session = null;
        Project object = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            object = (Project) session.load(Project.class, id);
            Stage st = (Stage) session.merge(object.getStages());
            Hibernate.initialize(object.getStages());
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return object;
    }

    public List getAll() {
        Session session = null;
        List<Project> objects = new ArrayList<Project>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            objects = session.createCriteria(Project.class).list();
            for (Project st : objects) {
                Hibernate.initialize(st.getStages());
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

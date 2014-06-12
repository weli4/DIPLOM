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
        return super.getById(id, Project.class);
    }

    public List getAll() {
        Session session = null;
        List<Project> objects = new ArrayList<Project>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            objects = (List<Project>) session.createCriteria(Project.class).list();
            for (Project pr : objects) {
                pr = (Project) session.merge(pr);
                Hibernate.initialize(pr.getStages()); // инициализация стадий текущего проекта          
                for (Stage st : pr.getStages()) {
                    Hibernate.initialize(st.getProcess());
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

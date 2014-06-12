package persist;

import entity.Stage;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import util.HibernateUtil;

public class StageDao extends AbstractDAO<Stage> {

    public Stage getById(Integer id) {
        Session session = null;
        Stage object = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            object = (Stage) session.load(Stage.class, id);
            Hibernate.initialize(object.getProcess());
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
        List<Stage> objects = new ArrayList<Stage>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            objects = session.createCriteria(Stage.class).list();
            for (Stage st : objects) {
                Hibernate.initialize(st.getProcess());
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

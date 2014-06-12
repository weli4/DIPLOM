package persist;

import entity.User;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import util.HibernateUtil;

public class UserDao extends AbstractDAO<User> {

    public User getById(Integer id) {
        Session session = null;
        User object = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            object = (User) session.load(User.class, id);
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
        List<User> objects = new ArrayList<User>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            objects = session.createCriteria(User.class).list();
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

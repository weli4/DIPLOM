package persist;

import entity.User;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

public class UserDao extends AbstractDAO<User> {

    public User getLogin(String username, String password) {
        Session session = null;
        List users = new ArrayList<User>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            String q = "from entity.User where username = '" + username + "' and password = '" + password+"'";
            Query query = session.createQuery(q);
            users = (List<User>) query.list();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        if (users.size() > 0) {
            return ((List<User>) users).get(0);
        }
        return null;
    }

    public User getById(Integer id) {
        return super.getById(id, User.class);
    }

    public List getAll() {
        return super.getAll(User.class);
    }
}

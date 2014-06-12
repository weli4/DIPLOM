package persist;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Session;
import util.HibernateUtil;

public abstract class AbstractDAO<E> {

    @Transactional
    public void add(E object) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Transactional
    public void update(E object) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(object);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Transactional
    protected E getById(Integer id, Class entityClass) {
        Session session = null;
        E object = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            object = (E) session.load(entityClass, id);
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return object;
    }

    @Transactional
    protected List<E> getAll(Class entityClass) {
        Session session = null;
        List<E> objects = new ArrayList<E>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            objects = session.createCriteria(entityClass).list();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return objects;
    }

    @Transactional
    public void delete(E object) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(object);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}

package persist;

import entity.Process;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import util.HibernateUtil;

public class ProcessDao extends AbstractDAO<Process> {

    @Override
    protected Process getById(Integer id) {
        Session session = null;
        Process object = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            object = (Process) session.load(Process.class, id);
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return object;
    }

    @Override
    protected List<Process> getAll() {
        Session session = null;
        List<Process> objects = new ArrayList<Process>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            objects = session.createCriteria(Process.class).list();
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

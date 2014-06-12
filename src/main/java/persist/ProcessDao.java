package persist;

import entity.Process;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

public class ProcessDao extends AbstractDAO<Process> {

    public List<Process> getById(Integer stage_id) {
        Session session = null;
        List processes = new ArrayList<Process>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            String q = "from entity.Process where stage_id = " + stage_id;
            Query query = session.createQuery(q);
            processes = (List<Process>) query.list();
            session.getTransaction().commit();
        } finally {
        if (session != null && session.isOpen()) {
            session.close();
            }
        }
        return (List<Process>)processes;
    }

    public List getAll() {
        return super.getAll(Process.class);
    }
}

package persist;

import entity.Stage;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

public class StageDao extends AbstractDAO<Stage> {

    public List<Stage> getById(Integer project_id) {
        Session session = null;
        List stages = new ArrayList<Stage>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            String q = "from entity.Stage where project_id = " + project_id;
            Query query = session.createQuery(q);
            stages = (List<Stage>) query.list();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return (List<Stage>) stages;
    }

    public List getAll() {
        ProcessDao prcDao = new ProcessDao();
        List<Stage> stages = super.getAll(Stage.class);
        for (Stage st : stages) {
            st.setProcess(prcDao.getById(st.getStage_id())); // инициализация процессов
        }
        return stages;
    }
}

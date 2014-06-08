package persist;

import entity.Process;
import java.util.List;

public class ProcessDao extends AbstractDAO<Process> {

    public Process getById(Integer id) {
        return super.getById(id, Process.class);
    }

    public List getAll() {
        return super.getAll(Process.class);
    }
}

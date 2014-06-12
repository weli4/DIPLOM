package persist;

import entity.User;
import java.util.List;
import javax.transaction.Transactional;

public class UserDao extends AbstractDAO<User> {

    @Transactional
    public User getById(Integer id) {
        return super.getById(id, User.class);
    }

    @Transactional
    public List getAll() {
        return super.getAll(User.class);
    }
}

package persist;

import entity.User;
import java.util.List;

public class UserDao extends AbstractDAO<User> {

    public User getById(Integer id) {
        return super.getById(id, User.class);
    }

    public List getAll() {
        return super.getAll(User.class);
    }
}

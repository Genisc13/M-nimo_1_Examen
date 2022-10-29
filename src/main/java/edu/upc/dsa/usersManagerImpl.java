package edu.upc.dsa;

import edu.upc.dsa.models.User;

import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

public class usersManagerImpl implements usersManager {
    private static usersManager instance;
    protected List<User> users;
    final static Logger logger = Logger.getLogger(usersManagerImpl.class);

    private usersManagerImpl() {
        this.users = new LinkedList<>();
    }

    public static usersManager getInstance() {
        if (instance==null) instance = new usersManagerImpl();
        return instance;
    }

    public int size() {
        int ret = this.users.size();
        logger.info("size " + ret);

        return ret;
    }

    public User addUser(User t) {
        logger.info("new Track " + t);

        this.users.add (t);
        logger.info("new Map added");
        return t;
    }

    public User addUser(String name, String surname, String birthday, String electronic, String password) {
        return this.addUser(new User(name, surname, birthday,electronic,password));
    }

    public User getUser(String id) {
        logger.info("getMap("+id+")");

        for (User t: this.users) {
            if (t.getId().equals(id)) {
                logger.info("getMap("+id+"): "+t);

                return t;
            }
        }

        logger.warn("not found " + id);
        return null;
    }

    public List<User> findAll() {
        return this.users;
    }

    @Override
    public void deleteUser(String id) {

        User t = this.getUser(id);
        if (t==null) {
            logger.warn("not found " + t);
        }
        else logger.info(t+" deleted ");

        this.users.remove(t);

    }

    @Override
    public User updateUser(User p) {
        User t = this.getUser(p.getId());

        if (t!=null) {
            logger.info(p+" rebut!!!! ");

            t.setSurname(p.getSurname());
            t.setName(p.getName());
            t.setBirthday(p.getBirthday());

            logger.info(t+" updated ");
        }
        else {
            logger.warn("not found "+p);
        }

        return t;
    }
}
package edu.upc.dsa;

import edu.upc.dsa.models.Objeto;
import edu.upc.dsa.models.User;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

public class shopManagerImpl implements shopManager {
    private static shopManager instance;
    protected List<User> users;

    protected List<Objeto> objects;
    final static Logger logger = Logger.getLogger(shopManagerImpl.class);

    private shopManagerImpl() {
        this.objects= new LinkedList<>();
        this.users = new LinkedList<>();
    }

    public static shopManager getInstance() {
        if (instance==null) instance = new shopManagerImpl();
        return instance;
    }

    public int sizeUsers() {
        int ret = this.users.size();
        logger.info("size " + ret);

        return ret;
    }

    public User addUser(User t) {
        logger.info("new User " + t);

        this.users.add (t);
        logger.info("new User added");
        return t;
    }
    public Objeto addObjeto(Objeto t) {
        logger.info("new Object " + t);

        this.objects.add (t);
        logger.info("new Object added");
        return t;
    }

    public User addUser(String name, String surname, String birthday, String electronic, String password) {
        return this.addUser(new User(name, surname, birthday,electronic,password));
    }

    public Objeto addObjeto(String name, String description, int coins){
        return this.addObjeto(new Objeto(name, description,coins));
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

    @Override
    public List<User> findUsers() {
        users.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

//        products.sort((o1, o2)-> Double.compare(o1.getPrice(), o2.getPrice()));
        return this.users;
    }

    @Override
    public List<Objeto> globalObjetos() {
        objects.sort(new Comparator<Objeto>() {
            @Override
            public int compare(Objeto o1, Objeto o2) {
                return Double.compare(o1.getCoins(), o2.getCoins());
            }
        });

//        products.sort((o1, o2)-> Double.compare(o1.getPrice(), o2.getPrice()));

        return objects;
    }
    @Override
    public int buscarUser(String id){
        boolean userencontrado=false;
        int indiceUser=0;
        for(int i = 0; !userencontrado || i<users.size(); i++) {
            if (id.equals(users.get(i).getId())){
                indiceUser=i;
                userencontrado=true;
            }
        }
        if (userencontrado){
            return indiceUser;
        }
        else{
            return -1;
        }
    }
    @Override
    public Objeto buyObjeto(String name,String userId) {
        Objeto res = new Objeto();
        int indiceUser=buscarUser(userId);
        if(indiceUser!=-1) {
            boolean objetoencontrado = false;
            for (int i = 0; !objetoencontrado || i < objects.size(); i++) {
                if (name.equals(objects.get(i).getName())) {
                    res = objects.remove(i);
                    objetoencontrado = true;
                }
            }
            if (objetoencontrado) {
                users.get(indiceUser).compraObjeto(res);
                return res;
            } else {
                return null;
            }
        }
        else {return null;}
    }


    @Override
    public List<Objeto> userObjetos(String id) {
        int indice= buscarUser(id);
        if (indice!=-1){
            return users.get(indice).getCompras();
        }
        else {return null;}
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
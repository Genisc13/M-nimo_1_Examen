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

    public shopManagerImpl() {
        this.objects= new LinkedList<>();
        this.users = new LinkedList<>();
    }

    public static shopManager getInstance() {
        if (instance==null) instance = new shopManagerImpl();
        return instance;
    }

    public User loginUser(String correo, String password){
        for (User t: this.users){
            if (t.getElectronic().equals(correo)){
                if(t.getPassword().equals(password)){
                    logger.info("El correo y la contraseña coinciden, bienvenido");
                    return t;
                }
                break;
            }
        }
        logger.warn("No se ha encontrado a ningun usuario registrado con esta combinación " + correo + "y "+password);
        return null;
    }

    public int sizeUsers() {
        int ret = this.users.size();
        logger.info("size " + ret);

        return ret;
    }
    public int sizeObjetos(){
        int ret=this.objects.size();
        logger.info("size "+ ret);
        return ret;
    }

    public User addUser(User t) {
        logger.info("new User " + t);
        for (User l: this.users){
            if (l.getElectronic().equals(t.getElectronic())){
                logger.warn("El usuario con este correo ya fue registrado");
                return null;
            }
        }
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

    public User addUser(String id,String name, String surname, String birthday, String electronic, String password) {
        return this.addUser(new User(id,name, surname, birthday,electronic,password));
    }

    public Objeto addObjeto(String name, String description, int coins){
        return this.addObjeto(new Objeto(name, description,coins));
    }

    public User getUser(String id) {
        logger.info("getUser("+id+")");

        for (User t: this.users) {
            if (t.getId().equals(id)) {
                logger.info("getUser("+id+"): "+t);

                return t;
            }
        }
        logger.warn("not found " + id);
        return null;
    }

    public Objeto getObjeto(String name) {
        logger.info("getObjeto("+name+")");

        for (Objeto t: this.objects) {
            if (t.getName().equals(name)) {
                logger.info("getObjeto("+name+"): "+t);

                return t;
            }
        }
        logger.warn("not found " + name);
        return null;
    }

    @Override
    public List<User> findUsers() {

        if (users.size()!=0) {
            users.sort(new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    int res=String.CASE_INSENSITIVE_ORDER.compare(o1.getSurname(), o2.getSurname());
                    if(res ==0){
                        res= String.CASE_INSENSITIVE_ORDER.compare(o1.getName(), o2.getName());
                    }
                    return res;
                }
            });
//        products.sort((o1, o2)-> Double.compare(o1.getPrice(), o2.getPrice()));
            return this.users;
            }
        else{
            logger.warn("no hay usuarios");
            return null;
        }
    }

    @Override
    public List<Objeto> globalObjetos() {

        if (objects.size()!=0) {
            objects.sort(new Comparator<Objeto>() {
                @Override
                public int compare(Objeto o1, Objeto o2) {
                    return Double.compare(o1.getCoins(), o2.getCoins());
                }
            });

//        products.sort((o1, o2)-> Double.compare(o1.getPrice(), o2.getPrice()));

            logger.info("se ha podido ordenar la lista correctamente");
            return objects;
        }
        else {
            logger.warn("no se han podido encontrar objetos");
            return null;
        }

    }
    @Override
    public Objeto buyObjeto(String name,String userId) {
        Objeto res = new Objeto();
        User user=getUser(userId);
        if(user!=null) {
            boolean objetoencontrado = false;
            for (int i = 0; !objetoencontrado && i < objects.size(); i++) {
                if (name.equals(objects.get(i).getName())) {
                    res = objects.get(i);
                    objetoencontrado = true;
                    logger.info("se encontró el objeto");
                }
            }
            if (objetoencontrado) {
                Objeto comprado =user.compraObjeto(res);
                if(comprado!=null) {
                    logger.info("se ha comprado el objeto");
                    return res;
                }
                else{
                    logger.info("sin saldo suficiente");
                    return null;
                }
            } else {
                logger.warn("no se ha podido comprar" + res);
                return null;
            }
        }
        else {
            logger.warn("Usuario no encontrado");
            return null;}
    }


    @Override
    public List<Objeto> userObjetos(String id) {
        User user= getUser(id);
        if (user!=null){
            logger.info("found"+ user.getCompras());
            return user.getCompras();
        }
        else {
            logger.warn("not found" + user);
            return null;}
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
    public void deleteObjeto(String name) {

        Objeto t = this.getObjeto(name);
        if (t==null) {
            logger.warn("not found " + t);
        }
        else logger.info(t+" deleted ");

        this.objects.remove(t);

    }

    @Override
    public User updateUser(User p) {
        User t = this.getUser(p.getId());

        if (t!=null) {
            logger.info(p+" rebut!!!! ");

            t.setSurname(p.getSurname());
            t.setName(p.getName());
            t.setBirthday(p.getBirthday());
            t.setElectronic(p.getElectronic());
            t.setPassword(p.getPassword());
            t.setDsa_coins(p.getDsa_coins());

            logger.info(t+" updated ");
        }
        else {
            logger.warn("not found "+p);
        }

        return t;
    }
    public Objeto updateObjeto(Objeto p) {
        Objeto t = this.getObjeto(p.getName());

        if (t!=null) {
            logger.info(p+" rebut!!!! ");

            t.setName(p.getName());
            t.setDescription(p.getDescription());
            t.setCoins(p.getCoins());

            logger.info(t+" updated ");
        }
        else {
            logger.warn("not found "+p);
        }

        return t;
    }
}
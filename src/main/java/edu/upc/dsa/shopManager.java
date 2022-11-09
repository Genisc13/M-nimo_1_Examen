package edu.upc.dsa;

import edu.upc.dsa.models.Credentials;
import edu.upc.dsa.models.Objeto;
import edu.upc.dsa.models.User;

import java.util.List;

public interface shopManager {


    public User addUser(String id,String name, String surname, String birthday,String electronic, String password);
    public User addUser(User t);
    public User loginUser(Credentials credentials);
    public Objeto addObjeto(Objeto t);
    public User getUser(String id);

    public Objeto getObjeto(String name);
    public List<User> findUsers();
    public Objeto addObjeto(String name, String description, int coins);

    public List<Objeto> globalObjetos();

    public Objeto buyObjeto(String name, String user);

    public List<Objeto> userObjetos(String id);

    public void deleteUser(String id);

    public void deleteObjeto(String name);
    public User updateUser(User t);
    public Objeto updateObjeto (Objeto t);

    public int sizeUsers();
    public int sizeObjetos();
}

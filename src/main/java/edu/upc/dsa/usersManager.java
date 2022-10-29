package edu.upc.dsa;

import edu.upc.dsa.models.User;

import java.util.List;

public interface usersManager {


    public User addUser(String name, String surname, String birthday,String electronic, String password);
    public User addUser(User t);
    public User getUser(String id);
    public List<User> findAll();
    public void deleteUser(String id);
    public User updateUser(User t);

    public int size();
}

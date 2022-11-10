package edu.upc.dsa;

import edu.upc.dsa.models.LevelResults;
import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.User;

import java.util.List;

public interface juegoManager {


    public User addUser(String id,String name, String surname);
    public User addUser(User t);
    public Partida crearPartida(Partida t);
    public User getUser(String id);
    public Partida getPartida(String name);
    public List<User> findUsers();
    public Partida crearPartida(String id, String description, int num_levels);
    public Partida iniciarPartida(String id_partida,String user_id);
    public int actualLevel(String user_id);
    public int actual_score(String user_id);
    public void level_passed(String user_id,int score,String date);
    public Partida acaba_partida(User usuario);
    public List<User> usuarios_partida(Partida id_partida);
    public List<LevelResults> resultadosPartidaUsuario(String id_partida, String user_id);
    public List<Partida> userPartidas(String id);
    public List<Partida> globalPartidas();
    public void deleteUser(String id);
    public void deletePartida(String name);
    public User updateUser(User t);
    public Partida updatePartida(Partida t);
    public int sizeUsers();
    public int sizePartidas();
}

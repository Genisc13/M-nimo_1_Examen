package edu.upc.dsa;

import edu.upc.dsa.models.LevelResults;
import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.User;

import java.util.*;

import org.apache.log4j.Logger;

public class juegoManagerImpl implements juegoManager {
    private static juegoManager instance;
    protected HashMap<String,User> users;

    protected List<Partida> partidas;
    final static Logger logger = Logger.getLogger(juegoManagerImpl.class);

    public juegoManagerImpl() {
        this.partidas = new LinkedList<>();
        this.users = new HashMap<>();
    }

    public static juegoManager getInstance() {
        if (instance==null) instance = new juegoManagerImpl();
        return instance;
    }

    public int sizeUsers() {
        int ret = this.users.size();
        logger.info("size " + ret);

        return ret;
    }
    public int sizePartidas(){
        int ret=this.partidas.size();
        logger.info("size "+ ret);
        return ret;
    }

    public User addUser(User t) {
        logger.info("new User " + t);
        if (users.containsValue(t.getName())){
            logger.warn("El usuario con este nombre ya fue registrado");
            return null;
        }
        this.users.put(t.getId(),t);
        logger.info("new User added");
        return t;
    }
    // Para la creación de un juego se debe indicar un
    //identificador, una descripción y un número de niveles
    public Partida crearPartida(Partida t) {
        logger.info("new Game " + t);
        this.partidas.add (t);
        logger.info("new Game added");
        return t;
    }

    public User addUser(String id,String name, String surname) {
        return this.addUser(new User(id,name, surname));
    }

    public Partida crearPartida(String id, String description, int num_levels){
        return this.crearPartida(new Partida(id, description, num_levels));
    }

    //Se debe
    //indicar el identificador del juego y el identificador del usuario. El
    //resultado de la operación es que el usuario entra en el primer nivel con
    //50 puntos iniciales. En caso que el usuario o juego no existan, se deberá
    //indicar el error. Un jugador SÓLO puede estar en una partida al mismo
    //tiempo. En caso que el jugador ya tenga una partida activa, se deberá
    //indicar el error.
    public Partida iniciarPartida(String id_partida,String user_id){
        User iniciador= this.getUser(user_id);
        Partida empezada = iniciador.iniciarPartida(getPartida(id_partida));
        if (empezada!=null){
            logger.info("La partida se pudo empezar correctamente");
            return empezada;
        }
        else {
            logger.warn("No se ha podido empezar la partida");
            return null;
        }

    }

    @Override
    public int actualLevel(String user_id) {
        return this.getUser(user_id).getActual_level();
    }
    public int actual_score(String user_id){
        return this.getUser(user_id).getActual_score();
    }

    //Se deberá indicar el identificador de usuario, los puntos
    //conseguidos con el paso de nivel y la fecha en la que se produce el
    //cambio de nivel. El resultado de la operación consiste en un cambio del
    //nivel que será el siguiente respecto al que estaba y se mantendrá el
    //acumulado de puntos de esa partida, de ese jugadorl. En caso que el
    //usuario esté en el último nivel, se incrementará la puntuación acumulada
    //en 100 puntos y la partida finalizará. En caso que el usuario no exista o
    //no esté en una partida en curso, se deberá indicar el error.
    public void level_passed(String user_id,int score,String date){
        User pasador= this.getUser(user_id);
        LevelResults res= pasador.pasarLevel(score,date);
        if (res == null){
            logger.warn("No se ha podido pasar de nivel, quizas no habia una partida actual");
        }
        else{
            logger.info("Se ha podido pasar de nivel correctamente");
        }

    }
    //Finalizar partida. Se indica que un determinado usuario ha finalizado la
    //partida actual. En caso que el usuario no exista o no esté en una partida
    //en curso de deberá indicar el error
    public Partida acaba_partida(User usuario){
        User usuari=this.getUser(usuario.getId());
        if(usuari==null){
            logger.warn("no se ha encontrado al usuario");
            return null;
        }
        else{
            Partida acabada=usuari.acabarPartida();
            if(acabada==null){
                logger.warn("no se pudo acabar la partida, quizás no tienes partida actual");
                return null;
            }
            else{
                logger.info("se ha acabado la partida correctamente");
                return acabada;
            }
        }
    }
    public List<User> usuarios_partida(Partida partida){
        Partida partit=this.getPartida(partida.getId());
        List<User> lista=partit.getUsuarios();
        lista.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return Double.compare(o2.getGameResults(partit.getId()).getFinal_score(), o1.getGameResults(partit.getId()).getFinal_score());
            }
        });
        return lista;
    }
    //- Consulta de las partidas en las que ha participado un usuario. En
    //caso que el usuario no exista se deberá indicar un error.
    @Override
    public List<Partida> userPartidas(String id) {
        User user= getUser(id);
        if (user!=null){
            logger.info("found"+ user.getPartidas());
            return user.getPartidas();
        }
        else {
            logger.warn("not found" + user);
            return null;}
    }
    //Se
    //proporciona un listado de información asociada a la actividad del usuario
    //en el juego. Ejemplo: actividad(“juan”, “the game”): -> [ {level: 1, points:
    //5, date: dd-mm-aaaa}, {level:2, points:15, date: dd-mm-aaaa}, {level3:
    //points: 20, date: dd-mm-aaaa}]

    public List<LevelResults> resultadosPartidaUsuario(String id_partida, String user_id){
        User usuario= this.getUser(user_id);
        if (usuario==null){
            logger.warn("No hay un usuario con esa id");
            return null;
        }
        else {
            List<LevelResults> resultados_juego = usuario.getGameResults(id_partida).getResultados();
            if (resultados_juego==null){
                logger.warn("no se pudieron encontrar los resultados");
                return null;
            }
            else {
                logger.info("Se encontraron los resultados correctamente");
                return resultados_juego;
            }
        }
    }
    public User getUser(String id) {
        logger.info("getUser("+id+")");
        if(users.containsKey(id)) {
            logger.info("getUser(" + id + "): " + this.users.get(id));
            return this.users.get(id);
        }
        else {
            logger.warn("not found " + id);
            return null;
        }
    }

    public Partida getPartida(String name) {
        logger.info("getObjeto("+name+")");

        for (Partida t: this.partidas) {
            if (t.getId().equals(name)) {
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

            Collection<User> list= users.values();
            List <User> lista= new ArrayList<>(list);
            lista.sort(new Comparator<User>() {
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
            return lista;
            }
        else{
            logger.warn("no hay usuarios");
            return null;
        }
    }

    @Override
    public List<Partida> globalPartidas() {

        if (partidas.size()!=0) {
            partidas.sort(new Comparator<Partida>() {
                @Override
                public int compare(Partida o1, Partida o2) {
                    return Double.compare(o2.getNum_levels(), o1.getNum_levels());
                }
            });

//        products.sort((o1, o2)-> Double.compare(o1.getPrice(), o2.getPrice()));

            logger.info("se ha podido ordenar la lista correctamente");
            return partidas;
        }
        else {
            logger.warn("no se han podido encontrar objetos");
            return null;
        }

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
    public void deletePartida(String name) {

        Partida t = this.getPartida(name);
        if (t==null) {
            logger.warn("not found " + t);
        }
        else logger.info(t+" deleted ");

        this.partidas.remove(t);

    }

    @Override
    public User updateUser(User p) {
        User t = this.getUser(p.getId());

        if (t!=null) {
            logger.info(p+" rebut!!!! ");

            t.setSurname(p.getSurname());
            t.setName(p.getName());
            t.setPartida_actual(p.getPartida_actual());
            t.setActual_score(p.getActual_score());

            logger.info(t+" updated ");
        }
        else {
            logger.warn("not found "+p);
        }

        return t;
    }
    public Partida updatePartida(Partida p) {
        Partida t = this.getPartida(p.getId());

        if (t!=null) {
            logger.info(p+" rebut!!!! ");

            t.setId(p.getId());
            t.setDescription(p.getDescription());
            t.setNum_levels(p.getNum_levels());

            logger.info(t+" updated ");
        }
        else {
            logger.warn("not found "+p);
        }

        return t;
    }
}
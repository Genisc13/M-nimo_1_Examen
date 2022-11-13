package edu.upc.dsa;

import edu.upc.dsa.models.LevelResults;
import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.User;
import edu.upc.dsa.util.*;
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
    @Override
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

    @Override
    public Partida iniciarPartida(String id_partida,String user_id){
        User iniciador= this.getUser(user_id);
        Partida empezada = iniciador.iniciarPartida(getPartida(id_partida));
        if (empezada!=null){
            logger.info("La partida se pudo empezar correctamente");
            this.getPartida(id_partida).getUsuarios().add(iniciador);
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

    @Override
    public LevelResults level_passed(String user_id,int score,String date){
        User pasador= this.getUser(user_id);
        LevelResults res= pasador.pasarLevel(score,date);
        if (res == null){
            logger.warn("No se ha podido pasar de nivel, quizas no habia una partida actual");
            return null;
        }
        else{
            logger.info("Se ha podido pasar de nivel correctamente");
            if (res.getLevel()==pasador.getPartida_actual().getNum_levels()){
                Partida terminada= this.acaba_partida(pasador);
                if(terminada!=null) {
                    return res;
                }
                else {
                    return null;
                }
            }
            return res;
        }

    }
    @Override
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
                acabada.getUsuarios().remove(usuari);
                return acabada;
            }
        }
    }
    @Override
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
    @Override
    public List<Partida> userPartidas(String user_id) {
        User user= getUser(user_id);
        if (user!=null){
            logger.info("found"+ user.getPartidas());
            return user.getPartidas();
        }
        else {
            logger.warn("not found" + user);
            return null;}
    }
    @Override
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
    @Override
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
    @Override
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

        if (this.partidas.size()!=0) {
            return this.partidas;
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
    @Override
    public Partida updatePartida(Partida p) {
        Partida t = this.getPartida(p.getId());

        if (t!=null) {
            logger.info(p+" rebut!!!! ");

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
package edu.upc.dsa;

import edu.upc.dsa.models.LevelResults;
import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class juegoManagerTest {
    juegoManager sm;

    @Before
    public void setUp() {
        sm = new juegoManagerImpl();
        this.sm.addUser("1","Juan","Perez Salva");
        this.sm.addUser("2","Oriol","Perchas Garrido");
        this.sm.addUser("3","Lucas","Naranjin Bicho");

        sm.crearPartida("1", "Nivel medio", 4);
        sm.crearPartida("2", "Nivel dificil", 8);
        sm.crearPartida("3", "Nivel medio", 5);
        sm.crearPartida("4", "Nivel facil", 3);
    }

    @After
    public void tearDown(){this.sm =null;}
    @Test
    public void testgetUser(){
        Assert.assertEquals(3, this.sm.sizeUsers());
        Assert.assertEquals(4, this.sm.sizePartidas());
        User cuartoUser=this.sm.addUser("4","Jose","Pedro Jordel");
        Assert.assertEquals(4, this.sm.sizeUsers());
        String Idcuarto= cuartoUser.getId();
        Assert.assertEquals(Idcuarto,sm.getUser(Idcuarto).getId());
    }

    @Test
    public void testfindUsers(){
        Assert.assertEquals(3, this.sm.sizeUsers());
        Assert.assertEquals(4, this.sm.sizePartidas());
        List<User> lista=sm.findUsers();
        Assert.assertEquals(3,lista.size());

        Assert.assertEquals("Juan", lista.get(2).getName());
        Assert.assertEquals("Perez Salva", lista.get(2).getSurname());

        Assert.assertEquals("Lucas", lista.get(0).getName());
        Assert.assertEquals("Naranjin Bicho", lista.get(0).getSurname());

        Assert.assertEquals("Oriol", lista.get(1).getName());
        Assert.assertEquals("Perchas Garrido", lista.get(1).getSurname());

    }

    @Test
    public void testiniciarPartida(){
        Assert.assertEquals(3, this.sm.sizeUsers());
        Assert.assertEquals(4, this.sm.sizePartidas());

        Partida empezada= this.sm.iniciarPartida("1","1");
        Assert.assertEquals(1,sm.getUser("1").getResultados().size());
        Assert.assertEquals(50,sm.getUser("1").getActual_score());
        Assert.assertEquals(1,sm.getUser("1").getActual_level());
    }

    @Test
    public void testlevelPassed(){
        Partida empezada= this.sm.iniciarPartida("1","1");
        this.sm.level_passed("1",12,"3/2/2004");
        Assert.assertEquals(2,sm.actualLevel("1"));
        Assert.assertEquals(62,sm.actual_score("1"));
        this.sm.level_passed("1",20,"3/2/2004");
        Assert.assertEquals(3,sm.actualLevel("1"));
        Assert.assertEquals(82,sm.actual_score("1"));
        this.sm.level_passed("1",15,"3/2/2004");
        Assert.assertEquals(4,sm.actualLevel("1"));
        Assert.assertEquals(97,sm.actual_score("1"));
        this.sm.level_passed("1",12,"3/2/2004");
        Assert.assertEquals(null,sm.getUser("1").getPartida_actual());
    }
    @Test
    public void resultados_partidaUser(){
        Partida empezada= this.sm.iniciarPartida("1","1");
        this.sm.level_passed("1",12,"3/2/2004");
        Assert.assertEquals(2,sm.actualLevel("1"));
        Assert.assertEquals(62,sm.actual_score("1"));
        this.sm.level_passed("1",20,"3/2/2004");
        Assert.assertEquals(3,sm.actualLevel("1"));
        Assert.assertEquals(82,sm.actual_score("1"));
        this.sm.level_passed("1",15,"3/5/2004");
        Assert.assertEquals(4,sm.actualLevel("1"));
        Assert.assertEquals(97,sm.actual_score("1"));
        this.sm.level_passed("1",12,"3/2/2005");
        Assert.assertEquals(null,sm.getUser("1").getPartida_actual());
        List<LevelResults> lista = sm.resultadosPartidaUsuario("1","1");
        Assert.assertEquals(4,lista.size());
    }

    @Test
    public void cosultaUsuariosjuego(){
        this.sm.iniciarPartida("1","1");
        this.sm.iniciarPartida("1","2");
        this.sm.iniciarPartida("1","3");
        this.sm.level_passed("2",24,"3/2/2021");
        this.sm.level_passed("1",20,"9/3/2000");
        this.sm.level_passed("3",30,"7/5/2020");
        List<User> usuarios=sm.usuarios_partida(sm.getPartida("1"));
        Assert.assertEquals(3,usuarios.size());
        Assert.assertEquals("3",usuarios.get(0).getId());
        Assert.assertEquals("2",usuarios.get(1).getId());
        Assert.assertEquals("1",usuarios.get(2).getId());
    }
    @Test
    public void cosultaPartidasTerminadas(){
        this.sm.iniciarPartida("1","1");
        this.sm.level_passed("1",20,"7/3/2000");
        this.sm.level_passed("1",10,"8/3/2000");
        this.sm.level_passed("1",25,"10/3/2000");
        this.sm.level_passed("1",2,"9/4/2000");
        List<Partida> partidas=this.sm.userPartidas("1");
        Assert.assertEquals(1,partidas.size());
        this.sm.iniciarPartida("4","1");
        this.sm.level_passed("1",20,"7/6/2000");
        this.sm.level_passed("1",10,"8/8/2000");
        this.sm.level_passed("1",25,"10/9/2000");
        partidas=this.sm.userPartidas("1");
        Assert.assertEquals(2,partidas.size());
    }

}

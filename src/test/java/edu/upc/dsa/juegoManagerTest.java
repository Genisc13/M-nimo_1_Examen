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
    public void testGlobalPartidas(){

        List<Partida> partidas = sm.globalPartidas();
        Assert.assertEquals("A001", partidas.get(3).getId());
        Assert.assertEquals(2, partidas.get(3).getNum_levels(), 0);

        Assert.assertEquals("B001", partidas.get(2).getId());
        Assert.assertEquals(4, partidas.get(2).getNum_levels(), 0);

        Assert.assertEquals("C002", partidas.get(1).getId());
        Assert.assertEquals(7, partidas.get(1).getNum_levels(), 0);

        Assert.assertEquals("A003", partidas.get(0).getId());
        Assert.assertEquals(8, partidas.get(0).getNum_levels(), 0);
    }

    @Test
    public void testiniciarPartida(){
        Assert.assertEquals(3, this.sm.sizeUsers());
        Assert.assertEquals(4, this.sm.sizePartidas());

        Partida empezada= this.sm.iniciarPartida("1","1");
        Assert.assertEquals(1,sm.userPartidas("1").size());
        Assert.assertEquals(50,sm.getUser("1").getActual_score());
    }

    @Test
    public void testlevelPassed(){
        Partida empezada= this.sm.iniciarPartida("1","1");
        this.sm.level_passed("1",12,"3/2/2004");
        Assert.assertEquals(2,sm.actualLevel("1"));
        Assert.assertEquals(62,sm.actual_score("1"));
    }


}

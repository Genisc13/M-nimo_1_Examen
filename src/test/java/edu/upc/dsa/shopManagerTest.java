package edu.upc.dsa;

import edu.upc.dsa.models.Objeto;
import edu.upc.dsa.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class shopManagerTest {
    shopManager sm;

    @Before
    public void setUp() {
        sm = new shopManagerImpl();
        this.sm.addUser("1","Juan","Perez Salva", "03/02/2001","juan.perez@estudiantat.upc.edu","Pedro");
        this.sm.addUser("2","Oriol","Perchas Garrido", "03/10/1999","oriol.perchas@estudiantat.upc.edu","Elpenao");
        this.sm.addUser("3","Lucas","Naranjin Bicho", "01/07/2000","lucas.naranjin@estudiantat.upc.edu","Acojonante23");

        sm.addObjeto("B001", "Coca cola", 4);
        sm.addObjeto("C002", "Café amb gel", 7);
        sm.addObjeto("A001", "Donut", 2);
        sm.addObjeto("A003", "Croissant", 8);
    }

    @After
    public void tearDown(){this.sm =null;}
    @Test
    public void testgetUser(){
        Assert.assertEquals(3, this.sm.sizeUsers());
        Assert.assertEquals(4, this.sm.sizeObjetos());
        User cuartoUser=this.sm.addUser("4","Jose","Pedro Jordel","03/03/2001","Jose.pedro@estudiantat.upc.edu","Joselito32");
        Assert.assertEquals(4, this.sm.sizeUsers());
        String Idcuarto= cuartoUser.getId();
        Assert.assertEquals(Idcuarto,sm.getUser(Idcuarto).getId());
    }

    @Test
    public void testfindUsers(){
        Assert.assertEquals(3, this.sm.sizeUsers());
        Assert.assertEquals(4, this.sm.sizeObjetos());
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
    public void testGlobalObjetos(){

        List<Objeto> objetos= sm.globalObjetos();
        Assert.assertEquals("A001",objetos.get(0).getName());
        Assert.assertEquals("A001", objetos.get(0).getName());
        Assert.assertEquals(2, objetos.get(0).getCoins(), 0);

        Assert.assertEquals("B001", objetos.get(1).getName());
        Assert.assertEquals(4, objetos.get(1).getCoins(), 0);

        Assert.assertEquals("C002", objetos.get(2).getName());
        Assert.assertEquals(7, objetos.get(2).getCoins(), 0);

        Assert.assertEquals("A003", objetos.get(3).getName());
        Assert.assertEquals(8, objetos.get(3).getCoins(), 0);
    }

    @Test
    public void testbuyObjeto(){
        Assert.assertEquals(3, this.sm.sizeUsers());
        Assert.assertEquals(4, this.sm.sizeObjetos());
        Objeto compra= this.sm.buyObjeto("A001","1");
        Assert.assertEquals("A001",compra.getName());
        Assert.assertEquals(1,sm.userObjetos("1").size());
        Assert.assertEquals(48,sm.getUser("1").getDsa_coins());
    }

    @Test
    public void testuserObjetos(){
        Objeto compra= this.sm.buyObjeto("A001","1");
        Assert.assertEquals("A001",compra.getName());
        Objeto compra2= this.sm.buyObjeto("B001","1");
        Assert.assertEquals("B001",compra2.getName());
        Objeto compra3= this.sm.buyObjeto("A003","1");
        Assert.assertEquals("A003",compra3.getName());
        Assert.assertEquals(3,sm.userObjetos("1").size());
        Assert.assertEquals(36,sm.getUser("1").getDsa_coins());
    }
    @Test
    public void testloginUser(){
        User logero=sm.loginUser("lucas.naranjin@estudiantat.upc.edu","Acojonante23");
        Assert.assertEquals("Lucas",logero.getName());
    }

}

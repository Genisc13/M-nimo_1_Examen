package edu.upc.dsa;

import edu.upc.dsa.models.Objeto;
import edu.upc.dsa.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class shopManagerTest {
    shopManager sm;

    @Before
    public void setUp() {
        sm = new shopManagerImpl();
        this.sm.addUser("Juan","Perez Salva", "03/02/2001","juan.perez@estudiantat.upc.edu","Pedro");
        this.sm.addUser("Lucas","Naranjin Bicho", "01/07/2000","lucas.naranjin@estudiantat.upc.edu","Acojonante23");
        this.sm.addUser("Oriol","Perchas Garrido", "03/10/1999","oriol.perchas@estudiantat.upc.edu","Elpenao");

    }
    @Test
    public void testgetUser(){
        User cuartoUser=this.sm.addUser("Jose","Pedro Jordel","03/03/2001","Jose.pedro@estudiantat.upc.edu","Joselito32");
        String Idcuarto= cuartoUser.getId();
        Assert.assertEquals(Idcuarto,sm.getUser(Idcuarto).getId());
    }

    @Test
    public void testaddObjeto(){
        sm.addObjeto("B001", "Coca cola", 4);
        Assert.assertEquals(1,sm.sizeObjetos());
        sm.addObjeto("C002", "Café amb gel", 7);
        Assert.assertEquals(2,sm.sizeObjetos());
        sm.addObjeto("A002", "Donut", 2);
        Assert.assertEquals(3,sm.sizeObjetos());
        sm.addObjeto("A003", "Croissant", 8);
        Assert.assertEquals(4,sm.sizeObjetos());
    }



}

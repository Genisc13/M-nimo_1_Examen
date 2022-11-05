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

        sm.addObjeto("B001", "Coca cola", 4);
        sm.addObjeto("C002", "Café amb gel", 7);
        sm.addObjeto("A002", "Donut", 2);
        sm.addObjeto("A003", "Croissant", 8);
    }
    @Test
    public void testBuscarUser(){
        User cuartoUser=this.sm.addUser("Jose","Pedro Jordel","03/03/2001","Jose.pedro@estudiantat.upc.edu","Joselito32");
        String Idcuarto= cuartoUser.getId();
        Assert.assertEquals(3,sm.buscarUser(Idcuarto));

    }



}

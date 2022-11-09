package edu.upc.dsa.services;


import edu.upc.dsa.models.Objeto;
import edu.upc.dsa.shopManager;
import edu.upc.dsa.shopManagerImpl;
import edu.upc.dsa.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/shop", description = "Endpoint to Shop Service")
@Path("/shop")
public class ShopService {

    private shopManager mm;

    public ShopService() {
        this.mm = shopManagerImpl.getInstance();
        if (mm.sizeUsers()==0) {
            this.mm.addUser("1","Juan","Perez Salva", "03/02/2001","juan.perez@estudiantat.upc.edu","Pedro");
            this.mm.addUser("2","Lucas","Naranjin Bicho", "01/07/2000","lucas.naranjin@estudiantat.upc.edu","Acojonante23");
            this.mm.addUser("3","Oriol","Perchas Garrido", "03/10/1999","oriol.perchas@estudiantat.upc.edu","Elpenao");
        }

    }

    @GET
    @ApiOperation(value = "get all Users", notes = "todos y cada uno de ellos")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer="List"),
    })
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {

        List<User> users = this.mm.findUsers();

        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
        return Response.status(201).entity(entity).build()  ;

    }

    @GET
    @ApiOperation(value = "get a User", notes = "Da un usuario según su id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User not found")
    })

    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") String id) {
        User t = this.mm.getUser(id);
        if (t == null) return Response.status(404).build();
        else  return Response.status(201).entity(t).build();
    }
    @GET
    @ApiOperation(value = "get a Object", notes = "Da un objeto segun su nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User not found")
    })

    @Path("/object/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObjeto(@PathParam("name") String name) {
        Objeto t = this.mm.getObjeto(name);
        if (t == null) return Response.status(404).build();
        else  return Response.status(201).entity(t).build();
    }

    @DELETE
    @ApiOperation(value = "delete a User", notes = "Elimina un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/user/{id}")
    public Response deleteUser(@PathParam("id") String id) {
        User t = this.mm.getUser(id);
        if (t == null) return Response.status(404).build();
        else this.mm.deleteUser(id);
        return Response.status(201).build();
    }

    @DELETE
    @ApiOperation(value = "delete a Object", notes = "Elimina un objeto")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/object/{name}")
    public Response deleteObjeto(@PathParam("name") String name) {
        Objeto t = this.mm.getObjeto(name);
        if (t == null) return Response.status(404).build();
        else this.mm.deleteObjeto(name);
        return Response.status(201).build();
    }

    @PUT
    @ApiOperation(value = "update a User", notes = "Actualiza datos del Usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/user")
    public Response updateUser(User user) {

        User t = this.mm.updateUser(user);

        if (t == null) return Response.status(404).build();

        return Response.status(201).build();
    }
    @PUT
    @ApiOperation(value = "update a Objeto", notes = "Actualiza datos del Objeto")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Objeto not found")
    })
    @Path("/object")
    public Response updateObjeto(Objeto objeto) {

        Objeto t = this.mm.updateObjeto(objeto);

        if (t == null) return Response.status(404).build();

        return Response.status(201).build();
    }


    @POST
    @ApiOperation(value = "create a new User", notes = "crea un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= User.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })

    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUser(User user) {

        if (user.getSurname()==null || user.getName()==null)  return Response.status(500).entity(user).build();
        this.mm.addUser(user);
        return Response.status(201).entity(user).build();
    }
    @POST
    @ApiOperation(value = "create a new Objeto", notes = "crea un objeto")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= User.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })

    @Path("/object")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newObjeto(Objeto objeto) {

        if (objeto.getName()==null || objeto.getDescription()==null || objeto.getCoins()<=0)  return Response.status(500).entity(objeto).build();
        this.mm.addObjeto(objeto);
        return Response.status(201).entity(objeto).build();
    }

}
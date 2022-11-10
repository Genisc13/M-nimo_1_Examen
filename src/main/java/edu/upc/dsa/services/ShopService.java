package edu.upc.dsa.services;


import edu.upc.dsa.models.LevelResults;
import edu.upc.dsa.models.Partida;
import edu.upc.dsa.juegoManager;
import edu.upc.dsa.juegoManagerImpl;
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

    private juegoManager mm;

    public ShopService() {
        this.mm = juegoManagerImpl.getInstance();
        if (mm.sizeUsers()==0) {
            this.mm.addUser("1","Juan","Perez Salva");
            this.mm.addUser("2","Lucas","Naranjin Bicho");
            this.mm.addUser("3","Oriol","Perchas Garrido");
        }
        if (mm.sizePartidas()==0){
            mm.crearPartida("1", "Nivel medio", 4);
            mm.crearPartida("2", "Nivel dificil", 8);
            mm.crearPartida("3", "Nivel medio", 5);
            mm.crearPartida("4", "Nivel facil", 3);
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
    @ApiOperation(value = "get all Objects", notes = "ordenados ascendentemente")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Partida.class, responseContainer="List"),
    })
    @Path("/objects")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObjects() {

        List<Partida> partidas = this.mm.globalPartidas();
        GenericEntity<List<Partida>> entity = new GenericEntity<List<Partida>>(partidas) {};
        return Response.status(201).entity(entity).build()  ;

    }
    @GET
    @ApiOperation(value = "Da los objetos del usuario", notes = "Objetos de usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Partida.class, responseContainer="List"),
    })
    @Path("/user/objects/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObjects(@PathParam("id") String id) {

        List<Partida> partidas = this.mm.userPartidas(id);
        GenericEntity<List<Partida>> entity = new GenericEntity<List<Partida>>(partidas) {};
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
            @ApiResponse(code = 201, message = "Successful", response = Partida.class),
            @ApiResponse(code = 404, message = "User not found")
    })

    @Path("/object/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObjeto(@PathParam("name") String name) {
        Partida t = this.mm.getPartida(name);
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
        Partida t = this.mm.getPartida(name);
        if (t == null) return Response.status(404).build();
        else this.mm.deletePartida(name);
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
    public Response updateObjeto(Partida partida) {

        Partida t = this.mm.updatePartida(partida);

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
        User m= this.mm.addUser(user);
        if (m==null ||user.getSurname()==null || user.getName()==null )  return Response.status(500).entity(user).build();
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
    public Response newObjeto(Partida partida) {

        if (partida.getId()==null || partida.getDescription()==null || partida.getNum_levels()<=0)  return Response.status(500).entity(partida).build();
        this.mm.crearPartida(partida);
        return Response.status(201).entity(partida).build();
    }

}
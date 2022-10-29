package edu.upc.dsa.services;


import edu.upc.dsa.usersManager;
import edu.upc.dsa.usersManagerImpl;
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

@Api(value = "/maps", description = "Endpoint to Map Service")
@Path("/maps")
public class UsersService {

    private usersManager mm;

    public UsersService() {
        this.mm = usersManagerImpl.getInstance();
        if (mm.size()==0) {
            this.mm.addUser("Juan","Perez Salva", "03/02/2001","juan.perez@estudiantat.upc.edu","Pedro");
            this.mm.addUser("Lucas","Naranjin Bicho", "01/07/2000","lucas.naranjin@estudiantat.upc.edu","Acojonante23");
            this.mm.addUser("Oriol","Perchas Garrido", "03/10/1999","oriol.perchas@estudiantat.upc.edu","Elpenao");
        }


    }

    @GET
    @ApiOperation(value = "get all Maps", notes = "todos y cada uno de ellos")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer="List"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMaps() {

        List<User> users = this.mm.findAll();

        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
        return Response.status(201).entity(entity).build()  ;

    }

    @GET
    @ApiOperation(value = "get a Map", notes = "De locos")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "Map not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMap(@PathParam("id") String id) {
        User t = this.mm.getUser(id);
        if (t == null) return Response.status(404).build();
        else  return Response.status(201).entity(t).build();
    }

    @DELETE
    @ApiOperation(value = "delete a Map", notes = "Elimina un mapa")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Map not found")
    })
    @Path("/{id}")
    public Response deleteMap(@PathParam("id") String id) {
        User t = this.mm.getUser(id);
        if (t == null) return Response.status(404).build();
        else this.mm.deleteUser(id);
        return Response.status(201).build();
    }

    @PUT
    @ApiOperation(value = "update a Map", notes = "Actualiza datos del mapa")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Map not found")
    })
    @Path("/")
    public Response updateMap(User user) {

        User t = this.mm.updateUser(user);

        if (t == null) return Response.status(404).build();

        return Response.status(201).build();
    }



    @POST
    @ApiOperation(value = "create a new Map", notes = "crea un mapa")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= User.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })

    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newMap(User user) {

        if (user.getSurname()==null || user.getName()==null)  return Response.status(500).entity(user).build();
        this.mm.addUser(user);
        return Response.status(201).entity(user).build();
    }

}
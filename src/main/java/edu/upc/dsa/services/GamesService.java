package edu.upc.dsa.services;
import edu.upc.dsa.models.*;
import edu.upc.dsa.juegoManager;
import edu.upc.dsa.juegoManagerImpl;
import edu.upc.dsa.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/game", description = "Endpoint to game service")
@Path("/game")
public class GamesService {

    private juegoManager mm;

    public GamesService() {
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
    @ApiOperation(value = "get all Partidas", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Partida.class, responseContainer="List"),
    })
    @Path("/partidas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPartidas() {

        List<Partida> partidas = this.mm.globalPartidas();
        GenericEntity<List<Partida>> entity = new GenericEntity<List<Partida>>(partidas) {};
        return Response.status(201).entity(entity).build()  ;

    }
    @GET
    @ApiOperation(value = "get Resultados de una partida", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = LevelResults.class, responseContainer="List"),
    })
    @Path("/user/getresultadospartida/{user_id}/{partida_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResultadospartida(@PathParam("user_id") String user_id, @PathParam("partida_id") String partida_id) {

        List<LevelResults> partidas = this.mm.resultadosPartidaUsuario(partida_id,user_id);
        GenericEntity<List<LevelResults>> entity = new GenericEntity<List<LevelResults>>(partidas) {};
        return Response.status(201).entity(entity).build()  ;

    }
    @GET
    @ApiOperation(value = "Da las partidas del usuario", notes = "Partidas de usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Partida.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/user/partidas/{id_user}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPartidasUser(@PathParam("id_user") String id_user) {

        List<Partida> partidas = this.mm.userPartidas(id_user);
        GenericEntity<List<Partida>> entity = new GenericEntity<List<Partida>>(partidas) {};
        if(partidas==null) Response.status(404).entity(entity).build();
        return Response.status(201).entity(entity).build();

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
    @ApiOperation(value = "Da los usuarios de una partida", notes = "Usuarios en partida")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/partida/users/{id_partida}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersPartida(@PathParam("id_partida") String id_partida) {

        List<User> usuarios_partida = this.mm.usuarios_partida(mm.getPartida(id_partida));
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(usuarios_partida) {};
        if(usuarios_partida==null) Response.status(404).entity(entity).build();
        return Response.status(201).entity(entity).build();

    }
    @GET
    @ApiOperation(value = "get a User level", notes = "Da el nivel del usuario segun su id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Level.class),
            @ApiResponse(code = 404, message = "User not found")
    })

    @Path("/user/level/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserlevel(@PathParam("id") String id) {
        User user=this.mm.getUser(id);
        int t = this.mm.actualLevel(id);
        Level level=new Level(t);
        if (user==null) return Response.status(404).build();
        else  return Response.status(201).entity(level).build();
    }
    @GET
    @ApiOperation(value = "get a User actual_score", notes = "Da el score del usuario segun su id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Score.class),
            @ApiResponse(code = 404, message = "User not found")
    })

    @Path("/user/score/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserscore(@PathParam("id") String id) {
        User user=this.mm.getUser(id);
        int t = this.mm.actual_score(id);
        Score score= new Score(t);
        if (user==null) return Response.status(404).build();
        else  return Response.status(201).entity(score).build();
    }
    @GET
    @ApiOperation(value = "get a Partida", notes = "Da una partida segun su id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Partida.class),
            @ApiResponse(code = 404, message = "User not found")
    })

    @Path("/partida/{name}")
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
    @ApiOperation(value = "delete a Partida", notes = "Elimina una partida")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/partida/{name}")
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
    @ApiOperation(value = "update a partida", notes = "Actualiza datos de una partida")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Partida not found")
    })
    @Path("/partida")
    public Response updatePartida(Partida partida) {

        Partida t = this.mm.updatePartida(partida);

        if (t == null) return Response.status(404).build();

        return Response.status(201).build();
    }
    @PUT
    @ApiOperation(value = "Inicia a partida", notes = "Inicia una partida para un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User or partida not found")
    })
    @Path("/user/iniciarpartida/{id_partida}/{id_user}")
    public Response iniciaPartida(@PathParam("id_partida") String id_partida,@PathParam("id_user") String id_user) {

        Partida t = this.mm.iniciarPartida(id_partida,id_user);

        if (t == null) return Response.status(404).build();

        return Response.status(201).build();
    }
    @PUT
    @ApiOperation(value = "Pasa de nivel a un usuario", notes = "Sirve para hacer que el usuario pase de nivel")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User or partida not found")
    })
    @Path("/user/pasarlevel/{id_user}/{score}/{date}")
    public Response pasanivel(@PathParam("id_user") String id_user,@PathParam("score") int score,@PathParam("date") String date) {

        LevelResults t = this.mm.level_passed(id_user,score,date);

        if (t == null) return Response.status(404).build();
        return Response.status(201).build();
    }
    @PUT
    @ApiOperation(value = "Acaba la partida", notes = "Acaba la partida para un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User or partida not found")
    })
    @Path("/user/acabarpartida/{id_user}")
    public Response acabaPartida(@PathParam("id_user") String id_user) {

        Partida t = this.mm.acaba_partida(mm.getUser(id_user));

        if (t == null) {
            return Response.status(404).build();
        }

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
    @ApiOperation(value = "create a new Partida", notes = "crea una partida")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= User.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })

    @Path("/partida")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newObjeto(Partida partida) {

        if (partida.getId()==null || partida.getDescription()==null)  return Response.status(500).entity(partida).build();
        this.mm.crearPartida(partida);
        return Response.status(201).entity(partida).build();
    }

}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

/**
 *
 * @author estudiante
 */
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import co.edu.uniandes.csw.boletas.dtos.ComentarioDTO;

@Path("comentarios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ComentarioResource {
    @POST
    public ComentarioDTO postComentario(ComentarioDTO comentario){
    return comentario;
    }
    
    @GET
    @Path("{comentarioid : \\d+}")
    public ComentarioDTO getComentario(@PathParam("comentarioid") Long comentarioid)
    {
        return null;
    }
    
}

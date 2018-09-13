/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.dtos.ComentarioDTO;
import co.edu.uniandes.csw.boletas.ejb.ComentarioLogic;
import co.edu.uniandes.csw.boletas.ejb.EspectaculoComentarioLogic;
import co.edu.uniandes.csw.boletas.ejb.EspectaculoLogic;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 * Clase que implementa el recurso de /espectaculos/{id}/comentarios
 * @author Diego Camacho y Sebastian Rodriguez 
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EspectaculoComentarioResourse 
{
    private static final Logger LOGGER = Logger.getLogger(EspectaculoComentarioResourse.class.getName());

    @Inject
    private EspectaculoComentarioLogic espectaculoComentarioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private EspectaculoLogic espectaculoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    @Inject
    private ComentarioLogic comentarioLogic;
    
   /**
    * Metodo que se usa para agregar un comentario a un espectaculo
    * @POST 
    * 
    */
    
    @POST
    @Path("{comentariosId: \\d+}")
    public ComentarioDTO addComentario(@PathParam("espectaculoId") Long espectaculosId, @PathParam("comentarioId") Long comentariosId)
    {
        LOGGER.log(Level.INFO, "EspectaculoComentarioResourse addComentario: input: espectaculoId: "+ espectaculosId +", comentariosId: " + comentariosId, new Object[]{espectaculosId, comentariosId});
        if (comentarioLogic == null) {
            throw new WebApplicationException("El recurso /comentarios/" + comentariosId + " no existe.", 404);
        }
        ComentarioDTO comentarioDTO = new ComentarioDTO(espectaculoComentarioLogic.addComentario(comentariosId, espectaculosId));
        LOGGER.log(Level.INFO, "EspectaculoComentarioResourse agregarComentario: output: {0}", comentarioDTO.toString());
        return new ComentarioDTO();
    }
    
    @GET
    public List<ComentarioDTO> getComentarios(@PathParam("espectaculosId") Long espectaculosId) {
        LOGGER.log(Level.INFO, "EspectaculoComentarioResourse getComentarios: input: {0}", espectaculosId);
        List<ComentarioDTO> listaDetailDTOs = new ArrayList<>();
     //   List<ComentarioDTO> listaDetailDTOs = booksListEntity2DTO(editorialBooksLogic.getBooks(editorialsId));
     //   LOGGER.log(Level.INFO, "EditorialBooksResource getBooks: output: {0}", listaDetailDTOs.toString());
        return listaDetailDTOs;
    }
    
    @GET
    @Path("{comentariosId: \\d+}")
    public ComentarioDTO getComentario(@PathParam("espectaculosId") Long espectaculosId, @PathParam("comentariosId") Long comentariosId)
    {
        return new ComentarioDTO();
    }
    
    
    
}

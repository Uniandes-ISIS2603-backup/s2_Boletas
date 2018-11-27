/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.dtos.ComentarioDTO;
import co.edu.uniandes.csw.boletas.ejb.ComentarioLogic;
import co.edu.uniandes.csw.boletas.ejb.EspectaculoComentarioLogic;
import co.edu.uniandes.csw.boletas.entities.ComentarioEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 * Clase que implementa el recurso de /espectaculos/{id}/comentarios
 * @author  Sebastian Rodriguez 
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EspectaculoComentarioResourse 
{
    
    private static final String RECURSOES = "El Recurso /espectaculos/ ";
    private static final String RECURSOCOM = "El Recurso /comentarios/ ";
    private static final String EXISTE = " no existe";
    private static final Logger LOGGER = Logger.getLogger(EspectaculoComentarioResourse.class.getName());

    @Inject
    private EspectaculoComentarioLogic espectaculoComentarioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    @Inject
    private ComentarioLogic comentarioLogic;
    
   /**
    * Metodo que se usa para agregar un comentario a un espectaculo
     * @param espectaculosId
     * @param comentariosId
     * @return 
    * @POST 
    */
    
    @POST
    @Path("{comentariosId: \\d+}")
    public ComentarioDTO addComentario(@PathParam("espectaculoId") Long espectaculosId, @PathParam("comentarioId") Long comentariosId)
    {
        LOGGER.log(Level.INFO, "EspectaculoComentarioResourse addComentario: input: espectaculoId: {0}, comentariosId: {1}" , new Object[]{espectaculosId, comentariosId});
        if (comentarioLogic == null) {
            throw new WebApplicationException(RECURSOCOM + comentariosId + EXISTE, 404);
        }
        ComentarioDTO comentarioDTO = new ComentarioDTO(espectaculoComentarioLogic.addComentario(comentariosId, espectaculosId));
        LOGGER.log(Level.INFO, "EspectaculoComentarioResourse agregarComentario: output: {0}", comentarioDTO);
        return new ComentarioDTO();
    }
    
    
    /**
     * Metodo para obtener los comentarios asociados a un espectaculo
     * Tiene anotacion GET y se el pasa como parametro el id del espectaculo
     * define el recurso de /espectaculos/{id}/comentarios
     * @param espectaculosId
     * @return Lista con comentariosDTO
     */
    @GET
    public List<ComentarioDTO> getComentarios(@PathParam("espectaculosId") Long espectaculosId) {
        LOGGER.log(Level.INFO, "EspectaculoComentarioResourse getComentarios: input: {0}", espectaculosId);
        
        List<ComentarioDTO> listaDetailDTOs = comentariosListEntity2DTO(espectaculoComentarioLogic.darComentarios(espectaculosId));
        LOGGER.log(Level.INFO, "EditorialBooksResource getBooks: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }
    
    /**
     * Metodo GET para devolver un comentario especifico sobre un espectaculo
     * define el recurso /espectaculos/{id}/comentarios/{id}
     * @param espectaculosId El espectaculo que tiene ese comentario
     * @param comentariosId El id del comentario que se esta buscando
     * @return Un objeto comentarioDTO 
     * @throws BusinessLogicException En caso de que no exista el comentario o 
     * no este asociado a ese espectaculo
     */
    @GET
    @Path("{comentariosId: \\d+}")
    public ComentarioDTO getComentario(@PathParam("espectaculosId") Long espectaculosId, @PathParam("comentariosId") Long comentariosId) throws BusinessLogicException
    {
        
        LOGGER.log(Level.INFO, "EspectaculoComentarioResourse getComentario");
        if(comentarioLogic.getComentario(comentariosId) == null)
        {
            throw new WebApplicationException(RECURSOES + espectaculosId + "/comentarios/" + comentariosId + EXISTE, 404);
        }    
        
        return new ComentarioDTO(espectaculoComentarioLogic.getComentario(espectaculosId, comentariosId));
        
    }
    
    
    /**
     * Metodo que remplaza los comentarios de un espectaculo, por una lista de 
     * comentarios que le entra como parametro
     * @param espectaculosId, El id del cual se quiere hacer la actualizacion
     * @param listaComentarios, Una lista de objetos ComentarioDTO
     * @return Una lista con los comentarios agregados
     */
    @PUT 
    public List<ComentarioDTO> remplazarComentarios(@PathParam("espectaculosId") Long espectaculosId, List<ComentarioDTO> listaComentarios)
    {
        for (ComentarioDTO comentario : listaComentarios) {
            if (comentarioLogic.getComentario(comentario.getId()) == null) {
                throw new WebApplicationException(RECURSOCOM + comentario.getId() +EXISTE, 404);
            }
        }
    
         return comentariosListEntity2DTO(espectaculoComentarioLogic.remplazarComentarios(espectaculosId, booksListDTO2Entity(listaComentarios)));
    
    } 
    
    private List<ComentarioDTO> comentariosListEntity2DTO(List<ComentarioEntity> entityList) {
        List<ComentarioDTO> list = new ArrayList();
        for (ComentarioEntity entity : entityList) {
            list.add(new ComentarioDTO(entity));
        }
        return list;
    }
    
    private List<ComentarioEntity> booksListDTO2Entity(List<ComentarioDTO> dtos) {
        List<ComentarioEntity> list = new ArrayList<>();
        for (ComentarioDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}

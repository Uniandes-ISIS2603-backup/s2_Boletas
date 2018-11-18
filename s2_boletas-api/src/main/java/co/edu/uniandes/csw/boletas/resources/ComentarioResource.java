/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;


import co.edu.uniandes.csw.boletas.dtos.BoletaDTO;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import co.edu.uniandes.csw.boletas.dtos.ComentarioDTO;
import co.edu.uniandes.csw.boletas.ejb.ComentarioLogic;
import co.edu.uniandes.csw.boletas.entities.ComentarioEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que representa el recurso comentario
 * 
 * @author Diego Camacho
 */
@Path("comentarios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ComentarioResource {
    
    /**
     * Dependencia de la lógica, conexión con la persistencia
     */
    @Inject 
    private ComentarioLogic comentarioLogic;
    private static final String recurso = "El Recurso /comentarios/ ";
    private static final String existe = " /no existe";
    private static final Logger LOGGER = Logger.getLogger(ComentarioResource.class.getName());
    
    /**
     * Crea un nuevo comentario con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param comentario {@link ComentarioDTO} - El comentario que se desea guardar.
     * @return JSON {@link ComentarioDTO} - El comentario guardado con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el comentario 
     */
    @POST
    public ComentarioDTO postComentario(ComentarioDTO comentario) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "ComentarioResource postComentario: input: {0}", comentario);
        ComentarioEntity comentarioEntity = comentario.toEntity();
        ComentarioEntity nuevoComentarioEntity = comentarioLogic.createComentario(comentarioEntity);
        ComentarioDTO nuevoComentarioDTO =new ComentarioDTO(nuevoComentarioEntity);
        LOGGER.log(Level.INFO, "ComentarioResource postComentario: output: {0}", nuevoComentarioDTO);
        return nuevoComentarioDTO;
    }
    
    /**
     * Busca y devuelve todos los comentarios que existen en la aplicacion.
     *
     * @return JSONArray {@link ComentarioDTO} - Los comentarios encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ComentarioDTO> getComentarios() {
        LOGGER.info("ComentarioResource getComentarios: input: void");
        List<ComentarioDTO> listComentarios = listEntity2DTO(comentarioLogic.getComentarios());
        LOGGER.log(Level.INFO, "ComentarioResource getComentarios: output: {0}", listComentarios);
        return listComentarios;
    }
    
    /**
     * Busca el comentario con el id asociado recibido en la URL y lo devuelve.
     *
     * @param comentariosId Identificador del comentario que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ComentarioDTO} - El comentario buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la boleta.
     */
    @GET
    @Path("{comentariosId : \\d+}")
    public ComentarioDTO getComentario(@PathParam("comentariosId") Long comentariosId) 
    {
        LOGGER.log(Level.INFO,"ComentarioResource getComentario: input: {0}", comentariosId);
        ComentarioEntity comentarioEntity = comentarioLogic.getComentario(comentariosId);
        if(comentarioEntity == null)
        {
            throw new WebApplicationException(recurso+comentariosId+existe, 404);
        }
        ComentarioDTO comentarioDTO = new ComentarioDTO(comentarioEntity);
        LOGGER.log(Level.INFO, "ComentarioResource getComentario: output: {0}", comentariosId);
        return comentarioDTO;
    }
    
    /**
     * Actualiza la boleta con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param boletasId Identificador de la boleta que se desea actualizar. Este debe
     * ser una cadena de dígitos.
     * @param boleta {@link BoletaDTO} La boleta que se desea guardar.
     * @return JSON {@link BoletaDTO} - La boleta guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la boleta a
     * actualizar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede actualizar la boleta.
     */
    @PUT
    @Path("{comentariosId: \\d+}")
    public ComentarioDTO updateComentario(@PathParam("comentariosId") Long comentariosId, ComentarioDTO comentario) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "ComentarioResource updateComentario: input: id:{0} , comentario: {1}", new Object[]{comentariosId, comentario});
        comentario.setId(comentariosId);
        if (comentarioLogic.getComentario(comentariosId) == null) {
            throw new WebApplicationException(recurso+comentariosId+existe, 404);
        }
        ComentarioDTO comentarioDTO = new ComentarioDTO(comentarioLogic.updateComentario(comentariosId, comentario.toEntity()));
        LOGGER.log(Level.INFO, "ComentarioResource updateComentario: output: {0}", comentarioDTO);
        return comentarioDTO;
    }
    
    /**
     * Borra el comentario con el id asociado recibido en la URL.
     *
     * @param comentariosId Identificador del comentario que se desea borrar. Este debe ser
     * una cadena de dígitos.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el comentario.
     */
    @DELETE
    @Path("{comentariosId: \\d+}")
    public void deleteComentario(@PathParam("comentariosId") Long comentariosId) 
    { 
        LOGGER.log(Level.INFO, "ComentarioResource deleteComentario: input: {0}", comentariosId);
        if (comentarioLogic.getComentario(comentariosId) == null) {
            throw new WebApplicationException(recurso+comentariosId+existe, 404);
        }
        comentarioLogic.deleteComentario(comentariosId);
        LOGGER.info("ComentarioResource deleteComentario: output: void");
    }
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos ComentarioEntity a una lista de
     * objetos ComentarioDTO (json)
     *
     * @param entityList corresponde a la lista de libros de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de comentarios en forma DTO (json)
     */
    private List<ComentarioDTO> listEntity2DTO(List<ComentarioEntity> entityList) {
        List<ComentarioDTO> list = new ArrayList<>();
        for (ComentarioEntity entity : entityList) {
            list.add(new ComentarioDTO(entity));
        }
        return list;
    }
    
    
    
}

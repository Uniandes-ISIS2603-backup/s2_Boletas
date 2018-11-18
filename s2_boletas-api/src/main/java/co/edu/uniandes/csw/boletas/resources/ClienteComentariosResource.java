/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.dtos.ComentarioDTO;
import co.edu.uniandes.csw.boletas.ejb.ClienteComentarioLogic;
import co.edu.uniandes.csw.boletas.ejb.ComentarioLogic;
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
 * Clase que implementa el recurso "cliente/{id}/comentarios"
 * @author Diego Camacho y Vilma Tirado 
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteComentariosResource {
    private static final String recursoCom = "El Recurso /comentarios/ ";
    private static final String recursoCl = "El Recurso /clientes/ ";
    private static final String existe = " /no existe";
    private static final Logger LOGGER = Logger.getLogger(ClienteComentariosResource.class.getName());
    
    @Inject
    private ClienteComentarioLogic clienteComentarioLogic;
    @Inject
    private ComentarioLogic comentarioLogic;
    
    /**
     * Guarda un comentario dentro de un cliente con la informacion que recibe el
     * la URL. Se devuelve el comentario que se guarda en el cliente.
     *
     * @param clientesId Identificador del cliente que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param comentarioId Identificador del comentario que se desea guardar. Este debe
     * ser una cadena de dígitos.
        * @return JSON {@link ComentarioDTO} - El comentario guardado en el cliente.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el comentario.
     */
    @POST
    @Path("{comentariosId: \\d+}")
    public ComentarioDTO addComentario(@PathParam("clienteId") Long clientesId, @PathParam("comentariosId") Long comentariosId) {
        LOGGER.log(Level.INFO, "ClienteComentariosResource addComentario: input: clientesId: {0} , comentariosId: {1}", new Object[]{clientesId, comentariosId});
        if (comentarioLogic.getComentario(comentariosId) == null) {
            throw new WebApplicationException(recursoCom + comentariosId + existe, 404);
        }
        ComentarioDTO comentarioDTO = new ComentarioDTO(clienteComentarioLogic.addComentario(comentariosId, clientesId));
        LOGGER.log(Level.INFO, "ClienteComentariosResource addComentario: output: {0}", comentarioDTO);
        return comentarioDTO;
    }
    
    /**
     * Busca y devuelve todos los comentarios que tiene un cliente.
     *
     * @param clientesId Identificador del comentario que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link ComentarioDTO} - Los comentarios encontrados en el
     * cliente. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ComentarioDTO> getComentarios(@PathParam("clienteId") Long clientesId) {
        LOGGER.log(Level.INFO, "ClienteComentariosResource getComentarios: input: {0}", clientesId);
        List<ComentarioDTO> lista = listEntity2DTO(clienteComentarioLogic.getComentarios(clientesId));
        LOGGER.log(Level.INFO, "ClienteComentarioResource getComentarios: output: {0}", lista);
        return lista;
    }
    
    /**
     * Busca el comentario con el id asociado dentro de los comentarios del cliente con id asociado.
     *
     * @param clientesId Identificador del cliente que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param comentariosId Identificador del comentario que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ComentarioDTO} - El comentario buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el comentario.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el comentario en la lista de 
     * comentarios del cliente.
     */
    @GET
    @Path("{comentariosId: \\d+}")
    public ComentarioDTO getComentario(@PathParam("clienteId") Long clientesId, @PathParam("comentariosId") Long comentariosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClienteComentariosResource getComentario: input: clientesId: {0} , comentariosId: {1}", new Object[]{clientesId, comentariosId});
        if (comentarioLogic.getComentario(comentariosId) == null) {
            throw new WebApplicationException(recursoCl + clientesId + "/comentarios/" + comentariosId + existe, 404);
        }
        ComentarioDTO comentarioDTO = new ComentarioDTO(clienteComentarioLogic.getComentario(clientesId, comentariosId));
        LOGGER.log(Level.INFO, "ClienteComentariosResource getComentario: output: {0}", comentarioDTO);
        return comentarioDTO;
    }
    
     /**
     * Remplaza las instancias de Comentario asociadas a una instancia de Cliente
     *
     * @param clientesId Identificador del cliente que se esta
     * remplazando. Este debe ser una cadena de dígitos.
     * @param comentarios JSONArray {@link ComentarioDTO} El arreglo de comentarios nuevos para el
     * cliente.
     * @return JSON {@link ComentarioDTO} - El arreglo de comentarios guardado en el
     * cliente.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el comentario.
     */
    @PUT
    public List<ComentarioDTO> replaceComentarios(@PathParam("clientesId") Long clientesId, List<ComentarioDTO> comentarios) {
        LOGGER.log(Level.INFO, "ClienteComentariosResource replaceComentarios: input: clientesId: {0} , comentarios: {1}", new Object[]{clientesId, comentarios});
        for (ComentarioDTO comentario : comentarios) {
            if (comentarioLogic.getComentario(comentario.getId()) == null) {
                throw new WebApplicationException(recursoCom + comentario.getId() + existe, 404);
            }
        }
        List<ComentarioDTO> listaDTOs = listEntity2DTO(clienteComentarioLogic.replaceComentarios(clientesId, listDTO2Entity(comentarios)));
        LOGGER.log(Level.INFO, "ClienteComentariosResource replaceComentarios: output: {0}", listaDTOs);
        return listaDTOs;
    }
    
    /**
     * Convierte una lista de ComentarioEntity a una lista de ComentarioDTO.
     *
     * @param entityList Lista de ComentarioEntity a convertir.
     * @return Lista de ComentarioDTO convertida.
     */
    private List<ComentarioDTO> listEntity2DTO(List<ComentarioEntity> entityList) {
        List<ComentarioDTO> list = new ArrayList();
        for (ComentarioEntity entity : entityList) {
            list.add(new ComentarioDTO(entity));
        }
        return list;
    }
    
    /**
     * Convierte una lista de ComentarioDTO a una lista de ComentarioEntity.
     *
     * @param dtos Lista de ComentarioDTO a convertir.
     * @return Lista de ComentarioEntity convertida.
     */
    private List<ComentarioEntity> listDTO2Entity(List<ComentarioDTO> dtos) {
        List<ComentarioEntity> list = new ArrayList<>();
        for (ComentarioDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.dtos.ComentarioDTO;
import co.edu.uniandes.csw.boletas.ejb.BoletaLogic;
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
 * @author Juan Diego Camacho
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteComentariosResource {
    
    private static final Logger LOGGER = Logger.getLogger(ClienteComentariosResource.class.getName());
    
    @Inject
    private ClienteComentarioLogic clienteComentarioLogic;
    @Inject
    private ComentarioLogic comentarioLogic;
    

    @POST
    @Path("{comentariosId: \\d+}")
    public ComentarioDTO addComentario(@PathParam("clientesId") Long clientesId, @PathParam("comentariosId") Long comentariosId) {
        LOGGER.log(Level.INFO, "ClienteComentariosResource addComentario: input: clientesId: {0} , comentariosId: {1}", new Object[]{clientesId, comentariosId});
        if (comentarioLogic.getComentario(comentariosId) == null) {
            throw new WebApplicationException("El recurso /comentarios/" + comentariosId + " no existe.", 404);
        }
        ComentarioDTO comentarioDTO = new ComentarioDTO(clienteComentarioLogic.addComentario(comentariosId, clientesId));
        LOGGER.log(Level.INFO, "ClienteComentariosResource addComentario: output: {0}", comentarioDTO.toString());
        return comentarioDTO;
    }
    
    @GET
    public List<ComentarioDTO> getComentarios(@PathParam("clientesId") Long clientesId) {
        LOGGER.log(Level.INFO, "ClienteComentariosResource getComentarios: input: {0}", clientesId);
        List<ComentarioDTO> lista = listEntity2DTO(clienteComentarioLogic.getComentarios(clientesId));
        LOGGER.log(Level.INFO, "ClienteComentarioResource getComentarios: output: {0}", lista.toString());
        return lista;
    }
    
    @GET
    @Path("{comentariosId: \\d+}")
    public ComentarioDTO getComentario(@PathParam("clientesId") Long clientesId, @PathParam("clientesId") Long comentariosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClienteComentariosResource getComentario: input: clientesId: {0} , comentariosId: {1}", new Object[]{clientesId, comentariosId});
        if (comentarioLogic.getComentario(comentariosId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clientesId + "/comentarios/" + comentariosId + " no existe.", 404);
        }
        ComentarioDTO comentarioDTO = new ComentarioDTO(clienteComentarioLogic.getComentario(clientesId, comentariosId));
        LOGGER.log(Level.INFO, "ClienteComentariosResource getComentario: output: {0}", comentarioDTO.toString());
        return comentarioDTO;
    }
    
    @PUT
    public List<ComentarioDTO> replaceComentarios(@PathParam("clientesId") Long clientesId, List<ComentarioDTO> comentarios) {
        LOGGER.log(Level.INFO, "ClienteComentariosResource replaceComentarios: input: clientesId: {0} , comentarios: {1}", new Object[]{clientesId, comentarios.toString()});
        for (ComentarioDTO comentario : comentarios) {
            if (comentarioLogic.getComentario(comentario.getComentarioID()) == null) {
                throw new WebApplicationException("El recurso /comentarios/" + comentario.getComentarioID() + " no existe.", 404);
            }
        }
        List<ComentarioDTO> listaDTOs = listEntity2DTO(clienteComentarioLogic.replaceComentarios(clientesId, listDTO2Entity(comentarios)));
        LOGGER.log(Level.INFO, "ClienteComentariosResource replaceComentarios: output: {0}", listaDTOs.toString());
        return listaDTOs;
    }
    
    
    private List<ComentarioDTO> listEntity2DTO(List<ComentarioEntity> entityList) {
        List<ComentarioDTO> list = new ArrayList();
        for (ComentarioEntity entity : entityList) {
            list.add(new ComentarioDTO(entity));
        }
        return list;
    }
    
    private List<ComentarioEntity> listDTO2Entity(List<ComentarioDTO> dtos) {
        List<ComentarioEntity> list = new ArrayList<>();
        for (ComentarioDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
}

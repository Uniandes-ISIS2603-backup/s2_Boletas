/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.dtos.BoletaDTO;
import co.edu.uniandes.csw.boletas.ejb.BoletaLogic;
import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;



/**
 * Clase que representa el recurso boleta
 * @author Diego Camacho
 */
@Path("boletas")
@Produces("application/json")
@Consumes("application/json")
public class BoletaResource {
    
    /**
     * Dependencia de la lógica, conexión con la persistencia
     */
    @Inject 
    private BoletaLogic boletaLogic;
    
    private static final Logger LOGGER = Logger.getLogger(BoletaResource.class.getName());
    
    /**
     * Crea una nueva boleta con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param boleta {@link BoletaDTO} - La boleta que se desea guardar.
     * @return JSON {@link BoletaTO} - La boleta guardada con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la boleta 
     */
    @POST
    public BoletaDTO postBoleta(BoletaDTO boleta) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "BoletaResource postBoleta: input: {0}", boleta.toString());
        BoletaEntity boletaEntity = boleta.toEntity();
        BoletaEntity nuevaBoletaEntity = boletaLogic.createBoleta(boletaEntity);
        BoletaDTO nuevaBoletaDto =new BoletaDTO(nuevaBoletaEntity);
        LOGGER.log(Level.INFO, "BoletaResource createBoleta: output: {0}", nuevaBoletaDto.toString());
        return nuevaBoletaDto;
    }
    
    
    @GET
    @Path("{boletasId : \\d+}")
    public BoletaDTO getBoleta(@PathParam("boletasId") Long boletasId) throws WebApplicationException
    {
        LOGGER.log(Level.INFO,"BoletaResource getBoleta: input: {0}", boletasId);
        BoletaEntity boletaEntity = boletaLogic.getBoleta(boletasId);
        if(boletaEntity == null)
        {
            throw new WebApplicationException("El recurso /boletas/"+boletasId+" no existe.", 404);
        }
        BoletaDTO boletaDTO = new BoletaDTO(boletaEntity);
        LOGGER.log(Level.INFO, "BoletaResource getBoleta: output: {0}", boletasId.toString());
        return boletaDTO;
    }
    
    @PUT
    @Path("{boletasId: \\d+}")
    public BoletaDTO updateBoleta(@PathParam("boletasId") Long boletasId, BoletaDTO boleta) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "BoletaResource updateBoleta: input: id:{0} , boleta: {1}", new Object[]{boletasId, boleta.toString()});
        boleta.setId(boletasId);
        if (boletaLogic.getBoleta(boletasId) == null) {
            throw new WebApplicationException("El recurso /boletas/" + boletasId + " no existe.", 404);
        }
        BoletaDTO boletaDTO = new BoletaDTO(boletaLogic.updateBoleta(boletasId, boleta.toEntity()));
        LOGGER.log(Level.INFO, "BoletaResource updateBoleta: output: {0}", boletaDTO.toString());
        return boletaDTO;
    }
    @DELETE
    @Path("{boletasId: \\d+}")
    public void deleteBoleta(@PathParam("boletasId") Long boletasId) throws BusinessLogicException
    { 
        LOGGER.log(Level.INFO, "BoletaResource deleteBoleta: input: {0}", boletasId);
        if (boletaLogic.getBoleta(boletasId) == null) {
            throw new WebApplicationException("El recurso /boletas/" + boletasId + " no existe.", 404);
        }
        boletaLogic.deleteBoleta(boletasId);
        LOGGER.info("BoletaResource deleteBoleta: output: void");
    }
    
}

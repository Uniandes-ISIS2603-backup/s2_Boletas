/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.dtos.BoletaDTO;
import co.edu.uniandes.csw.boletas.ejb.BoletaLogic;
import co.edu.uniandes.csw.boletas.ejb.CompraBoletasLogic;
import co.edu.uniandes.csw.boletas.ejb.CompraLogic;
import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.*;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Gabriel Hamilton 
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CompraBoletasResource {
    
    
    private static final Logger LOGGER = Logger.getLogger(CompraBoletasResource.class.getName());

    @Inject
    private CompraBoletasLogic compraBoletasLogic;

    @Inject
    private BoletaLogic boletaLogic;
    
    @Inject
    private CompraLogic compraLogic;

    /**
     * Guarda una boleta dentro de una compra con la informacion que recibe
     * la URL. Se devuelve la boleta que se guarda en la compra.
     *
     * @param compraId Identificador de la compra que se esta
     * actualizando.
     * @param boletaId Identificador de la boleta que se desea guardar.
     * @return JSON {@link BoletaDTO} - La boleta guardada en la compra.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la boleta.
     */
    @POST
    @Path("{compraId: \\d+}")
    public BoletaDTO postBoleta(@PathParam("compraId") Long compraId, @PathParam("boletaId") Long boletaId) 
    {
        LOGGER.log(Level.INFO, "CompraBoletasResource postBoleta: input: compraId: {0} , boletaId: {1}", new Object[]{compraId, boletaId});
        if (boletaLogic.getBoleta(boletaId) == null) {
            throw new WebApplicationException("El recurso /boletas/" + boletaId + " no existe.", 404);
        }
        BoletaDTO boletaDTO = new BoletaDTO(compraBoletasLogic.createBoleta(boletaId, compraId));
        LOGGER.log(Level.INFO, "CompraBoletasResource postBoleta: output: {0}", boletaDTO.toString());
        return boletaDTO;
    }
    
    /**
     * Busca y devuelve todas las boletas que existen en la compra.
     *
     * @param compraId Identificador de la compra que se esta buscando.
     * @return JSONArray {@link BoletaDTO} - Las boletas encontradas en la
     * compra.
     */
    @GET
    public List<BoletaDTO> getBoletas(@PathParam("compraId") Long compraId) {
        LOGGER.log(Level.INFO, "CompraBoletasResource getBoletas: input: {0}", compraId);
        List<BoletaDTO> listaDTOs = boletasListEntityToDTO(compraBoletasLogic.getBoletas(compraId));
        LOGGER.log(Level.INFO, "CompraBoletasResource getBoletas: output: {0}", listaDTOs.toString());
        return listaDTOs;
    }

    /**
     * Busca la boleta con el id asociado dentro de la compra con id asociado.
     *
     * @param compraId Identificador de la compra que se esta buscando.
     * @param boletaId Identificador de la boleta que se esta buscando.
     * @return JSON {@link BoletaDTO} - La boleta buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la boleta.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la boleta en la
     * compra.
     */
    @GET
    @Path("{boletaId: \\d+}")
    public BoletaDTO getBoleta(@PathParam("compraId") Long compraId, @PathParam("boletaId") Long boletaId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CompraBoletasResource getBoleta: input: CompraId: {0} , boletaId: {1}", new Object[]{compraId, boletaId});
        if (boletaLogic.getBoleta(boletaId) == null) {
            throw new WebApplicationException("El recurso /compras/" + compraId + "/boletas/" + boletaId + " no existe.", 404);
        }
        BoletaDTO boletaDTO = new BoletaDTO(compraBoletasLogic.getBoleta(compraId, boletaId));
        LOGGER.log(Level.INFO, "CompraBoletasResource getBoleta: output: {0}", boletaDTO.toString());
        return boletaDTO;
    }

    /**
     * Remplaza las instancias de Boletas asociadas a una instancia de Compra
     *
     * @param compraId Identificador de la compra que se esta
     * remplazando. 
     * @param boletas JSONArray {@link BoletaDTO} El arreglo de boletas nueva para la
     * compra.
     * @return JSON {@link BoletaDTO} - El arreglo de boletas guardado en la
     * compra.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la boleta.
     */
    @PUT
    public List<BoletaDTO> putBoletas(@PathParam("compraId") Long compraId, List<BoletaDTO> boletas) {
        LOGGER.log(Level.INFO, "CompraBoletasResource putBoletas: input: compraId: {0} , boletas: {1}", new Object[]{compraId, boletas.toString()});
        for (BoletaDTO boleta : boletas) {
            if (boletaLogic.getBoleta(boleta.getID()) == null) {
                throw new WebApplicationException("El recurso /boletas/" + boleta.getID() + " no existe.", 404);
            }
        }
        List<BoletaDTO> listaDTOs = boletasListEntityToDTO(compraBoletasLogic.putBoletas(compraId, boletasListDTO2Entity(boletas)));
        LOGGER.log(Level.INFO, "CompraBoletasResource putBoletas: output: {0}", listaDTOs.toString());
        return listaDTOs;
    }

    
    /**
     * Elimina la conexión entre la compra y la boleta recibidos en la URL.
     *
     * @param compraId El ID de la compra a la cual se le va a desasociar la boleta
     * @param boletaId El ID de la boleta que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la boleta o la compra.
     */
    @DELETE
    @Path("{boletaId: \\d+}")
    public void deleteBoleta(@PathParam("compraId") Long compraId, @PathParam("boletaId") Long boletaId) {
        LOGGER.log(Level.INFO, "CompraBoletasResource deleteBoleta: input: compraId {0} , boletaId {1}", new Object[]{compraId, boletaId});
        if (boletaLogic.getBoleta(boletaId) == null) {
            throw new WebApplicationException("El recurso /boletas/" + boletaId + " no existe.", 404);
        }
        else if (compraLogic.getCompra(compraId)==null)
        {
            throw new WebApplicationException("El recurso /compras/" + compraId + " no existe.", 404);            
        }
        compraBoletasLogic.deleteBoleta(compraId, boletaId);
        LOGGER.info("CompraBoletasResource deleteBoleta: output: void");
    }
    
    /**
     * Convierte una lista de BoletaEntity a una lista de BoletaDTO.
     *
     * @param entityList Lista de BoletaEntity a convertir.
     * @return Lista de BoletaDTO convertida.
     */
    private List<BoletaDTO> boletasListEntityToDTO(List<BoletaEntity> entityList) {
        List<BoletaDTO> list = new ArrayList();
        for (BoletaEntity entity : entityList) {
            list.add(new BoletaDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de BoletaDTO a una lista de BoletaEntity.
     *
     * @param dtos Lista de BoletaDTO a convertir.
     * @return Lista de BoletaEntity convertida.
     */
    private List<BoletaEntity> boletasListDTO2Entity(List<BoletaDTO> dtos) {
        List<BoletaEntity> list = new ArrayList<>();
        for (BoletaDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
}

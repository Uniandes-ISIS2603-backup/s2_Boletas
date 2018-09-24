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

    /**
     * Guarda una boleta dentro de una compra con la informacion que recibe el
     * la URL. Se devuelve la boleta que se guarda en la compra.
     *
     * @param comprasId Identificador de la compra que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param boletasId Identificador de la boleta que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link BoletaDTO} - La boleta guardada en la compra.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la boleta.
     */
    @POST
    @Path("{boletasId: \\d+}")
    public BoletaDTO addBoleta(@PathParam("comprasId") Long comprasId, @PathParam("boletasId") Long boletasId) {
        LOGGER.log(Level.INFO, "CompraBoletasResource addBoleta: input: comprasID: {0} , boletasId: {1}", new Object[]{comprasId, boletasId});
        if (boletaLogic.getBoleta(boletasId) == null) {
            throw new WebApplicationException("El recurso /boletas/" + boletasId + " no existe.", 404);
        }
        BoletaDTO boletaDTO = new BoletaDTO(compraBoletasLogic.addBoleta(comprasId, boletasId));
        LOGGER.log(Level.INFO, "CompraBoletasResource addBoleta: output: {0}", boletaDTO.toString());
        return boletaDTO;
    }

    /**
     * Busca y devuelve todas las boletas que existen en la compra.
     *
     * @param comprasId Identificador de la compra que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link BoletaDTO} - Las boletas encontradas en la
     * compra. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<BoletaDTO> getBoletas(@PathParam("comprasId") Long comprasId) {
        LOGGER.log(Level.INFO, "CompraBoletasResource getBoletas: input: {0}", comprasId);
        List<BoletaDTO> listaDetailDTOs = boletasListEntity2DTO(compraBoletasLogic.getBoletas(comprasId));
        LOGGER.log(Level.INFO, "CompraBoletasResource getBoletas: output: {0}", listaDetailDTOs.toString());
        return listaDetailDTOs;
    }

    /**
     * Busca la boleta con el id asociado dentro de la compra con id asociado.
     *
     * @param comprasId Identificador de la compra que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param boletasId Identificador de la boleta que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link BoletaDTO} - La boleta buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la boleta.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la boleta en la
     * compra.
     */
    @GET
    @Path("{boletasId: \\d+}")
    public BoletaDTO getBoleta(@PathParam("comprasId") Long comprasId, @PathParam("boletasId") Long boletasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CompraBoletasResource getBoleta: input: comprasID: {0} , boletasId: {1}", new Object[]{comprasId, boletasId});
        if (boletaLogic.getBoleta(boletasId) == null) {
            throw new WebApplicationException("El recurso /compras/" + comprasId + "/boletas/" + boletasId + " no existe.", 404);
        }
        BoletaDTO boletaDetailDTO = new BoletaDTO(compraBoletasLogic.getBoleta(comprasId, boletasId));
        LOGGER.log(Level.INFO, "CompraBoletasResource getBoleta: output: {0}", boletaDetailDTO.toString());
        return boletaDetailDTO;
    }

    /**
     * Remplaza las instancias de Boleta asociadas a una instancia de Compra
     *
     * @param comprasId Identificador de la compra que se esta
     * remplazando. Este debe ser una cadena de dígitos.
     * @param boletas JSONArray {@link BoletaDTO} El arreglo de boletas nuevo para la
     * compra.
     * @return JSON {@link BoletaDTO} - El arreglo de boletas guardado en la
     * compra.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la boleta.
     */
    @PUT
    public List<BoletaDTO> replaceBoletas(@PathParam("comprasId") Long comprasId, List<BoletaDTO> boletas) {
        LOGGER.log(Level.INFO, "CompraBoletasResource replaceBoletas: input: comprasId: {0} , boletas: {1}", new Object[]{comprasId, boletas.toString()});
        for (BoletaDTO boleta : boletas) {
            if (boletaLogic.getBoleta(boleta.getId()) == null) {
                throw new WebApplicationException("El recurso /boletas/" + boleta.getId() + " no existe.", 404);
            }
        }
        List<BoletaDTO> listaDetailDTOs = boletasListEntity2DTO(compraBoletasLogic.putBoletas(comprasId, boletasListDTO2Entity(boletas)));
        LOGGER.log(Level.INFO, "CompraBoletasResource replaceBoletas: output: {0}", listaDetailDTOs.toString());
        return listaDetailDTOs;
    }

    /**
     * Convierte una lista de BoletaEntity a una lista de BoletaDTO.
     *
     * @param entityList Lista de BoletaEntity a convertir.
     * @return Lista de BoletaDTO convertida.
     */
    private List<BoletaDTO> boletasListEntity2DTO(List<BoletaEntity> entityList) {
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.biblioteca.mappers.BusinessLogicExceptionMapper;
import co.edu.uniandes.csw.biblioteca.mappers.WebApplicationExceptionMapper;
import co.edu.uniandes.csw.boletas.dtos.EspectaculoDTO;
import co.edu.uniandes.csw.boletas.dtos.EspectaculoDetailDTO;
import co.edu.uniandes.csw.boletas.ejb.EspectaculoLogic;
import co.edu.uniandes.csw.boletas.ejb.LugarEspectaculoLogic;
import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Vilma Tirado y Jhonatan Amortegui 
 */
public class LugarEspectaculoResource {
    private static final Logger LOGGER = Logger.getLogger(LugarEspectaculoResource.class.getName());

    @Inject
    private LugarEspectaculoLogic lugarEspectaculosLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private EspectaculoLogic espectaculoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Guarda un libro dentro de una lugar con la informacion que recibe el
     * la URL. Se devuelve el libro que se guarda en la lugar.
     *
     * @param lugarId Identificador de la lugar que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param espectaculosId Identificador del libro que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link EspectaculoDTO} - El libro guardado en la lugar.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @POST
    @Path("{espectaculosId: \\d+}")
    public EspectaculoDTO addEspectaculo(@PathParam("lugarId") Long lugarId, @PathParam("espectaculosId") Long espectaculosId) {
        LOGGER.log(Level.INFO, "LugarEspectaculosResource addEspectaculo: input: lugarID: {0} , espectaculosId: {1}", new Object[]{lugarId, espectaculosId});
        if (espectaculoLogic.getEspectaculo(espectaculosId) == null) {
            throw new WebApplicationException("El recurso /espectaculos/" + espectaculosId + " no existe.", 404);
        }
        EspectaculoDTO espectaculoDTO = new EspectaculoDTO(lugarEspectaculosLogic.addEspectaculo(espectaculosId, lugarId));
        LOGGER.log(Level.INFO, "LugarEspectaculosResource addEspectaculo: output: {0}", espectaculoDTO);
        return espectaculoDTO;
    }

    /**
     * Busca y devuelve todos los libros que existen en la lugar.
     *
     * @param lugarId Identificador de la lugar que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link EspectaculoDetailDTO} - Los libros encontrados en la
     * lugar. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<EspectaculoDetailDTO> getEspectaculos(@PathParam("lugarId") Long lugarId) {
        LOGGER.log(Level.INFO, "LugarEspectaculosResource getEspectaculos: input: {0}", lugarId);
        List<EspectaculoDetailDTO> listaDetailDTOs = espectaculosListEntity2DTO(lugarEspectaculosLogic.getEspectaculos(lugarId));
        LOGGER.log(Level.INFO, "LugarEspectaculosResource getEspectaculos: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     * Busca el libro con el id asociado dentro de la lugar con id asociado.
     *
     * @param lugarId Identificador de la lugar que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param espectaculosId Identificador del libro que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link EspectaculoDetailDTO} - El libro buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro en la
     * lugar.
     */
    @GET
    @Path("{espectaculosId: \\d+}")
    public EspectaculoDetailDTO getEspectaculo(@PathParam("lugarId") Long lugarId, @PathParam("espectaculosId") Long espectaculosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "LugarEspectaculosResource getEspectaculo: input: lugarID: {0} , espectaculosId: {1}", new Object[]{lugarId, espectaculosId});
        if (espectaculoLogic.getEspectaculo(espectaculosId) == null) {
            throw new WebApplicationException("El recurso /lugar/" + lugarId + "/espectaculos/" + espectaculosId + " no existe.", 404);
        }
        EspectaculoDetailDTO espectaculoDetailDTO = new EspectaculoDetailDTO(lugarEspectaculosLogic.getEspectaculo(lugarId, espectaculosId));
        LOGGER.log(Level.INFO, "LugarEspectaculosResource getEspectaculo: output: {0}", espectaculoDetailDTO);
        return espectaculoDetailDTO;
    }

    /**
     * Remplaza las instancias de Espectaculo asociadas a una instancia de Lugar
     *
     * @param lugarId Identificador de la lugar que se esta
     * remplazando. Este debe ser una cadena de dígitos.
     * @param espectaculos JSONArray {@link EspectaculoDTO} El arreglo de libros nuevo para la
     * lugar.
     * @return JSON {@link EspectaculoDTO} - El arreglo de libros guardado en la
     * lugar.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @PUT
    public List<EspectaculoDetailDTO> replaceEspectaculos(@PathParam("lugarId") Long lugarId, List<EspectaculoDetailDTO> espectaculos) {
        LOGGER.log(Level.INFO, "LugarEspectaculosResource replaceEspectaculos: input: lugarId: {0} , espectaculos: {1}", new Object[]{lugarId, espectaculos});
        for (EspectaculoDetailDTO espectaculo : espectaculos) {
            if (espectaculoLogic.getEspectaculo(espectaculo.getId()) == null) {
                throw new WebApplicationException("El recurso /espectaculos/" + espectaculo.getId() + " no existe.", 404);
            }
        }
        List<EspectaculoDetailDTO> listaDetailDTOs = espectaculosListEntity2DTO(lugarEspectaculosLogic.replaceEspectaculos(lugarId, espectaculosListDTO2Entity(espectaculos)));
        LOGGER.log(Level.INFO, "LugarEspectaculosResource replaceEspectaculos: output: {0}", listaDetailDTOs);
        return listaDetailDTOs;
    }

    /**
     * Convierte una lista de EspectaculoEntity a una lista de EspectaculoDetailDTO.
     *
     * @param entityList Lista de EspectaculoEntity a convertir.
     * @return Lista de EspectaculoDTO convertida.
     */
    private List<EspectaculoDetailDTO> espectaculosListEntity2DTO(List<EspectaculoEntity> entityList) {
        List<EspectaculoDetailDTO> list = new ArrayList();
        for (EspectaculoEntity entity : entityList) {
            list.add(new EspectaculoDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de EspectaculoDetailDTO a una lista de EspectaculoEntity.
     *
     * @param dtos Lista de EspectaculoDetailDTO a convertir.
     * @return Lista de EspectaculoEntity convertida.
     */
    private List<EspectaculoEntity> espectaculosListDTO2Entity(List<EspectaculoDetailDTO> dtos) {
        List<EspectaculoEntity> list = new ArrayList<>();
        for (EspectaculoDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.ejb.OrganizadorEspectaculoLogic;
import co.edu.uniandes.csw.boletas.dtos.EspectaculoDetailDTO;
import co.edu.uniandes.csw.boletas.ejb.EspectaculoLogic;
import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
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
 * Clase que implementa el subrecurso /organizadores/{id}/espectaculos
 * @author Vilma Tirado y Sebastian Rodriguez 
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrganizadorEspectaculoResourse 
{
    
    private static final String RECURSOES = "El Recurso /espectaculos/ ";
    private static final String RECURSOOR = "El Recurso /organizadores/ ";
    private static final String EXISTE = " no existe";
    private static final Logger LOGGER = Logger.getLogger(OrganizadorEspectaculoResourse.class.getName());

   @Inject
   private OrganizadorEspectaculoLogic organizadorEspectaculoLogic;

    @Inject
    private EspectaculoLogic espectaculoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    
    
    
    /**
     * Metodo que define el recurso organizadores/{id}/espectaculos
     * para agregar un organizador a un espectaculo
     * @param organizadoresId
     * @param espectaculosId
     * @return Un espectaculo con su organizador definido
     */
    @POST
    @Path("{espectaculosId: \\d+}")
    public EspectaculoDetailDTO addEspectaculo(@PathParam("organizadorId") Long organizadoresId, @PathParam("espectaculosId") Long espectaculosId)
    {
        LOGGER.log(Level.INFO, "OrganizadorEspectaculoResourse addEspectaculo: input: organizadorId: {0}, espectaculosId: {1}", new Object[]{organizadoresId, espectaculosId});
        if (espectaculoLogic.getEspectaculo(espectaculosId) == null) {
            throw new WebApplicationException(RECURSOES + espectaculosId + EXISTE, 404);
        }
        EspectaculoDetailDTO espectaculoDTO = new EspectaculoDetailDTO(organizadorEspectaculoLogic.addEspectaculo(espectaculosId, organizadoresId));
        
        LOGGER.log(Level.INFO, "OrganizadorEspectaculoResourse addEspectaculo: output: {0}", espectaculoDTO);
        return espectaculoDTO;
    }
    
    
   /**
     * Busca y devuelve el espectaculo con el ID recibido en la URL, relativo a un
     * organizador.
     *
     * @param espectaculoId El ID del espectaculo que se busca
     * @param organizadorId El ID del organizador del cual se busca el espectaculo
     * @return {@link EspectaculoDetailDTO} - El espectaculo encontrado en el organizador.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el espectaculo.
     */
    @GET
    @Path("{espectaculoId: \\d+}")
    public EspectaculoDetailDTO getEspectaculo(@PathParam("organizadorId") Long organizadorId, @PathParam("espectaculoId") Long espectaculoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "OrganizadorEspectaculoResource getEspectaculo: input: organizadorId {0} , espectaculoId {1}", new Object[]{organizadorId, espectaculoId});
        if (espectaculoLogic.getEspectaculo(espectaculoId) == null) {
            throw new WebApplicationException(RECURSOES + espectaculoId + EXISTE, 404);
        }
        EspectaculoDetailDTO detailDTO = new EspectaculoDetailDTO(organizadorEspectaculoLogic.getEspectaculo(organizadorId, espectaculoId));
        LOGGER.log(Level.INFO, "OrganizadorEspectaculoResource getEspectaculo: output: {0}", detailDTO);
        return detailDTO;
    }
    
    
        /**
     * Busca y devuelve todos los espectaculos que estan asociados al organizador.
     *
     * @param organizadorId
     * @return JSONArray {@link EspectaculoDetailDTO} - Los espectaculos asociados al
     * organizador. Si no hay ninguno retorna una lista vacía.
     */
    
    @GET
    public List<EspectaculoDetailDTO> getEspectaculos (@PathParam("organizadorId") Long organizadorId)
    {
        LOGGER.log(Level.INFO, "OrganizadorEspectaculoResourse addEspectaculo: output: {0}", organizadorId);
        return espectaculosListEntity2DTO(organizadorEspectaculoLogic.getEspectaculos(organizadorId));
        
        
    }
    
    /**
     * Actualiza la lista de espectaculos de un organizador con la lista que se recibe en
     * el cuerpo.
     *
     * @param organizadorId El ID del organizador al cual se le va a asociar la lista de
     * espectaculos
     * @param espectaculos JSONArray {@link EspectaculoDetailDTO} - La lista de espectaculos
     * que se desea guardar.
     * @return JSONArray {@link EspectaculoDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra la espectaculo.
     */
    @PUT
    public List<EspectaculoDetailDTO> updateEspectaculos(@PathParam("organizadorId") Long organizadorId, List<EspectaculoDetailDTO> espectaculos) {
        LOGGER.log(Level.INFO, "OrganizadorEspectaculoResource replaceEspectaculos: input: organizadorId {0} , espectaculos {1}", new Object[]{organizadorId, espectaculos});
        for (EspectaculoDetailDTO espectaculo : espectaculos) {
            if (espectaculoLogic.getEspectaculo(espectaculo.getId()) == null) {
                throw new WebApplicationException(RECURSOES + espectaculo.getId() + EXISTE, 404);
            }
        }
        List<EspectaculoDetailDTO> lista = espectaculosListEntity2DTO(organizadorEspectaculoLogic.updateEspectaculos(organizadorId, espectaculoListDTO2Entity(espectaculos)));
        LOGGER.log(Level.INFO, "OrganizadorEspectaculoResource replaceEspectaculos: output:{0}", lista);
        return lista;
    }
    
        /**
     * Convierte una lista de espectaculoEntity  a una lista de EspectaculoDetailDTO.
     *
     * @param entityList Lista de espectaculoEntity a convertir.
     * @return Lista de espectaculoDTO convertida.
     */
    
    public List<EspectaculoDetailDTO> espectaculosListEntity2DTO(List<EspectaculoEntity> entityList)
    {
        List<EspectaculoDetailDTO> lista = new ArrayList();
        for (EspectaculoEntity entity: entityList)
        {
            lista.add(new EspectaculoDetailDTO(entity));
        }
        return lista;
    }
    
    
    
        /**
     * Convierte una lista de EspectaculoDetailDTO a una lista de EspectaculoEntity.
     *
     * @param dtos Lista de EspectaculoDetailDTO a convertir.
     * @return Lista de EspectaculoEntity convertida.
     */
    
    public List<EspectaculoEntity> espectaculoListDTO2Entity( List<EspectaculoDetailDTO> dtos)
    {
        List<EspectaculoEntity> lista= new ArrayList();
        for (EspectaculoDetailDTO dto: dtos)
        {
            lista.add(dto.toEntity());
        }
        return lista;
        
    }
    
    
    
}

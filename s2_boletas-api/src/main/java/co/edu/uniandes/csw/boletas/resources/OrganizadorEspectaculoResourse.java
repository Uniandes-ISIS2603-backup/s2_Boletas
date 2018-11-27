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
     * Retorna un espectaculo buscado por su id, asociado a un organizador asociado
     * a su id
     * @param espectaculosId
     * @param organizadoresId
     * @return
     * @throws BusinessLogicException 
     */
    @GET
    @Path("{espectaculosId: \\+d}")
    public EspectaculoDetailDTO getEspectaculo ( @PathParam("organizadorId") Long organizadoresId,@PathParam("espectaculosId") Long espectaculosId) throws BusinessLogicException      
    {
        if (espectaculoLogic.getEspectaculo(espectaculosId)==null)
            throw new WebApplicationException(RECURSOOR+ organizadoresId+ "/espectaculos / "+ espectaculosId + EXISTE,404);
        LOGGER.log(Level.INFO, "Estoy bien, (seguro?)");
        return new EspectaculoDetailDTO(organizadorEspectaculoLogic.getEspectaculo(organizadoresId, espectaculosId));
        
    }
    
    
        /**
     * Busca y devuelve todos los espectaculos que estan asociados al organizador.
     *
     * @param espectaculosId
     * @return JSONArray {@link EspectaculoDetailDTO} - Los espectaculos asociados al
     * organizador. Si no hay ninguno retorna una lista vacía.
     */
    
    @GET
    public List<EspectaculoDetailDTO> getEspectaculos (@PathParam("organizadorId") Long espectaculosId)
    {
        LOGGER.log(Level.INFO, "OrganizadorEspectaculoResourse addEspectaculo: output: {0}", espectaculosId);
        return espectaculosListEntity2DTO(organizadorEspectaculoLogic.getEspectaculos(espectaculosId));
        
        
    }
    
    /**
     * Metodo que remplaza los espectaculos de un organizador
     * @param organizadoresId
     * @param listaEspectaculo
     * @return 
     */
    @PUT
    public List<EspectaculoDetailDTO> remplazarEspectaculos(@PathParam("organizadoresId") Long organizadoresId, List<EspectaculoDetailDTO> listaEspectaculo)
    {
        for (EspectaculoDetailDTO espectaculo : listaEspectaculo) 
        {
          if(espectaculoLogic.getEspectaculo(espectaculo.getId()) == null)
          {
              throw new WebApplicationException(RECURSOES + espectaculo.getId()+ EXISTE,404);
          }
        }
        
        return espectaculosListEntity2DTO(organizadorEspectaculoLogic.updateEspectaculos(espectaculoListDTO2Entity(listaEspectaculo), organizadoresId));
        
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

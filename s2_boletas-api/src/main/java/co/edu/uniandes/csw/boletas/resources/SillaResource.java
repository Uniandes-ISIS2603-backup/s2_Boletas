/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.entities.SillaEntity;
import co.edu.uniandes.csw.boletas.dtos.SillaDTO;
import co.edu.uniandes.csw.boletas.ejb.SillaLogic;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
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
 *
 * @author ja.amortegui10
 */

@Path("sillas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped //A partir de ahí va a iniciar una transacción.
public class SillaResource {
    private static final String recurso = "El Recurso /sillas/ ";
    private static final String existe = " /no existe";
    private static final Logger LOGGER = Logger.getLogger(SillaResource.class.getName());
    @Inject
    private SillaLogic logic;
    
    /**
     * Método correspondiente al servicio Post Silla.
     * @param sillaDTO
     * @return
     * @throws BusinessLogicException 
     */
    @POST
    public SillaDTO createSilla(SillaDTO sillaDTO) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "SillaResource createSilla: ", sillaDTO);
        
        SillaEntity silla = sillaDTO.toEntity();
        
        SillaEntity nuevo = logic.createSilla(silla);
        
        return  new SillaDTO(nuevo);

    }
    
    /**
     * Método correspondiente al servicio Put Silla.
     * @param sillaId
     * @param sillaDTO
     * @return
     * @throws BusinessLogicException 
     */
    @PUT
    @Path("{sillaId : \\d+}")
    public SillaDTO updateSilla(@PathParam("sillaId") Long sillaId, SillaDTO sillaDTO)throws BusinessLogicException
    {
        
        LOGGER.log(Level.INFO, "SillaResource updateSilla: ", sillaDTO);
        sillaDTO.setId(sillaId);
        SillaEntity sillaEntity = sillaDTO.toEntity();
        if(logic.getSillaById(sillaId) == null)
        {
            throw new WebApplicationException(recurso + sillaId+ existe, 404);
        }
        
        return new SillaDTO(logic.updateSilla(sillaId, sillaEntity));
        
     
    }
    
    /**
     * Método correspondiente al servicio Get Silla.
     * @param sillaId
     * @return 
     */
    @GET
    @Path("{sillaId: \\d+}")
    public SillaDTO getSilla(@PathParam("sillaId")Long sillaId)
    {
        SillaEntity finded = logic.getSillaById(sillaId);
        if(finded == null)
        {
            throw new WebApplicationException(recurso + sillaId + existe,404 );
        }
        return new SillaDTO(finded);
    }
    
    /**
     * Método correspondiente al servicio Get Sillas.
     * @return 
     */
    @GET
    public List<SillaDTO> getSillas()
    {
        List<SillaEntity> sillasEntity = logic.getSillas();
        List<SillaDTO> sillasDTO = new ArrayList<>();
        for(SillaEntity entitieActual : sillasEntity)
            sillasDTO.add(new SillaDTO(entitieActual));
        return sillasDTO;
    }
    
    /**
     * Método correspondiente al servicio Delete Silla.
     * @param sillaId
     * @throws BusinessLogicException 
     */
    @DELETE
    @Path("{sillaId: \\d+}")
    public void deleteSilla(@PathParam("sillaId")Long sillaId)throws BusinessLogicException
    {
        
        if(logic.getSillaById(sillaId) == null)
        {
            throw new WebApplicationException(recurso + sillaId + existe, 404);
        }
        
        logic.deleteSilla(sillaId);
        
//        SillaEntity deleted = null;
//        try
//        {
//            deleted = logic.deleteSilla(silla_id);
//        }catch(BusinessLogicException bE)
//        { 
//            throw new WebApplicationException(bE.getMessage());
//        }
//        return new SillaDTO(deleted);
    }
}

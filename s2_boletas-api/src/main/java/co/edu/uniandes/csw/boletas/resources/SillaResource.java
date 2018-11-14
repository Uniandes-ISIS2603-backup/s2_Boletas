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
    private static final Logger LOGGER = Logger.getLogger(SillaResource.class.getName());
    @Inject
    private SillaLogic logic;
    
    /**
     * Método correspondiente al servicio Post Silla.
     * @param sillaDTO
     * @return
     * @throws WebApplicationException 
     */
    @POST
    public SillaDTO createSilla(SillaDTO sillaDTO)throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "SillaResource createSilla: ", sillaDTO.toString());
        SillaEntity created = null;
        try
        {
            created = logic.createSilla(sillaDTO.toEntity());
        }catch(BusinessLogicException bE)
        {
            throw new WebApplicationException(bE.getMessage());
        }
        
        return new SillaDTO(created);
    }
    
    /**
     * Método correspondiente al servicio Put Silla.
     * @param silla_id
     * @param sillaDTO
     * @return
     * @throws WebApplicationException 
     */
    @PUT
    @Path("{silla_id : \\d+}")
    public SillaDTO updateSilla(@PathParam("silla_id") Long silla_id, SillaDTO sillaDTO)throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "SillaResource updateSilla: ", sillaDTO.toString());
        SillaEntity sillaEntity = sillaDTO.toEntity();
        sillaEntity.setId(silla_id);
        SillaEntity updated = null;
        try
        {
            updated = logic.updateSilla(silla_id, sillaEntity);
        }catch(Exception e)
        {
            throw new WebApplicationException(e.getMessage());
        }
        return new SillaDTO(updated);
    }
    
    /**
     * Método correspondiente al servicio Get Silla.
     * @param silla_id
     * @return 
     */
    @GET
    @Path("{silla_id: \\d+}")
    public SillaDTO getSilla(@PathParam("silla_id")Long silla_id)
    {
        SillaEntity finded = logic.getSillaById(silla_id);
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
        List<SillaDTO> sillasDTO = new ArrayList<SillaDTO>();
        for(SillaEntity entitieActual : sillasEntity)
            sillasDTO.add(new SillaDTO(entitieActual));
        return sillasDTO;
    }
    
    /**
     * Método correspondiente al servicio Delete Silla.
     * @param silla_id
     * @return
     * @throws WebApplicationException 
     */
    @DELETE
    @Path("{silla_id: \\d+}")
    public SillaDTO deleteSilla(@PathParam("silla_id")Long silla_id)throws WebApplicationException
    {
        SillaEntity deleted = null;
        try
        {
            deleted = logic.deleteSilla(silla_id);
        }catch(BusinessLogicException bE)
        { 
            throw new WebApplicationException(bE.getMessage());
        }
        return new SillaDTO(deleted);
    }
}

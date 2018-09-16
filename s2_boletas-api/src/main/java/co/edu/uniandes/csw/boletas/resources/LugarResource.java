/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.entities.LugarEntity;
import co.edu.uniandes.csw.boletas.dtos.LugarDTO;
import co.edu.uniandes.csw.boletas.ejb.LugarLogic;
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
@Path("lugares")
@Consumes("application/json")
@Produces("application/json")
@RequestScoped
public class LugarResource {
    private static final Logger LOGGER = Logger.getLogger(LugarResource.class.getName());
    @Inject
    private LugarLogic logic;
    @POST
    public LugarDTO createLugar(LugarDTO lugarDTO)throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "LugarResource createLugar: ", lugarDTO.toString());
        LugarEntity lugarEntity = null;
        try
        {
            lugarEntity = logic.createLugar(lugarDTO.toEntity());
        }catch(BusinessLogicException bE)
        {
            throw new WebApplicationException(bE.getMessage());
        }
        
        LugarDTO createdDTO = new LugarDTO(lugarEntity);
        LOGGER.log(Level.INFO, createdDTO.toString());
        
        return createdDTO;
    }
    
    @PUT
    @Path("{lugar_id : \\d+}")
    public LugarDTO updateLugar(@PathParam("lugar_id")Long lugar_id, LugarDTO lugarDTO) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "LugarResource updateLugar: ", lugarDTO.toString());
        LugarEntity lugarEntity = lugarDTO.toEntity();
        LugarEntity updatedEntity = null;
        try
        {
           updatedEntity = logic.updateLugar(lugar_id, lugarEntity); 
        }catch(BusinessLogicException bE)
        {
            throw new WebApplicationException(bE.getMessage());
        }
        return new LugarDTO(updatedEntity);
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    @GET
    @Path("{id: \\d+}")
    public LugarDTO getLugar(@PathParam("id") Long id)throws WebApplicationException
    { 
        LugarEntity finded;
        try{
         finded = logic.getLugarById(id);
        }catch(Exception e)
        {
            throw new WebApplicationException(e.getMessage());
        }
        return new LugarDTO(finded);
    }
    
    @GET
    public List<LugarDTO> getLugares()
    {
        List<LugarEntity> lugaresEntities = logic.getLugares();
        List<LugarDTO> lugaresDTO = new ArrayList<LugarDTO>();
        for(LugarEntity entitieActual : lugaresEntities)
            lugaresDTO.add(new LugarDTO(entitieActual));
        return lugaresDTO;
    }
    
    @DELETE
    @Path("{lugar_id : \\d+}")
    public LugarDTO deleteLugar(@PathParam("lugar_id") Long id)
    {
        return new LugarDTO();
    }
}

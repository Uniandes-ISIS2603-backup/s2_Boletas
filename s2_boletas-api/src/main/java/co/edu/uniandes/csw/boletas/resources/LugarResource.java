/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.entities.LugarEntity;
import co.edu.uniandes.csw.boletas.dtos.LugarDTO;
import co.edu.uniandes.csw.boletas.dtos.LugarDetailDTO;
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
    
    /**
     * Método correspondiente al servicio Post Lugar.
     * @param lugarDTO
     * @return
     * @throws WebApplicationException 
     */
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
    
    /**
     * Método correspondiente al servicio Put Lugar.
     * @param lugar_id
     * @param lugarDTO
     * @return
     * @throws WebApplicationException 
     */
    @PUT
    @Path("{lugar_id : \\d+}")
    public LugarDTO updateLugar(@PathParam("lugar_id")Long lugar_id, LugarDTO lugarDTO) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "LugarResource updateLugar: ", lugarDTO.toString());
        LugarEntity lugarEntity = lugarDTO.toEntity();
        lugarEntity.setId(lugar_id);
        LugarEntity updatedEntity = null;
        try
        {
           updatedEntity = logic.updateLugar(lugar_id, lugarEntity); 
        }catch(BusinessLogicException bE)
        {
            throw new WebApplicationException(bE.getMessage());
        }
        return new LugarDetailDTO(updatedEntity);
    }
    
    /**
     * Método correspondiente al servicio Get Lugar.
     * @param id
     * @return 
     */
    @GET
    @Path("{id: \\d+}")
    public LugarDTO getLugar(@PathParam("id") Long id)throws WebApplicationException
    { 
        LugarEntity finded = null;
        try{
         finded = logic.getLugarById(id);
        }catch(Exception e)
        {
            throw new WebApplicationException(e.getMessage());
        }
        return new LugarDetailDTO(finded);
    }
    
    /**
     * Método correspondiente al servicio Get Lugares.
     * @return 
     */
    @GET
    public List<LugarDetailDTO> getLugares()
    {
        List<LugarEntity> lugaresEntities = logic.getLugares();
        return convertEntitiesToDTO(lugaresEntities);
    }
    /*
    @GET
    public List<LugarDetailDTO> getLugaresByNumSillas()throws WebApplicationException
    {
        List<LugarEntity> lugaresEntities = null;
        try
        {
            lugaresEntities = logic.getLugaresByNumSillas(numSillas);
        }catch(BusinessLogicException bLE)
        {
            throw new WebApplicationException(bLE.getMessage());
        }
        return convertEntitiesToDTO(lugaresEntities);
        
    }
    */
    
    /**
     * Método correspondiente al servicio Delete Lugar.
     * @param lugar_id
     * @return
     * @throws WebApplicationException 
     */
    @DELETE
    @Path("{lugar_id : \\d+}")
    public LugarDTO deleteLugar(@PathParam("lugar_id") Long lugar_id)throws WebApplicationException
    {
        LugarEntity deleted = null;
        try
        {
            deleted = logic.deleteLugar(lugar_id);
        }catch(BusinessLogicException bE)
        {
            throw new WebApplicationException(bE.getMessage());
        }
        return new LugarDTO(deleted);
    }
    
    private List<LugarDetailDTO> convertEntitiesToDTO(List<LugarEntity> lugaresEntities)
    {
        List<LugarDetailDTO> lugaresDTO = new ArrayList<LugarDetailDTO>();
        for(LugarEntity entitieActual : lugaresEntities)
            lugaresDTO.add(new LugarDetailDTO(entitieActual));
        return lugaresDTO;
    }
}

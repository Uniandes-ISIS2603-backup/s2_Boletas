/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.entities.LugarEntity;
import co.edu.uniandes.csw.boletas.dtos.LugarDTO;
import co.edu.uniandes.csw.boletas.dtos.LugarDetailDTO;
import co.edu.uniandes.csw.boletas.ejb.LugarEspectaculoLogic;
import co.edu.uniandes.csw.boletas.ejb.LugarLogic;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.Date;
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
import javax.ws.rs.QueryParam;
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
    private static final String RECURSO = "El Recurso /lugares/ ";
    private static final String EXISTE = " /no existe";
    private static final Logger LOGGER = Logger.getLogger(LugarResource.class.getName());
    @Inject
    private LugarLogic logic;
    @Inject
    private LugarEspectaculoLogic lugarEspectaculoLogic;
    
    /**
     * Método correspondiente al servicio Post Lugar.
     * @param lugarDTO
     * @return
     * @throws WebApplicationException 
     */
    @POST
    public LugarDetailDTO createLugar(LugarDTO lugarDTO)
    {
        LOGGER.log(Level.INFO, "LugarResource createLugar: {0}", lugarDTO);
        LugarEntity lugarEntity = null;
        try
        {
            lugarEntity = logic.createLugar(lugarDTO.toEntity());
        }catch(BusinessLogicException bE)
        {
            throw new WebApplicationException(bE.getMessage());
        }
        
        LugarDetailDTO createdDTO = new LugarDetailDTO(lugarEntity);
        LOGGER.log(Level.INFO,"LugarResource createLugar Terminado: {0}" ,createdDTO);
        
        return createdDTO;
    }
    
    /**
     * Método correspondiente al servicio Put Lugar.
     * @param lugarId
     * @param lugarDTO
     * @return
     * @throws WebApplicationException 
     */
    @PUT
    @Path("{lugarId : \\d+}")
    public LugarDetailDTO updateLugar(@PathParam("lugarId")Long lugarId, LugarDTO lugarDTO) 
    {
        LOGGER.log(Level.INFO, "LugarResource updateLugar: {0}", lugarDTO);
        LugarEntity lugarEntity = lugarDTO.toEntity();
        lugarEntity.setId(lugarId);
        LugarEntity updatedEntity = null;
        try
        {
           updatedEntity = logic.updateLugar(lugarId, lugarEntity); 
        }catch(BusinessLogicException bE)
        {
            throw new WebApplicationException(bE.getMessage());
        }
        if(updatedEntity == null)
            throw new WebApplicationException(RECURSO + lugarId + EXISTE,404 );
        return new LugarDetailDTO(updatedEntity);
    }
    
    /**
     * Método correspondiente al servicio Get Lugar.
     * @param id
     * @return 
     */
    @GET
    @Path("{id: \\d+}")
    public LugarDetailDTO getLugar(@PathParam("id") Long id)
    { 
        LugarEntity finded = logic.getLugarById(id);
        if(finded == null)
        {

             throw new WebApplicationException(RECURSO + id + EXISTE,404 );
      
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

    
    /**
     * Método que retorna una lista de dto's con los lugares que están disponibles en la fecha dada por paráemtro.
     * @param fecha
     * @return 
     */
    @Path("disponibles")
    @GET
    public List<LugarDetailDTO> getLugares(@QueryParam("fecha") Date fecha)
    {
        List<LugarEntity> lugaresDisponibles = new ArrayList<LugarEntity>();
        try
        {
            lugaresDisponibles = lugarEspectaculoLogic.getLugaresDisponiblles(fecha);
        }catch(Exception e)
        {
            throw new WebApplicationException(e.getMessage());
        }
         
        return convertEntitiesToDTO(lugaresDisponibles);
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
     * @param lugarId
     * @return
     * @throws WebApplicationException 
     */
    @DELETE
    @Path("{lugarId : \\d+}")
    public LugarDTO deleteLugar(@PathParam("lugarId") Long lugarId)
    {
        LugarEntity deleted = null;
        try
        {
            deleted = logic.deleteLugar(lugarId);
        }catch(BusinessLogicException bE)
        {
            throw new WebApplicationException(bE.getMessage());
        }
        if(deleted == null)
            throw new WebApplicationException(RECURSO + lugarId + EXISTE);
        return new LugarDTO(deleted);
    }
    
    private List<LugarDetailDTO> convertEntitiesToDTO(List<LugarEntity> lugaresEntities)
    {
        List<LugarDetailDTO> lugaresDTO = new ArrayList<>();
        for(LugarEntity entitieActual : lugaresEntities)
            lugaresDTO.add(new LugarDetailDTO(entitieActual));
        return lugaresDTO;
    }
    
        @Path("{lugarId: \\d+}/espectaculos")
    public Class<LugarEspectaculoResource> getLugarEspectaculoResourse(@PathParam("lugarId") Long lugarId) {
        if (logic.getLugarById(lugarId) == null) {
            throw new WebApplicationException(RECURSO + lugarId + EXISTE, 404);
        }
        return LugarEspectaculoResource.class;
    }
}


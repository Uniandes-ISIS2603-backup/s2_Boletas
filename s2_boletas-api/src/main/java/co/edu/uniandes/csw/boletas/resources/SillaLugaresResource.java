/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.dtos.LugarDetailDTO;
import co.edu.uniandes.csw.boletas.ejb.SillaLugaresLogic;
import co.edu.uniandes.csw.boletas.entities.LugarEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author estudiante
 */
@Path("lugares/by_num_sillas")
@Consumes("application/json")
@Produces("application/json")
@RequestScoped
public class SillaLugaresResource {
    private static final Logger LOGGER = Logger.getLogger(SillaLugaresResource.class.getName());
    
    @Inject
    private SillaLugaresLogic logic;
    
    /**
     * Métodoq ue retorna una lista de lugares con un número igual o mayor al dado por parámetro
     * @param numSillas
     * @return
     * @throws WebApplicationException 
     */
    @GET
    @Path("/by_num_sillas")
    public List<LugarDetailDTO> getLugaresByNumSillas(@QueryParam("numSillas")Integer numSillas)throws WebApplicationException
    {
        List<LugarEntity> lugaresEntities = null;
        try
        {
            lugaresEntities = logic.getLugaresByNumSillas(numSillas);
        }catch(BusinessLogicException bLE)
        {
            throw new WebApplicationException(bLE.getMessage());
        }
        if(lugaresEntities == null)
            throw new WebApplicationException("Ocurrió un problema inesperado.");
        return convertEntitiesToDTO(lugaresEntities);
        
    }
    
    private List<LugarDetailDTO> convertEntitiesToDTO(List<LugarEntity> lugaresEntities)
    {
        List<LugarDetailDTO> lugaresDTO = new ArrayList<>();
        for(LugarEntity entitieActual : lugaresEntities)
            lugaresDTO.add(new LugarDetailDTO(entitieActual));
        return lugaresDTO;
    }
}

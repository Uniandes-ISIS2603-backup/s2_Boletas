/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.dtos.SillaDTO;
import co.edu.uniandes.csw.boletas.ejb.LugarSillaLogic;
import co.edu.uniandes.csw.boletas.entities.SillaEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

/**
 *
 * @author estudiante
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("lugares/{lugarId: \\d+}/sillas")
public class LugarSillaResource {
    
    private final static Logger LOGGER = Logger.getLogger(LugarSillaResource.class.getName());
    
    @Inject
    private LugarSillaLogic logic;
    
    @POST
    @Path("{sillaId: \\d+}")
    public SillaDTO addSilla(@PathParam("lugarId") Long lugarId, @PathParam("sillaId") Long sillaId)
    {
        LOGGER.log(Level.INFO, "LugarSillaResource addBSilla: input: lugarId: {0} , sillaId: {1}", new Object[]{lugarId, sillaId});
        SillaDTO sillaAgregada = null;
        try
        {
            sillaAgregada = new SillaDTO( logic.addSilla(lugarId, sillaId) );
        }catch(Exception e)
        {
            throw new WebApplicationException(e.getMessage());
        }
        return sillaAgregada;
    }
    
    @GET
    public List<SillaDTO> getSillas(@PathParam("lugarId")Long lugarId)
    {
        //ist<SillaDTO> sillas = entityToDTO(logic.)
        List<SillaDTO> sillasDTO = null;
        try
        {
            List<SillaEntity> sillasEntity = logic.getSillas(lugarId) ;
            sillasDTO = entityToDTO(sillasEntity);
        }catch(Exception e)
        {
            throw new WebApplicationException(e.getMessage());
        }
        return sillasDTO;
    }
    
    private List<SillaDTO> entityToDTO(List<SillaEntity> sillasEntity)
    {
        List<SillaDTO> sillasDTO = new ArrayList<SillaDTO>();
        for(SillaEntity sillaActual: sillasEntity)
            sillasDTO.add(new SillaDTO(sillaActual));
        return sillasDTO;
    }
}

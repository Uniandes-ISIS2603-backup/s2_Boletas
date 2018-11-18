/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.dtos.SillaDTO;
import co.edu.uniandes.csw.boletas.ejb.LugarSillaLogic;
import co.edu.uniandes.csw.boletas.ejb.SillaLogic;
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
    private static final String recursoLu = "El Recurso /lugares/ ";
    private static final String recursoSi = "El Recurso /sillas/ ";
    private static final String existe = " /no existe";
    private static final Logger LOGGER = Logger.getLogger(LugarSillaResource.class.getName());
    
    @Inject
    private LugarSillaLogic lugarSillaLogic;
    
    @Inject 
    private SillaLogic sillaLogic;
    
    @POST
    @Path("{sillasId: \\d+}")
    public SillaDTO addSilla(@PathParam("lugarId") Long lugarId, @PathParam("sillasId") Long sillaId)
    {
        LOGGER.log(Level.INFO, "LugarSillaResource addBSilla: input: lugarId: {0} , sillaId: {1}", new Object[]{lugarId, sillaId});
        
        if(sillaLogic.getSillaById(sillaId) == null)
        {
            throw new WebApplicationException(recursoSi + sillaId + existe, 404);
        
        }
        
        return new SillaDTO(lugarSillaLogic.addSilla(lugarId,sillaId));
//        SillaDTO sillaAgregada = null;
//        try
//        {
//            sillaAgregada = new SillaDTO( lugarSillaLogic.addSilla(lugarId, sillaId) );
//        }catch(Exception e)
//        {
//            throw new WebApplicationException(e.getMessage());
//        }
//        return sillaAgregada;
        
    }
    
    @GET
    public List<SillaDTO> getSillas(@PathParam("lugarId")Long lugarId)
    {
        //ist<SillaDTO> sillas = entityToDTO(logic.)
        List<SillaDTO> sillasDTO = null;
        try
        {
            List<SillaEntity> sillasEntity = lugarSillaLogic.getSillas(lugarId) ;
            sillasDTO = entityToDTO(sillasEntity);
        }catch(Exception e)
        {
            throw new WebApplicationException(e.getMessage());
        }
        return sillasDTO;
    }
    
    private List<SillaDTO> entityToDTO(List<SillaEntity> sillasEntity)
    {
        List<SillaDTO> sillasDTO = new ArrayList<>();
        for(SillaEntity sillaActual: sillasEntity)
            sillasDTO.add(new SillaDTO(sillaActual));
        return sillasDTO;
    }
}

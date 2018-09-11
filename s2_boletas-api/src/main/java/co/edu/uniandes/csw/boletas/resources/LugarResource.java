/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.entities.LugarEntity;
import co.edu.uniandes.csw.boletas.dtos.LugarDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

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
    
    @POST
    public LugarDTO createLugar(LugarDTO lugarDTO)
    {
        LOGGER.info("LugarResource createLugar: input:" + lugarDTO.toString());
        LugarEntity lugarEntity = lugarDTO.toEntity();
        return lugarDTO;
    }
    
    @PUT
    @Path("(lugar_id : \\d+)")
    public LugarDTO updateLugar(@PathParam("lugar_id") Long lugar_id, LugarDTO lugarDTO)
    {
        return lugarDTO;
    }
    
    @GET
    @Path("(lugar_id : \\d+)")
    public LugarDTO getLugar(@PathParam("lugar_id") Long id)
    {
        return null;
    }
    
    @GET
    public List<LugarDTO> getLugares()
    {
        return new ArrayList<LugarDTO>();
    }
    
    @DELETE
    @Path("(lugar_id : \\d+)")
    public LugarDTO deleteLugar(@PathParam("lugar_id") Long id)
    {
        return null;
    }
}

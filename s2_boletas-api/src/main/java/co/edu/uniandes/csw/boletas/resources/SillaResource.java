/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.entities.SillaEntity;
import co.edu.uniandes.csw.boletas.dtos.SillaDTO;
import java.util.List;
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
 * @author Jhonatan Amórtegui
 */

@Path("sillas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped //A partir de ahí va a iniciar una transacción.
public class SillaResource {
    private static final Logger LOGGER = Logger.getLogger(SillaResource.class.getName());
    
    @POST
    public SillaDTO createSilla(SillaDTO sillaDTO)
    {
        LOGGER.info("SillaResource createSilla: input: " + SillaResource.class.getName());
        SillaEntity sillaEntity = sillaDTO.toEntity();
        return sillaDTO;
    }
    
    @PUT
    @Path("(silla_id : \\d+)")
    public SillaDTO updateSilla(@PathParam("silla_id") Long id, SillaDTO sillaDTO)
    {
        
        return sillaDTO;
    }
    
    @GET
    @Path("(silla_id: \\d+)")
    public SillaDTO getSilla(@PathParam("silla_id")Long id)
    {
        return null;
    }
    
    @GET
    public List<SillaDTO> getSillas()
    {
        return null;
    }
    
    @DELETE
    @Path("(silla_id: \\d+)")
    public SillaDTO deleteSilla(@PathParam("silla_id")Long id)
    {
        return null;
    }
}

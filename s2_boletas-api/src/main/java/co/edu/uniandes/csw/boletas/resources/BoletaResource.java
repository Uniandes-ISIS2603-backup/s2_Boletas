/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.dtos.BoletaDTO;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author estudiante
 */
@Path("boletas")
@Produces("application/json")
@Consumes("application/json")
public class BoletaResource {
    
    private static final Logger LOGGER = Logger.getLogger(BoletaResource.class.getName());
    @POST
    public BoletaDTO postBoleta(BoletaDTO boleta) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "BoletaResource postBoleta: input: {0}", boleta.toString());
        return boleta;
    }
    
    @GET
    @Path("{boletasId : \\d+}")
    public BoletaDTO getBoleta(@PathParam("boletasId") Long boletasId)
    {
        return null;
    }
    
    @DELETE
    @Path("{boletasId: \\d+}")
    public void deleteBoleta(@PathParam("boletasId") Long boletasId) 
    { 
    }
    
}

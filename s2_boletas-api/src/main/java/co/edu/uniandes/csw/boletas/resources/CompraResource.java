/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.dtos.CompraDTO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.*;

/**
 *
 * @author Gabriel hamilton
 */
@Path("compras")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CompraResource {
 
    
    private static final Logger LOGGER = Logger.getLogger(CompraResource.class.getName());
    
    /**
     * todavia no se implementa
     * @param compra
     * @return
     * @throws BusinessLogicException 
     */
    @POST
    public CompraDTO postCompra(CompraDTO compra) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CompraResource postCompra: input: {0}", compra.toString());
        
       return compra;   
    }
       
    /**
     * todavia no se implementa
     * @param compraid
     * @return 
     */
    @GET
    @Path("{compraid : \\d+}")
    public CompraDTO getCompra(@PathParam("compraid") Long compraid)
    {
        return null;
    }
    
    /**
     * todavia no se implementa
     * @param compraid
     * @param compra
     * @return 
     */
    @PUT
    @Path("{compraid : \\d+}")
    public CompraDTO putCompra(@PathParam("compraid") Long compraid, CompraDTO compra)
    {
        return compra;
    }
    
    /**
     * todavia no se implementa
     * @param compraid 
     */
    @DELETE
    @Path("{compraid : \\d+}")
    public void deleteCompra (@PathParam("compraid") Long compraid)
    {
        
    }
        
}
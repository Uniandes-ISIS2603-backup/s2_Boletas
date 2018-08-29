/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.dtos.CompraDTO;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.*;

/**
 *
 * @author Gabriel hamilton
 */
@Path("compra")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CompraResource {
 
    @POST
    public CompraDTO postCompra(CompraDTO compra)
    {
        return compra;   
    }
       
    @GET
    @Path("{compraid : \\d+}")
    public CompraDTO getCompra(@PathParam("compraid") Long compraid)
    {
        return null;
    }
    
    @PUT
    @Path("{compraid : \\d+}")
    public CompraDTO putCompra(@PathParam("compraid") Long compraid, CompraDTO compra)
    {
        return compra;
    }
    
    @DELETE
    @Path("{compraid : \\d+}")
    public void deleteCompra (@PathParam("compraid") Long compraid)
    {
        
    }
    
}
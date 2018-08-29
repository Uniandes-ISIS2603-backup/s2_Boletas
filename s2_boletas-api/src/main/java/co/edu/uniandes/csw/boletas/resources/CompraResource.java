/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

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
    
}

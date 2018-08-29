/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import co.edu.uniandes.csw.boletas.dtos.BoletaDTO;
import javax.ws.rs.PathParam;

/**
 *
 * @author estudiante
 */
@Path("boletas")
@Produces("aplication/json")
@Consumes("aplication/json")
@RequestScoped
public class BoletaResource {
    
    @POST
    public BoletaDTO postBoleta(BoletaDTO boleta){
    return boleta;
    }
    
    @GET
    @Path("{boletaid : \\d+}")
    public BoletaDTO getBoleta(@PathParam("boletaid") Long boletaid)
    {
        return null;
    }
    
}

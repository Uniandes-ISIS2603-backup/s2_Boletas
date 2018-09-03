/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.entities.OrganizadorEntity;
import co.edu.uniandes.csw.boletas.dtos.OrganizadorDTO;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;

/**
 *
 * @author estudiante
 */
@Path("organizadores")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class OrganizadorResourse 
{
    
    private static final Logger LOGGER = Logger.getLogger(ClienteResourse.class.getName());
    
    @POST
    public OrganizadorDTO createOrganizador(OrganizadorDTO organizador)
    { 
        
        LOGGER.info("OrganizadorResourse createOrganizador: input: " + organizador.toString());
        
        OrganizadorEntity entity = organizador.toEntity();
        
        
        
        return organizador;   
    }
    
    @PUT
    @Path("{organizadorId : \\d+}")
    public OrganizadorDTO updateOrganizador(@PathParam("organizadorId") Long organizadorId, OrganizadorDTO organizador)
    {
        return null;
    }
    
    @GET
    @Path("{organizadorId : \\d+}")
    public OrganizadorDTO getOrganizador(@PathParam("organizadorId") Long organizadorId)
    {
        return null;
    }
    
    @GET 
    public List<OrganizadorDTO> getOrganizador()
    {
        return null;
    }
    
    @DELETE
    @Path("{organizadorId: \\d+}")
    public void deleteOrganizador(@PathParam("organizadorId") Long organizadorId) 
    { 
    }
}
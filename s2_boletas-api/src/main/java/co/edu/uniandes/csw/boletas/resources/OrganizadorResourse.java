/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.entities.OrganizadorEntity;
import co.edu.uniandes.csw.boletas.dtos.OrganizadorDTO;
import co.edu.uniandes.csw.boletas.dtos.OrganizadorDetailDTO;
import co.edu.uniandes.csw.boletas.ejb.OrganizadorLogic;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Vilma Tirado Gomez
 */
@Path("organizadores")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class OrganizadorResourse 
{
    
    @Inject
    private OrganizadorLogic organizadorLogic;
    
    private static final Logger LOGGER = Logger.getLogger(OrganizadorResourse.class.getName());
    
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
                LOGGER.log(Level.INFO, "Proceso de actualizar un organizador: PUT");
        
        OrganizadorEntity entity = organizador.toEntity();
        if(organizadorLogic.getOrganizador(organizadorId) == null)
        {
            throw new WebApplicationException("El organizador que se quiere actualizar con id:" + organizadorId +" No existe");
        }    
         
        OrganizadorEntity actualizado = organizadorLogic.update( entity);
        
        OrganizadorDTO dto = new OrganizadorDTO(actualizado);
        
        return dto;
    }
    
    @GET
    @Path("{organizadorId : \\d+}")
    public OrganizadorDTO getOrganizador(@PathParam("organizadorId") Long organizadorId)
    {
        return null;
    }
    
    @GET 
    public List<OrganizadorDetailDTO> getOrganizadores()
    {
               List<OrganizadorDetailDTO> listaClientes = listEntity2DetailDTO(organizadorLogic.getOrganizadores());
        return listaClientes;
    }
    
    @DELETE
    @Path("{organizadorId: \\d+}")
    public void deleteOrganizador(@PathParam("organizadorId") Long organizadorId) 
    { 
       organizadorLogic.delete(organizadorId);
    }
    
            private List<OrganizadorDetailDTO> listEntity2DetailDTO(List<OrganizadorEntity> entityList) {
       List<OrganizadorDetailDTO> list = new ArrayList<>();
       for (OrganizadorEntity entity : entityList) {
           list.add(new OrganizadorDetailDTO(entity));
       }
       return list;
    }
}
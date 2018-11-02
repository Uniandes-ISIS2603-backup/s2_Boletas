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
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
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
    
    private static final Logger LOGGER = Logger.getLogger(OrganizadorResourse.class.getName());
    
    //Representacion de la clase OrganizadorLogic. Es una injeccion de dependencias
    
    @Inject
    OrganizadorLogic logica;
    
    @POST
    public OrganizadorDTO createOrganizador(OrganizadorDTO organizador) throws BusinessLogicException
    { 
        
        LOGGER.info("OrganizadorResourse createOrganizador: input: " + organizador.toString());
        
        //Lo primero que se hace es pasar el DTO  a entity ya que la logica solo conoce entities
        OrganizadorEntity entity=organizador.toEntity();
        //Despues se le pasa el entity a la logica la cual lo guarda en persistencia 
        //y persistencia le da un id 
        OrganizadorEntity nuevoOrganizador= logica.createOrganizador(entity);
        
        //Una vez creada la entity en la aplicacion esta se puede pasar nuevamente a DTO
        OrganizadorDTO dto= new OrganizadorDTO(nuevoOrganizador);
        
    
        return dto;   
    }
    
    @PUT
    @Path("{organizadorId : \\d+}")
    public OrganizadorDetailDTO updateOrganizador(@PathParam("organizadorId") Long organizadorId, OrganizadorDetailDTO organizador)
    {
        organizador.setId(organizadorId);
        
        if(logica.getOrganizador(organizadorId)== null)
        {
            throw new WebApplicationException("El recurso/organizadores/"+ organizadorId+" no existe");
        }
        OrganizadorDetailDTO updatedDTO= new OrganizadorDetailDTO(logica.update(organizador.toEntity()));
        return updatedDTO;
        
    }
    
    @GET
    @Path("{organizadorId : \\d+}")
    public OrganizadorDetailDTO getOrganizador(@PathParam("organizadorId") Long organizadorId) throws WebApplicationException
    {
        //Se busca el entity que se quiere modificar
        OrganizadorEntity entity = logica.getOrganizador(organizadorId);
        //Si no existe se manda excepcion
        if(entity==null)
        {
            throw new WebApplicationException("El recurso /organizadors/ "+ organizadorId+ "no existe",404);
        }
        //Si existe se modifica y se vuelve DTO 
        OrganizadorDetailDTO detailDTO= new OrganizadorDetailDTO(entity);
        
        return detailDTO;
                
        
    }
    
    @GET 
    public List<OrganizadorDetailDTO> getOrganizadors()
    {
         List<OrganizadorDetailDTO> listaOrganizadors = listEntity2DetailDTO(logica.getOrganizadores());
        return listaOrganizadors;
    }
    
    @DELETE
    @Path("{organizadorId: \\d+}")
    public void deleteOrganizador(@PathParam("organizadorId") Long organizadorId) 
    { 
        logica.delete(organizadorId);
    }
    
        private List<OrganizadorDetailDTO> listEntity2DetailDTO(List<OrganizadorEntity> entityList) {
       List<OrganizadorDetailDTO> list = new ArrayList<>();
       for (OrganizadorEntity entity : entityList) {
           list.add(new OrganizadorDetailDTO(entity));
       }
       return list;
    }
}
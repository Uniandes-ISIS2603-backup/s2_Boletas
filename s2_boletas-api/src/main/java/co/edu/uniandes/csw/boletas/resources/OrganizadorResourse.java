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
    private static final String RECURSO = "El Recurso /organizadores/ ";
    private static final String EXISTE = " no existe";
    
    private static final Logger LOGGER = Logger.getLogger(OrganizadorResourse.class.getName());
    
    //Representacion de la clase OrganizadorLogic. Es una injeccion de dependencias
    
    @Inject
    OrganizadorLogic logica;
    
    @POST
    public OrganizadorDTO createOrganizador(OrganizadorDTO organizador) throws BusinessLogicException
    { 
        
        LOGGER.log(Level.INFO,"OrganizadorResourse createOrganizador: input: {0}" , organizador);
        
        //Lo primero que se hace es pasar el DTO  a entity ya que la logica solo conoce entities
        OrganizadorEntity entity=organizador.toEntity();
        //Despues se le pasa el entity a la logica la cual lo guarda en persistencia 
        //y persistencia le da un id 
        OrganizadorEntity nuevoOrganizador= logica.createOrganizador(entity);
        
        //Una vez creada la entity en la aplicacion esta se puede pasar nuevamente a DTO
        return new OrganizadorDTO(nuevoOrganizador);
        
      
    }
    
    @PUT
    @Path("{organizadorId : \\d+}")
    public OrganizadorDetailDTO updateOrganizador(@PathParam("organizadorId") Long organizadorId, OrganizadorDetailDTO organizador)
    {
        organizador.setId(organizadorId);
        
        if(logica.getOrganizador(organizadorId)== null)
        {
            throw new WebApplicationException(RECURSO+ organizadorId+EXISTE, 404);
        }
        return new OrganizadorDetailDTO(logica.update(organizador.toEntity()));
       
        
    }
    
    @GET
    @Path("{organizadorId : \\d+}")
    public OrganizadorDetailDTO getOrganizador(@PathParam("organizadorId") Long organizadorId)
    {
        //Se busca el entity que se quiere modificar
        OrganizadorEntity entity = logica.getOrganizador(organizadorId);
        //Si no existe se manda excepcion
        if(entity==null)
        {
            throw new WebApplicationException(RECURSO+ organizadorId+ EXISTE,404);
        }
        //Si existe se modifica y se vuelve DTO 
        return new OrganizadorDetailDTO(entity);
        
                
        
    }
    
    @GET 
    public List<OrganizadorDetailDTO> getOrganizadores()
    {
         return listEntity2DetailDTO(logica.getOrganizadores());
       
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

    /* * Este método conecta la ruta de /organizadores con las rutas de /espectaculo que
     * dependen del organizador, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los espectaculos  de un organizador.
     *
     * @param organizadorId El ID de la organizador con respecto a la cual se
     * accede al servicio.
     * @return El servicio de espectaculos para este organizador en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @Path("{organizadorId: \\d+}/espectaculos")
    public Class<OrganizadorEspectaculoResourse> getOrganizadoresEspectaculoResourse(@PathParam("organizadorId") Long organizadorId) {
        if (logica.getOrganizador(organizadorId) == null) {
            throw new WebApplicationException(RECURSO + organizadorId + EXISTE, 404);
        }
        return OrganizadorEspectaculoResourse.class;
    }
}
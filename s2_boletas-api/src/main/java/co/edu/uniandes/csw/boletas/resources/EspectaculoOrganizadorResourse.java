/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.dtos.EspectaculoDetailDTO;
import co.edu.uniandes.csw.boletas.dtos.OrganizadorDTO;
import co.edu.uniandes.csw.boletas.dtos.OrganizadorDetailDTO;
import co.edu.uniandes.csw.boletas.ejb.OrganizadorEspectaculoLogic;
import co.edu.uniandes.csw.boletas.ejb.OrganizadorLogic;
import co.edu.uniandes.csw.boletas.entities.OrganizadorEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 * Clase que define el recurso de /espectaculos/{id}/organizadores
 * @author Sebastian Rodriguez Beltran
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EspectaculoOrganizadorResourse {
    
    private static final Logger LOGGER = Logger.getLogger(EspectaculoOrganizadorResourse.class.getName());
    
    @Inject
    private OrganizadorEspectaculoLogic organizadorEspectaculoLogic;
    
    @Inject 
    private OrganizadorLogic organizadorLogic;
    
    /**
     * Metodo de post, que asigna a un espectaculo un organizador
     * @param espectaculosId
     * @param organizadoresId
     * @return 
     */
    @POST
    @Path("{organizadoresId: \\d+}")
    public OrganizadorDetailDTO addEspectaculo(@PathParam("espectaculosId") Long espectaculosId, @PathParam("organizadoresId") Long organizadoresId)
    {
        LOGGER.log(Level.INFO, "EspectaculoOrganizadorResourse addOrganizador: input: organizadorId: "+ organizadoresId +", espectaculosId: " + espectaculosId, new Object[]{organizadoresId, espectaculosId});
        if (organizadorLogic.getOrganizador(organizadoresId) == null) {
            throw new WebApplicationException("El recurso /espectaculos" + espectaculosId + " no existe.", 404);
        }
        OrganizadorDetailDTO organizadorDTO = new OrganizadorDetailDTO(organizadorEspectaculoLogic.addOrganizador(espectaculosId, organizadoresId));
        
        LOGGER.log(Level.INFO, "EspectaculoOrganizadorResourse addOrganizador: output: {0}", organizadorDTO.toString());
        return organizadorDTO;
    }
    
    
    /**
     * Metodo que obtiene el organizador de un espectaculo
     * @param espectaculosId
     * @param organizadoresId
     * @return 
     */
    @GET
    @Path("{espectaculosId:\\+d}")
    public OrganizadorDetailDTO getOrganizador(@PathParam("espectaculosid") Long espectaculosId, @PathParam("organizadoresId") Long organizadoresId)
    {
        if (organizadorLogic.getOrganizador(organizadoresId)==null)
            throw new WebApplicationException("El recurso espectaculos/"+ espectaculosId+ "/organizadores/ "+ organizadoresId + "No existe",404);
        OrganizadorDetailDTO organizador= new OrganizadorDetailDTO(organizadorEspectaculoLogic.getOrganizador(espectaculosId, organizadoresId));
        
        return organizador;
    }
    
    
    /**
     * Metodo que cambia el organizador  de un espectaculo
     * @param espectaculoId, el id del espectaculo a modificar
     * @param organizador, un objeto OrganizadorDetailDTO
     * @return Un espectaculo Detail DTO con su atributo cambiado
     */
    @PUT 
    public EspectaculoDetailDTO replaceOrganizador(Long espectaculoId, OrganizadorDetailDTO organizador)
    {
        LOGGER.log(Level.INFO, "EspectaculoOrganizadorResourse replaceOrganizador");
        if(organizadorLogic.getOrganizador(organizador.getId() )==null)
        {
            throw new WebApplicationException("El organizador con id " + organizador.getId() + " no existe");
        }
        
        EspectaculoDetailDTO actualizado = new EspectaculoDetailDTO(organizadorEspectaculoLogic.replaceOrganizador(espectaculoId, organizador.getId()));
        
        return actualizado;
    }
    
    
    
    
}

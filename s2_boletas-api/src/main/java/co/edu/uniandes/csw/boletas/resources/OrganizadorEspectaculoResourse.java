/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.dtos.ComentarioDTO;
import co.edu.uniandes.csw.boletas.dtos.EspectaculoDTO;
import co.edu.uniandes.csw.boletas.ejb.EspectaculoLogic;
import co.edu.uniandes.csw.boletas.ejb.OrganizadorLogic;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 * Clase que implementa el recurso /organizadores/{id}/espectaculos
 * @author Vilma Tirado y Sebastian Rodriguez 
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrganizadorEspectaculoResourse 
{
    private static final Logger LOGGER = Logger.getLogger(OrganizadorEspectaculoResourse.class.getName());

//    @Inject
//    private OrganizadorEspectaculoLogic espectaculoComentarioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private EspectaculoLogic espectaculoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    @Inject
    private OrganizadorLogic comentarioLogic;
    
    
    
    @POST
    @Path("{espectaculosId: \\d+}")
    public EspectaculoDTO addEspectaculo(@PathParam("organizadorId") Long organizadoresId, @PathParam("espectaculoId") Long espectaculosId)
    {
        LOGGER.log(Level.INFO, "OrganizadorEspectaculoResourse addEspectaculo: input: organizadorId: "+ organizadoresId +", espectaculosId: " + espectaculosId, new Object[]{organizadoresId, espectaculosId});
        if (comentarioLogic == null) {
            throw new WebApplicationException("El recurso /espectaculos" + espectaculosId + " no existe.", 404);
        }
//        ComentarioDTO comentarioDTO = new ComentarioDTO(espectaculoComentarioLogic.addComentario(comentariosId, espectaculosId));
//        LOGGER.log(Level.INFO, "EspectaculoComentarioResourse agregarComentario: output: {0}", comentarioDTO.toString());
        return new EspectaculoDTO();
    }
    
    
    
}

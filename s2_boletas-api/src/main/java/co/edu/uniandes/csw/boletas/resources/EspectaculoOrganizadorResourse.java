/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.dtos.EspectaculoDetailDTO;
import co.edu.uniandes.csw.boletas.dtos.OrganizadorDTO;
import co.edu.uniandes.csw.boletas.ejb.EspectaculoLogic;
import co.edu.uniandes.csw.boletas.ejb.EspectaculoOrganizadorLogic;
import co.edu.uniandes.csw.boletas.ejb.OrganizadorLogic;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
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
@Path("espectaculos/{espectaculoId: \\d+}/organizadores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EspectaculoOrganizadorResourse {
    private static final String RECURSOES = "El Recurso /espectaculos/ ";
    private static final String RECURSOOR = "El Recurso /organizadores/ ";
    private static final String EXISTE = " no existe";
    private static final Logger LOGGER = Logger.getLogger(EspectaculoOrganizadorResourse.class.getName());
    
    @Inject
    private EspectaculoOrganizadorLogic espectaculoOrganizadorLogic;
    
    @Inject 
    private OrganizadorLogic organizadorLogic;
    
    @Inject 
    private EspectaculoLogic espectaculoLogic;

    @PUT
  public EspectaculoDetailDTO replaceOrganizador(@PathParam("espectaculosId") Long espectaculosId, OrganizadorDTO organizador) {
        LOGGER.log(Level.INFO, "EspectaculoOrganizadorResource replaceOrganizador: input: espectaculosId{0} , Organizador:{1}", new Object[]{espectaculosId, organizador});
        if (espectaculoLogic.getEspectaculo(espectaculosId) == null) {
            throw new WebApplicationException(RECURSOES + espectaculosId + EXISTE, 404);
        }
        if (organizadorLogic.getOrganizador(organizador.getId()) == null) {
            throw new WebApplicationException(RECURSOOR+ organizador.getId() + EXISTE, 404);
        }
        EspectaculoDetailDTO espectaculoDetailDTO = new EspectaculoDetailDTO(espectaculoOrganizadorLogic.replaceOrganizador(espectaculosId, organizador.getId()));
        LOGGER.log(Level.INFO, "EspectaculoOrganizadorResource replaceOrganizador: output: {0}", espectaculoDetailDTO);
        return espectaculoDetailDTO;
    }
}



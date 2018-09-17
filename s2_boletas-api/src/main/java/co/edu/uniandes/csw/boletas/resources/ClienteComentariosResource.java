/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.ejb.BoletaLogic;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Clase que implementa el recurso "cliente/{id}/comentarios"
 * @author Juan Diego Camacho
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteComentariosResource {
    
    private static final Logger LOGGER = Logger.getLogger(ClienteComentariosResource.class.getName());
    
    @Inject
    private BoletaLogic boletaLogic;
    
    
}

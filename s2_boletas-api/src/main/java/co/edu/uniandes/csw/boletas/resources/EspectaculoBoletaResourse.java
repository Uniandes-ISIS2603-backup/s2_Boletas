/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.dtos.BoletaDTO;
import co.edu.uniandes.csw.boletas.ejb.BoletaLogic;
import co.edu.uniandes.csw.boletas.ejb.EspectaculoBoletasLogic;
import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 * Clase que define el recurso espectaculos/{id}/boletas
 * 
 * 
 * @author Sebastian Rodriguez Beltran
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EspectaculoBoletaResourse 
{
    
    private static final Logger LOGGER = Logger.getLogger(EspectaculoBoletaResourse.class.getName());
    
    @Inject 
    private EspectaculoBoletasLogic espectaculoBoletaLogic;
    
    @Inject 
    private BoletaLogic boletasLogic;
    
    
    
    /**
     * Metodo que define el agregar una boleta a un espectaculo
     * @param espectaculosId
     * @param boletasId
     * @return 
     */
    @POST
    @Path("{boletasId: \\d+ }")
    private BoletaDTO addBoleta(@PathParam("espectaculoId") Long espectaculosId, @PathParam("boletaId") Long boletasId)
    {
        LOGGER.log(Level.INFO, "Se inicia el proceso de añadir una nueva boleta");
        
        if(boletasLogic.getBoleta(boletasId) == null)
        {
            throw new WebApplicationException("No se encontro la boleta con id" + boletasId);
        }
        
        BoletaEntity boleta = espectaculoBoletaLogic.addBoleta(boletasId, espectaculosId);
        
        BoletaDTO bole = new BoletaDTO(boleta);
        
        LOGGER.log(Level.INFO, "Se termino el proceso de añadir una boleta a un espectaculo satisfactoriamente");
        
        return bole;
    }
    
    
    @GET 
    @Path("{boletasId: \\d+}")
    private BoletaDTO getBoleta(@PathParam("espectaculosId") Long espectaculosId, @PathParam("boletasId") Long boletasId)
    {
        return null;
    }
    
}

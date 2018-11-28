/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.dtos.EspectaculoDetailDTO;
import co.edu.uniandes.csw.boletas.dtos.LugarDetailDTO;
import co.edu.uniandes.csw.boletas.ejb.LugarEspectaculoLogic;
import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import co.edu.uniandes.csw.boletas.entities.LugarEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author estudiante
 */
@Path("lugares/{lugarId: \\d+}/espectaculos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class LugarEspectaculoResource {
    private static final String RECURSODE = "El Recurso /lugar/ ";
    private static final String RECURSOCO = "El Recurso /espectaculo/ ";
    private static final String EXISTE = " no existe";
    
    private static final Logger LOGGER = Logger.getLogger(LugarEspectaculoResource.class.getName());
    
    @Inject
    private LugarEspectaculoLogic lELogic;
    
    /**
     * 
     * @param lugarId
     * @param espectaculoId
     * @return 
     */
    @POST
    @Path("{espectaculoId: \\d+}")
    public LugarDetailDTO agregarEspectaculo(@PathParam("lugarId") Long lugarId, @PathParam("espectaculoId")Long espectaculoId)
    {
       LugarEntity lugar = null;
       try
       {
            lugar = lELogic.addEspectaculo(lugarId, espectaculoId);
       }catch(Exception e)
       {
           throw new WebApplicationException(e.getMessage());
       }
       if(lugar == null)
           throw new WebApplicationException("El lugar retornado es null.");
       return new LugarDetailDTO(lugar);
    }
    
    /**
     * Método que retorna los espectaculos pertenecientes a un lugar.
     * @param lugarId
     * @return 
     */
    @GET
    public List<EspectaculoDetailDTO> getEspectaculosByLugar(@PathParam("lugarId") Long lugarId)
    {
        List<EspectaculoEntity> espectaculos = new ArrayList<EspectaculoEntity>();
        try
        {
            espectaculos = lELogic.getEspectaculosByLugar(lugarId);
        }catch(Exception e)
        {
            throw new WebApplicationException(e.getMessage());
        }
        
        List<EspectaculoDetailDTO> espectaculosDTO = listEntity2DetailDTO(espectaculos);
        return espectaculosDTO;
        
    }
    
    private List<EspectaculoDetailDTO> listEntity2DetailDTO(List<EspectaculoEntity> entityList) {
       List<EspectaculoDetailDTO> list = new ArrayList<>();
       for (EspectaculoEntity entity : entityList) {
           list.add(new EspectaculoDetailDTO(entity));
       }
       return list;
    }
    
}

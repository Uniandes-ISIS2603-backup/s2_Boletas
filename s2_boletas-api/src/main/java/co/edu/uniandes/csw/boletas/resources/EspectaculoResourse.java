/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import co.edu.uniandes.csw.boletas.dtos.EspectaculoDTO;
import co.edu.uniandes.csw.boletas.dtos.EspectaculoDetailDTO;
import co.edu.uniandes.csw.boletas.ejb.EspectaculoLogic;
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

/**
 *
<<<<<<< HEAD
 * @author Sebastian Rodriguez
=======
 * @author Sebastian Rodriguez Beltran
>>>>>>> master
 */
@Path("espectaculos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EspectaculoResourse 
{
    
    private static final Logger LOGGER = Logger.getLogger(EspectaculoResourse.class.getName());
    
    @Inject
    EspectaculoLogic espectaculoLogic;
    
    @POST
    public EspectaculoDTO createEspectaculo(EspectaculoDTO espectaculo) throws BusinessLogicException
    { 
        
        LOGGER.info("EspectaculoResourse createEspectaculo: input: " + espectaculo.toString());
        
        EspectaculoEntity entity = espectaculo.toEntity();
        
        EspectaculoEntity nuevo = espectaculoLogic.createEntity(entity);
        
        EspectaculoDTO dto = new EspectaculoDTO(nuevo);
        
        LOGGER.log(Level.INFO, dto.toString());
        
        return dto;   
    }
    
    @PUT
    @Path("{espectaculoId : \\d+}")
    public EspectaculoDTO uptadeEspectaculo(@PathParam("espectaculoId") Long espectaculoId, EspectaculoDTO espec)
    {
        return espec;
    }
    
    @GET
    @Path("{espectaculoId : \\d+}")
    public EspectaculoDTO getEspectaculo(@PathParam("espectaculoId") Long especatculoId)
    {
        return new EspectaculoDTO();
    }
    
    @GET 
    public List<EspectaculoDetailDTO> getEspectaculos()
    {
        LOGGER.info("EspectaculoResourse getEditorials: input: void");
        List<EspectaculoDetailDTO> listaEditoriales = listEntity2DetailDTO(espectaculoLogic.getEspectaculos());
        LOGGER.log(Level.INFO, "EditorialResource getEditorials: output: {0}", listaEditoriales.toString());
        return listaEditoriales;
    }
    
    @DELETE
    @Path("{espectaculoId: \\d+}")
    public void deleteEditorial(@PathParam("espectaculoId") Long editorialsId) 
    {
        
    }
    
     private List<EspectaculoDetailDTO> listEntity2DetailDTO(List<EspectaculoEntity> entityList) {
        List<EspectaculoDetailDTO> list = new ArrayList<>();
        for (EspectaculoEntity entity : entityList) {
            list.add(new EspectaculoDetailDTO(entity));
        }
        return list;
    }
}
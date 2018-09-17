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
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Sebastian Rodriguez Beltran
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
    
    
    /**
     * Metodo Post para agregar un nuevo objeto de EspectaculoDTO
     * @param espectaculo El espectaculo a añadir, fue pasado como un json
     * @return El espectaculo añadido
     * @throws BusinessLogicException 
     */
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
    
    
    /**
     * Metodo PUT para actualizar un espectaculo, dado su id y los valores en el espectaculo DTO
     * @param espectaculoId El id del espectaculo a actualizar
     * @param espec Objeto de tipo EspectaculoDTO que llega como objeto JSON
     * @return El objeto EspectaculoDTO actualizado
     */
    @PUT
    @Path("{espectaculoId : \\d+}")
    public EspectaculoDTO uptadeEspectaculo(@PathParam("espectaculoId") Long espectaculoId, EspectaculoDTO espec)
    {
        LOGGER.log(Level.INFO, "Proceso de actualizar un espectaculo: PUT");
        
        EspectaculoEntity entity = espec.toEntity();
        if(espectaculoLogic.getEspectaculo(espectaculoId) == null)
        {
            throw new WebApplicationException("El espectaculo que se quiere actualizar con id:" + espectaculoId +" No existe");
        }    
         
        EspectaculoEntity actualizado = espectaculoLogic.updateEspectaculo(espectaculoId, entity);
        
        EspectaculoDTO dto = new EspectaculoDTO(actualizado);
        
        return dto;
    }
    
    
    /**
     * Metodo que busca un espectaculo dado un ID
     * @param espectaculoId El id del espectaculo a buscar
     * @return El objeto EspectaculoDTO que se encuentra en el sistema, en caso de no encontrarlo bota excepcion
     */
    @GET
    @Path("{espectaculoId : \\d+}")
    public EspectaculoDTO getEspectaculo(@PathParam("espectaculoId") Long espectaculoId)
    {
        
        LOGGER.log(Level.INFO, "EspectaculoResourse get Espectaculo");
        EspectaculoEntity entity = espectaculoLogic.getEspectaculo(espectaculoId);
        
        
        if(entity == null)
        {
            throw new WebApplicationException("El espectaculo buscado con id:" + espectaculoId + " no existe" );
        }
        
        EspectaculoDTO dto = new EspectaculoDTO(entity);
        
        LOGGER.log(Level.INFO, "EspectaculoResourse getEspectaculo" + dto.toString());
        
        return dto;
    }
    
    @GET 
    public List<EspectaculoDetailDTO> getEspectaculos()
    {
        LOGGER.info("EspectaculoResourse getEspectaculos: input: void");
        List<EspectaculoDetailDTO> listaEspectaculos = listEntity2DetailDTO(espectaculoLogic.getEspectaculos());
        LOGGER.log(Level.INFO, "EspectaculoResourse getEspectaculos: output: {0}", listaEspectaculos.toString());
        return listaEspectaculos;
    }
    
    @DELETE
    @Path("{espectaculoId: \\d+}")
    public void deleteEspectaculo(@PathParam("espectaculoId") Long espectaculoId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "EspectaculoResourse deleteEspectaculo: output: ");
        
       espectaculoLogic.deleteEspectaculo(espectaculoId);
       
       LOGGER.log(Level.INFO, "Espectaculo con id :" + espectaculoId+"ha sido elimiado");
    }
    
    private List<EspectaculoDetailDTO> listEntity2DetailDTO(List<EspectaculoEntity> entityList) {
       List<EspectaculoDetailDTO> list = new ArrayList<>();
       for (EspectaculoEntity entity : entityList) {
           list.add(new EspectaculoDetailDTO(entity));
       }
       return list;
    }
}
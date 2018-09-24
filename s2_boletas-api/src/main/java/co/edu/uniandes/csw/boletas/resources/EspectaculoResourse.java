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
        
        LOGGER.log(Level.INFO, "EspectaculoResourse createEspectaculo: input: {0}", espectaculo.toString());
        
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
     * @throws co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException
     */
    @PUT
    @Path("{espectaculosId : \\d+}")
    public EspectaculoDetailDTO uptadeEspectaculo(@PathParam("espectaculosId") Long espectaculoId, EspectaculoDetailDTO espec) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Proceso de actualizar un espectaculo: PUT");
        
        espec.setId(espectaculoId);
        EspectaculoEntity entity = espec.toEntity();
        if(espectaculoLogic.getEspectaculo(espectaculoId) == null)
        {
            throw new WebApplicationException("El espectaculo que se quiere actualizar con id:" + espectaculoId +" No existe",404);
        }
        
        EspectaculoEntity actualizado;
        try 
        {
            actualizado = espectaculoLogic.updateEspectaculo(espectaculoId, entity);
        }
        catch(BusinessLogicException e)
        {
            throw new WebApplicationException(e.getMessage(),412);
        }
        
        EspectaculoDetailDTO dto = new EspectaculoDetailDTO(actualizado);
        
        return dto;
    }
    
    
    /**
     * Metodo que busca un espectaculo dado un ID
     * @param espectaculoId El id del espectaculo a buscar
     * @return El objeto EspectaculoDTO que se encuentra en el sistema, en caso de no encontrarlo bota excepcion
     */
    @GET
    @Path("{espectaculosId : \\d+}")
    public EspectaculoDetailDTO getEspectaculo(@PathParam("espectaculosId") Long espectaculoId)
    {
        
        LOGGER.log(Level.INFO, "EspectaculoResourse getEspectaculo");
        EspectaculoEntity entity = espectaculoLogic.getEspectaculo(espectaculoId);
        
        if(entity == null)
        {
            throw new WebApplicationException("El espectaculo buscado con id:" + espectaculoId + " no existe",404 );
        }
        
        EspectaculoDetailDTO dto = new EspectaculoDetailDTO(entity);
        
        LOGGER.log(Level.INFO, "EspectaculoResourse getEspectaculo{0}", dto.toString());
        
        return dto;
    }
    
    
    /**
     * Metodo que retorna una lista de espectaculos
     * Esta anotado con el get, devulve un JSON con todos los objetos de 
     * EspectaculoDetailDTO
     * @return 
     */
    @GET 
    public List<EspectaculoDetailDTO> getEspectaculos()
    {
        LOGGER.info("EspectaculoResourse getEspectaculos: input: void");
        List<EspectaculoDetailDTO> listaEspectaculos = listEntity2DetailDTO(espectaculoLogic.getEspectaculos());
        LOGGER.log(Level.INFO, "EspectaculoResourse getEspectaculos: output: {0}", listaEspectaculos.toString());
        return listaEspectaculos;
    }
    
    
    /**
     * Metodo para eliminar un espectaculo del sistema
     * Esta anotado con @DELETE y define la operacion de eliminar sobre
     * /espectaculos/{id}
     * @param espectaculoId El id del espectaculo que se quiere eliminar
     * @throws BusinessLogicException En caso de que el espectaculo a eliminar
     * no exista
     */
    @DELETE
    @Path("{espectaculosId: \\d+}")
    public void deleteEspectaculo(@PathParam("espectaculosId") Long espectaculoId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "EspectaculoResourse deleteEspectaculo: output: ");
        
        if(espectaculoLogic.getEspectaculo(espectaculoId) == null)
        {
            throw new WebApplicationException("El recurso /espectaculos/" + espectaculoId + " no existe.", 404);
        }
        
       espectaculoLogic.deleteEspectaculo(espectaculoId);
       
       LOGGER.log(Level.INFO, "Espectaculo con id :{0}ha sido elimiado", espectaculoId);
    }
    
    private List<EspectaculoDetailDTO> listEntity2DetailDTO(List<EspectaculoEntity> entityList) {
       List<EspectaculoDetailDTO> list = new ArrayList<>();
       for (EspectaculoEntity entity : entityList) {
           list.add(new EspectaculoDetailDTO(entity));
       }
       return list;
    }
    
    
    /**
     * Cuando se quiera acceder de espectaculos a sus comentarios, este metodo 
     * va a retornar la clase de EspectaculoComentarioResourse
     * que va a tener sus propios metodos para devolver lo solicitado
     * @param espectaculosId El recurso sobre el cual se quieren hacer las 
     * consultas o operaciones
     * @return La clase de EspectaculoComentarioResourse
     */
    @Path("{espectaculosId: \\d+}/comentarios")
    public Class<EspectaculoComentarioResourse> getEditorialBooksResource(@PathParam("espectaculosId") Long espectaculosId) {
        if (espectaculoLogic.getEspectaculo(espectaculosId)== null) {
            throw new WebApplicationException("El recurso /espectaculos/" + espectaculosId + " no existe.", 404);
        }
        return EspectaculoComentarioResourse.class;
    }
}
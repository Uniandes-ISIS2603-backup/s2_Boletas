/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.dtos.DevolucionDTO;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.ejb.DevolucionLogic;
import co.edu.uniandes.csw.boletas.entities.DevolucionEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Gabriel Hamilton
 */
@Path("devoluciones")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class DevolucionResource {
    
     private static final Logger LOGGER = Logger.getLogger(DevolucionResource.class.getName());
    
    private static final String recurso = "El Recurso /devoluciones/ ";
    private static final String existe = " /no existe";
    
    @Inject
    private DevolucionLogic devolucionLogic; 

    
    /**
     * Crea una nueva devolucion con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param devolucion {@link DevolucionDetailDTO} - La devolucion que se desea
     * guardar.
     * @return JSON {@link DevolucionDetailDTO} - La devolucion guardada con el atributo
     * id autogenerado.
     */
    @POST
    public DevolucionDTO postDevolucion(DevolucionDTO devolucion) 
    {
       LOGGER.log(Level.INFO, "DevolucionResource postDevolucion: input: {0}", devolucion);
       DevolucionEntity devolucionEntity = devolucion.toEntity();
       DevolucionEntity nuevaDevolucionEntity = devolucionLogic.createDevolucion(devolucionEntity);
       DevolucionDTO nuevaDevolucionDTO = new DevolucionDTO(nuevaDevolucionEntity);
       LOGGER.log(Level.INFO, "DevolucionResource postDevolucion: output: {0}", nuevaDevolucionDTO);
       return nuevaDevolucionDTO;   
    }
    
    
    /**
     * Busca y devuelve todas las devoluciones que existen en la aplicacion.
     *
     * @return JSONArray {@link DevolucionDetailDTO} - Las devoluciones
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<DevolucionDTO> getDevoluciones() {
        LOGGER.info("DevolucionResource getDevoluciones: input: void");
        List<DevolucionDTO> listaDevoluciones = listEntity2DetailDTO(devolucionLogic.getDevoluciones());
        LOGGER.log(Level.INFO, "DevolucionResource getDevoluciones: output: {0}", listaDevoluciones);
        return listaDevoluciones;
    }
    
       
    /**
     * Busca la devolucion con el id asociado recibido en la URL y la devuelve.
     *
     * @param devolucionId Identificador de la devolucion que se esta buscando.
     * @return JSON {@link DevolucionDetailDTO} - La devolucion buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la devolucion.
     */
    @GET
    @Path("{devolucionesId : \\d+}")
    public DevolucionDTO getDevolucion(@PathParam("devolucionesId") Long devolucionId)
    {
        LOGGER.log(Level.INFO, "DevolucionResource getDevolucion: input: {0}", devolucionId);
        DevolucionEntity devolucionEntity = devolucionLogic.getDevolucion(devolucionId);
        if (devolucionEntity == null) {
            throw new WebApplicationException(recurso + devolucionId + existe, 404);
        }
        DevolucionDTO detailDTO = new DevolucionDTO(devolucionEntity);
        LOGGER.log(Level.INFO, "DevolucionResource getDevolucion: output: {0}", detailDTO);
        return detailDTO;
    }
    
    /**
     * Actualiza la devolucion con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param devolucionId Identificador de la devolucion que se desea
     * actualizar.
     * @param devolucion {@link DevolucionDetailDTO} La devolucion que se desea
     * guardar.
     * @return JSON {@link DevolucionDetailDTO} - La devolucion guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la devolucion a
     * actualizar.
     */
    @PUT
    @Path("{devolucionesId : \\d+}")
    public DevolucionDTO putDevolucion(@PathParam("devolucionesId") Long devolucionId, DevolucionDTO devolucion)
    {
        LOGGER.log(Level.INFO, "DevolucionResource putDevolucion: input: id:{0} , devolucion: {1}", new Object[]{devolucionId, devolucion});
        devolucion.setId(devolucionId);
        if (devolucionLogic.getDevolucion(devolucionId) == null) {
            throw new WebApplicationException(recurso + devolucionId + existe, 404);
        }
        DevolucionDTO detailDTO = new DevolucionDTO(devolucionLogic.updateDevolucion(devolucionId, devolucion.toEntity()));
        LOGGER.log(Level.INFO, "DevolucionResource putDevolucion: output: {0}", detailDTO);
        return detailDTO;
    }
    
    /**
     * Borra la devolucion con el id asociado recibido en la URL
     *
     * @param devolucionId Identificador de la devolucion que se desea borrar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la devolucion.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la devolucion.
     */
    @DELETE
    @Path("{devolucionId : \\d+}")
    public void deleteDevolucion (@PathParam("devolucionId") Long devolucionId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "DevolucionResource deleteDevolucion: input: {0}", devolucionId);
        if (devolucionLogic.getDevolucion(devolucionId) == null) {
            throw new WebApplicationException(recurso + devolucionId + existe, 404);
        }
        devolucionLogic.deleteDevolucion(devolucionId);
        LOGGER.info("DevolucionResource deleteDevolucion: output: void");
    }
       
      
    /**
     * Conexión con el servicio de compra para una devolucion.
     * {@link DevolucionCompraResource}
     *
     * Este método conecta la ruta de /devoluciones con la ruta de /compras que
     * depende de la devolucion, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de la compra de una devolucion.
     *
     * @param devolucionId El ID de la devolucion con respecto a la cual se
     * accede al servicio.
     * @return El servicio de compra para esta devolucion en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la devolucion.
     */
    @Path("{devolucionId: \\d+}/clientes")
    public Class<DevolucionCompraResource> getDevolucionCompraResource(@PathParam("devolucionId") Long devolucionId) {
        if (devolucionLogic.getDevolucion(devolucionId) == null) {
            throw new WebApplicationException(recurso + devolucionId + existe, 404);
        }
        return DevolucionCompraResource.class;
    }
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos DevolucionEntity a una lista de
     * objetos DevolucionDetailDTO (json)
     *
     * @param entityList corresponde a la lista de compras de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de devoluciones en forma DTO (json)
     */
    private List<DevolucionDTO> listEntity2DetailDTO(List<DevolucionEntity> entityList) {
        List<DevolucionDTO> list = new ArrayList<>();
        for (DevolucionEntity entity : entityList) {
            list.add(new DevolucionDTO(entity));
        }
        return list;
    }
}

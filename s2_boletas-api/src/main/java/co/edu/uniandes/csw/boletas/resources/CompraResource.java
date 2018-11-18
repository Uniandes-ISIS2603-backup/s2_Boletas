/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.dtos.CompraDetailDTO;
import co.edu.uniandes.csw.boletas.ejb.CompraLogic;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.*;

/**
 *
 * @author Gabriel hamilton
 */
@Path("compras")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CompraResource {
 
    
    private static final Logger LOGGER = Logger.getLogger(CompraResource.class.getName());
    
    @Inject
    private CompraLogic compraLogic; 

    private static final String recursoCompras = "El Recurso /compras/ ";
    private static final String existe = " /no existe";
    
    /**
     * Crea una nueva compra con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param compra {@link CompraDetailDTO} - La compra que se desea
     * guardar.
     * @return JSON {@link CompraDetailDTO} - La compra guardada con el atributo
     * id autogenerado.
     */
    @POST
    public CompraDetailDTO postCompra(CompraDetailDTO compra) throws BusinessLogicException
    {
       LOGGER.log(Level.INFO, "CompraResource postCompra: input: {0}", compra);
       LOGGER.log(Level.INFO, "CompraResource postCompra: costoTotal: {0}", compra.getCostoTotal());
       CompraEntity compraEntity = compra.toEntity();
       CompraEntity nuevaCompraEntity = compraLogic.createCompra(compraEntity);
       CompraDetailDTO nuevaCompraDTO = new CompraDetailDTO(nuevaCompraEntity);
       LOGGER.log(Level.INFO, "CompraResource postCompra: output: {0}", nuevaCompraDTO);
       return nuevaCompraDTO;   
    }
    
    
    /**
     * Busca y devuelve todas las compras que existen en la aplicacion.
     *
     * @return JSONArray {@link CompraDetailDTO} - Las compras
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<CompraDetailDTO> getCompras() {
        LOGGER.info("CompraResource getCompras: input: void");
        List<CompraDetailDTO> listaCompras = listEntity2DetailDTO(compraLogic.getCompras());
        LOGGER.log(Level.INFO, "CompraResource getCompras: output: {0}", listaCompras);
        return listaCompras;
    }
    
       
    /**
     * Busca la compra con el id asociado recibido en la URL y la devuelve.
     *
     * @param compraId Identificador de la compra que se esta buscando.
     * @return JSON {@link CompraDetailDTO} - La compra buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la compra.
     */
    @GET
    @Path("{comprasId : \\d+}")
    public CompraDetailDTO getCompra(@PathParam("comprasId") Long compraId)
    {
        LOGGER.log(Level.INFO, "CompraResource getCompra: input: {0}", compraId);
        CompraEntity compraEntity = compraLogic.getCompra(compraId);
        if (compraEntity == null) {
            throw new WebApplicationException(recursoCompras + compraId + existe, 404);
        }
        CompraDetailDTO detailDTO = new CompraDetailDTO(compraEntity);
        LOGGER.log(Level.INFO, "CompraResource getCompra: output: {0}", detailDTO);
        return detailDTO;
    }
    
    /**
     * Actualiza la compra con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param compraId Identificador de la compra que se desea
     * actualizar.
     * @param compra {@link CompraDetailDTO} La compra que se desea
     * guardar.
     * @return JSON {@link CompraDetailDTO} - La compra guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la compra a
     * actualizar.
     */
    @PUT
    @Path("{comprasId : \\d+}")
    public CompraDetailDTO putCompra(@PathParam("comprasId") Long compraId, CompraDetailDTO compra)
    {
        LOGGER.log(Level.INFO, "CompraResource putCompra: input: id:{0} , compra: {1}", new Object[]{compraId, compra});
        compra.setId(compraId);
        if (compraLogic.getCompra(compraId) == null) {
            throw new WebApplicationException(recursoCompras + compraId + existe, 404);
        }
        CompraDetailDTO detailDTO = new CompraDetailDTO(compraLogic.updateCompra(compraId, compra.toEntity()));
        LOGGER.log(Level.INFO, "CompraResource putCompra: output: {0}", detailDTO);
        return detailDTO;
    }
    
    /**
     * Borra la compra con el id asociado recibido en la URL, Borrar para compra es cambiar el estado de TRUE a FALSE.
     *
     * @param compraId Identificador de la compra que se desea borrar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la compra.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la compra.
     */
    @DELETE
    @Path("{comprasId : \\d+}")
    public void deleteCompra (@PathParam("comprasId") Long compraId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CompraResource deleteCompra: input: {0}", compraId);
        if (compraLogic.getCompra(compraId) == null) {
            throw new WebApplicationException(recursoCompras + compraId + existe, 404);
        }
        compraLogic.deleteCompra(compraId);
        LOGGER.info("CompraResource deleteCompra: output: void");
    }
       
    
    /**
     * Conexión con el servicio de boletas para una compra.
     * {@link CompraBoletasResource}
     *
     * Este método conecta la ruta de /compras con las rutas de /boletas que
     * dependen de la compra, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de las boletas de una compra.
     *
     * @param compraId El ID de la compra con respecto a la cual se
     * accede al servicio.
     * @return El servicio de boletas para esta compra en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la compra.
     */
    @Path("{comprasId: \\d+}/boletas")
    public Class<CompraBoletasResource> getCompraBoletasResource(@PathParam("comprasId") Long compraId) {
        if (compraLogic.getCompra(compraId) == null) {
            throw new WebApplicationException(recursoCompras + compraId + existe, 404);
        }
        return CompraBoletasResource.class;
    }
    
    
       /**
     * Conexión con el servicio de devolucion para una compra.
     * {@link CompraDevolucionResource}
     *
     * Este método conecta la ruta de /compras con la ruta de /devoluciones que
     * depende de la compra, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de la devolucion de una compra.
     *
     * @param compraId El ID de la compra con respecto a la cual se
     * accede al servicio.
     * @return El servicio de devolucion para esta compra en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la compra.
     */
    @Path("{comprasId: \\d+}/devoluciones")
    public Class<CompraDevolucionResource> getCompraDevolucionResource(@PathParam("comprasId") Long compraId) {
        if (compraLogic.getCompra(compraId) == null) {
            throw new WebApplicationException(recursoCompras + compraId + existe, 404);
        }
        return CompraDevolucionResource.class;
    }

    
    
    /**
     * Conexión con el servicio de cliente para una compra.
     * {@link CompraClienteResource}
     *
     * Este método conecta la ruta de /compras con la ruta de /clientes que
     * depende de la compra, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga del cliente de una compra.
     *
     * @param compraId El ID de la compra con respecto a la cual se
     * accede al servicio.
     * @return El servicio de cliente para esta compra en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la compra.
     */
    @Path("{comprasId: \\d+}/clientes")
    public Class<CompraClienteResource> getCompraClienteResource(@PathParam("comprasId") Long compraId) {
        if (compraLogic.getCompra(compraId) == null) {
            throw new WebApplicationException("El recurso /compras/" + compraId + " no existe.", 404);
        }
        return CompraClienteResource.class;
    }
    
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos CompraEntity a una lista de
     * objetos CompraDetailDTO (json)
     *
     * @param entityList corresponde a la lista de compras de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de compras en forma DTO (json)
     */
    private List<CompraDetailDTO> listEntity2DetailDTO(List<CompraEntity> entityList) {
        List<CompraDetailDTO> list = new ArrayList<>();
        for (CompraEntity entity : entityList) {
            list.add(new CompraDetailDTO(entity));
        }
        return list;
    }
    
}
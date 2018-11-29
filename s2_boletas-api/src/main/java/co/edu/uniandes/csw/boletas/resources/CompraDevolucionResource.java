/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.dtos.DevolucionDTO;
import co.edu.uniandes.csw.boletas.ejb.CompraDevolucionLogic;
import co.edu.uniandes.csw.boletas.ejb.CompraLogic;
import co.edu.uniandes.csw.boletas.ejb.DevolucionLogic;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Gabriel Hamilton
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CompraDevolucionResource {
    
    private static final String RECURSOCO = "El Recurso /compras/ ";
    private static final String RECURSODE = "El Recurso devoluciones/ ";
    private static final String EXISTE = " no existe";
    private static final Logger LOGGER = Logger.getLogger(CompraDevolucionResource.class.getName());

    @Inject
    private CompraDevolucionLogic compraDevolucionLogic;

    @Inject
    private DevolucionLogic devolucionLogic;
    
    @Inject
    private CompraLogic compraLogic;

    /**
     * Asocia una devolucion existente con un compra existente
     *
     * @param devolucionId El ID de la devolucion que se va a asociar
     * @param compraId El ID del compra al cual se le va a asociar la devolucion
     * @return JSON {@link DevolucionDetailDTO} - La devolucion asociada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la devolucion.
     */
    @POST
    @Path("{devolucionesId: \\d+}")
    public DevolucionDTO addDevolucion(@PathParam("comprasId") Long compraId, @PathParam("devolucionesId") Long devolucionId) {
        LOGGER.log(Level.INFO, "CompraDevolucionResource addDevolucion: input: compraId {0} , devolucionId {1}", new Object[]{compraId, devolucionId});
        if (devolucionLogic.getDevolucion(devolucionId) == null) {
            throw new WebApplicationException(RECURSODE + devolucionId + EXISTE, 404);
        }
        if (compraLogic.getCompra(compraId) == null) {
            throw new WebApplicationException(RECURSOCO + compraId + EXISTE, 404);
        }
        DevolucionDTO detailDTO = new DevolucionDTO(compraDevolucionLogic. addDevolucion( devolucionId,compraId));
        LOGGER.log(Level.INFO, "CompraDevolucionResource addDevolucion: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Busca y devuelve la devolucion con el ID recibido en la URL, relativo a un
     * compra.
     *
     * @param devolucionId El ID de la devolucion que se busca
     * @param compraId El ID del compra del cual se busca la devolucion
     * @return {@link DevolucionDetailDTO} - La devolucion encontrada en el compra.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra la devolucion.
     */
    @GET
    public DevolucionDTO getDevolucion(@PathParam("comprasId") Long compraId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CompraDevolucionResource getDevolucion: input: {0}", compraId);
        if (compraLogic.getCompra(compraId) == null) {
            throw new WebApplicationException(RECURSOCO + compraId + EXISTE, 404);
        }
        DevolucionDTO devolucion =new DevolucionDTO(compraDevolucionLogic.getDevolucion(compraId));
        LOGGER.log(Level.INFO, "CompraDevolucionResource getDevolucion: output: {0}", devolucion);
        return devolucion;
    }

    /**
     * Actualiza la devolucion de una compra con la devolucion que se recibe en
     * el cuerpo.
     *
     * @param compraId El ID del compra al cual se le va a asociar la devolucion
     * @param devolucion {@link DevolucionDTO} - La devolucion que se desea guardar.
     * @return devolucion {@link DevolucionDTO} - La devolucion actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra la devolucion.
     */
    @PUT
    public DevolucionDTO updateDevolucion(@PathParam("comprasId") Long compraId, DevolucionDTO devolucion) {
        LOGGER.log(Level.INFO, "CompraDevolucionResource updateDevolucion: input: compraId {0} , devoluciones {1}", new Object[]{compraId, devolucion});
        
        if (devolucionLogic.getDevolucion(devolucion.getId()) == null) {
            throw new WebApplicationException(RECURSODE + devolucion.getId() + EXISTE, 404);
        }
       
        DevolucionDTO nueva = new DevolucionDTO( compraDevolucionLogic.updateDevolucion(compraId, devolucion.toEntity()));
        LOGGER.log(Level.INFO, "CompraDevolucionResource updateDevolucion: output:{0}", nueva);
        return nueva;
    }

    /**
     * Elimina la conexión entre la devolucion y el libro recibidos en la URL.
     *
     * @param compraId El ID del compra al cual se le va a desasociar la devolucion
     * @param devolucionId El ID de la devolucion que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra la devolucion.
     */
    @DELETE
    @Path("{devolucionId: \\d+}")
    public void deleteDevolucion(@PathParam("comprasId") Long compraId, @PathParam("devolucionId") Long devolucionId) {
        LOGGER.log(Level.INFO, "CompraDevolucionResource removeDevolucion: input: compraId {0} , devolucionId {1}", new Object[]{compraId, devolucionId});
        if (devolucionLogic.getDevolucion(devolucionId) == null) {
            throw new WebApplicationException(RECURSODE + devolucionId + EXISTE, 404);
        }
        compraDevolucionLogic.removeDevolucion(compraId, devolucionId);
        LOGGER.info("CompraDevolucionResource removeDevolucion: output: void");
    }
    
}
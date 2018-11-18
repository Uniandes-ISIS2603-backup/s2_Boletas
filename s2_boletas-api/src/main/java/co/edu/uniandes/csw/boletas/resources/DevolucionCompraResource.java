/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.ejb.CompraLogic;
import co.edu.uniandes.csw.boletas.dtos.CompraDetailDTO;
import co.edu.uniandes.csw.boletas.ejb.DevolucionCompraLogic;
import co.edu.uniandes.csw.boletas.ejb.DevolucionLogic;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @compra Gabriel Hamilton
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DevolucionCompraResource {
    private static final String recursoDe = "El Recurso /devoluciones/ ";
    private static final String recursoCo = "El Recurso /compras/ ";
    private static final String existe = " /no existe";
    
    private static final Logger LOGGER = Logger.getLogger(DevolucionCompraResource.class.getName());

    @Inject
    private DevolucionCompraLogic devolucionCompraLogic;

    @Inject
    private CompraLogic compraLogic;
    
     @Inject
    private DevolucionLogic devolucionLogic;

    /**
     * Actualiza la compra de una devolucion con la compra que se recibe en
     * el cuerpo.
     *
     * @param devolucionesId El ID de la devolucion a la cual se le va a asociar la compra
     * @param compras JSONArray {@link CompraDetailDTO} - La compra que se desea guardar.
     * @return compra {@link CompraDetailDTO} - La compra actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra la compra.
     */
    @PUT
    public CompraDetailDTO replaceCompras(@PathParam("devolucionesId") Long devolucionesId, CompraDetailDTO compras) {
        LOGGER.log(Level.INFO, "DevolucionComprasResource replaceCompra: input: devolucionesId {0} , compras {1}", new Object[]{devolucionesId, compras});
      
            if (compraLogic.getCompra(compras.getId()) == null) {
                throw new WebApplicationException(recursoCo + compras.getId() + existe, 404);
            }
            if (devolucionLogic.getDevolucion(devolucionesId) == null) {
                throw new WebApplicationException(recursoDe + devolucionesId + existe, 404);
            }
       
        CompraDetailDTO compra = new CompraDetailDTO(devolucionCompraLogic.replaceCompra(devolucionesId, compras.getId()).getCompra());
        LOGGER.log(Level.INFO, "DevolucionComprasResource replaceCompras: output:{0}", compra);
        return compra;
    }

    /**
     * Elimina la conexión entre la compra reibida en la URL.
     *
     * @param devolucionesId El ID de la devolucion a la cual se le va a desasociar la compra
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra la compra.
     */
    @DELETE
    @Path("{devolucionesId: \\d+}")
    public void removeCompra(@PathParam("devolucionesId") Long devolucionesId)
    {
        LOGGER.log(Level.INFO, "DevolucionComprasResource removeCompra: input: devolucionesId {0} ", new Object[]{devolucionesId});
        if (devolucionLogic.getDevolucion(devolucionesId) == null) {
                throw new WebApplicationException(recursoDe + devolucionesId +existe, 404);
            }
       
        devolucionCompraLogic.removeCompra(devolucionesId);
        LOGGER.info("DevolucionComprasResource removeCompra: output: void");
    }
    
}
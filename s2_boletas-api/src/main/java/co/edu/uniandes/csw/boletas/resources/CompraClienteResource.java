/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;


import co.edu.uniandes.csw.boletas.dtos.ClienteDetailDTO;
import co.edu.uniandes.csw.boletas.dtos.CompraDetailDTO;
import co.edu.uniandes.csw.boletas.ejb.ClienteLogic;
import co.edu.uniandes.csw.boletas.ejb.CompraClienteLogic;
import co.edu.uniandes.csw.boletas.ejb.CompraLogic;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
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
public class CompraClienteResource {
 
private static final Logger LOGGER = Logger.getLogger(BoletaCompraResource.class.getName());

    @Inject
    private CompraLogic compraLogic; 

    @Inject
    private CompraClienteLogic compraClienteLogic; 

    @Inject
    private ClienteLogic clienteLogic; 

    /**
     * Remplaza la instancia de Cliente asociada a una Compra.
     *
     * @param comprasId Identificador de la compra que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param cliente El cliente que será de la compra.
     * @return JSON {@link CompraDetailDTO} - El arreglo de compras guardado en el cliente.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el cliente o la
     * compra.
     */
    @PUT
    public CompraDetailDTO replaceCliente(@PathParam("comprasId") Long comprasId, ClienteDetailDTO cliente) {
        LOGGER.log(Level.INFO, "CompraClienteResource replaceCliente: input: comprasId{0} , Compra:{1}", new Object[]{comprasId, cliente});
        if (compraLogic.getCompra(comprasId) == null) {
            throw new WebApplicationException("El recurso /compras/" + comprasId + " no existe.", 404);
        }
        if (clienteLogic.getCliente(cliente.getId()) == null) {
            throw new WebApplicationException("El recurso /clientes/" + cliente.getId() + " no existe.", 404);
        }
        CompraDetailDTO compraDetailDTO = new CompraDetailDTO(compraClienteLogic.replaceCliente(comprasId, cliente.getId()));
        LOGGER.log(Level.INFO, "CompraClienteResource replaceCliente: output: {0}", compraDetailDTO);
        return compraDetailDTO;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;
import co.edu.uniandes.csw.boletas.dtos.CompraDTO;
import co.edu.uniandes.csw.boletas.ejb.ClienteCompraLogic;
import co.edu.uniandes.csw.boletas.ejb.CompraLogic;
import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Gabriel Hamilton
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteCompraResource {
    
    private static final Logger LOGGER = Logger.getLogger(ClienteCompraResource.class.getName());

    @Inject
    private ClienteCompraLogic clienteCompraLogic;

    @Inject
    private CompraLogic compraLogic;

    /**
     * Asocia un autor existente con un cliente existente
     *
     * @param compraId El ID de la compra que se va a asociar
     * @param clienteId El ID del cliente al cual se le va a asociar la compra
     * @return JSON {@link CompraDTO} - La compra asociada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la compra.
     */
    @POST
    @Path("{compraId: \\d+}")
    public CompraDTO addCompra(@PathParam("clienteId") Long clienteId, @PathParam("compraId") Long compraId) {
        LOGGER.log(Level.INFO, "ClienteCompraResource addCompra: input: clienteId {0} , compraId {1}", new Object[]{clienteId, compraId});
        if (compraLogic.getCompra(compraId) == null) {
            throw new WebApplicationException("El recurso /compra/" + compraId + " no existe.", 404);
        }
        CompraDTO detailDTO = new CompraDTO(clienteCompraLogic. addCompra(clienteId, compraId));
        LOGGER.log(Level.INFO, "ClienteCompraResource addCompra: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Busca y devuelve todos las compras que existen en un cliente.
     *
     * @param clienteId El ID del cliente del cual se buscan las compras
     * @return JSONArray {@link CompraDTO} - Las compras encontradas en el
     * cliente. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<CompraDTO> getCompras(@PathParam("clienteId") Long clienteId) {
        LOGGER.log(Level.INFO, "ClienteCompraResource getCompras: input: {0}", clienteId);
        List<CompraDTO> lista =comprasListEntity2DTO(clienteCompraLogic.getCompras(clienteId));
        LOGGER.log(Level.INFO, "ClienteCompraResource getCompras: output: {0}", lista.toString());
        return lista;
    }

    /**
     * Busca y devuelve la compra con el ID recibido en la URL, relativo a un
     * cliente.
     *
     * @param compraId El ID de la compra que se busca
     * @param clienteId El ID del cliente del cual se busca la compra
     * @return {@link CompraDTO} - La compra encontrada en el cliente.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra la compra.
     */
    @GET
    @Path("{compraId: \\d+}")
    public CompraDTO getCompra(@PathParam("clienteId") Long clienteId, @PathParam("compraId") Long compraId) {
        LOGGER.log(Level.INFO, "ClienteCompraResource getCompra: input: clienteId {0} , compraId {1}", new Object[]{clienteId, compraId});
        if (compraLogic.getCompra(compraId) == null) {
            throw new WebApplicationException("El recurso /compra/" + compraId + " no existe.", 404);
        }
        CompraDTO detailDTO = new CompraDTO(clienteCompraLogic.getCompra(clienteId, compraId));
        LOGGER.log(Level.INFO, "ClienteCompraResource getCompra: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza la lista de compras de un cliente con la lista que se recibe en
     * el cuerpo.
     *
     * @param clienteId El ID del cliente al cual se le va a asociar la lista de
     * compras
     * @param compras JSONArray {@link CompraDTO} - La lista de compras
     * que se desea guardar.
     * @return JSONArray {@link CompraDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra la compra.
     */
    @PUT
    public List<CompraDTO> replaceCompras(@PathParam("clienteId") Long clienteId, List<CompraDTO> compras) {
        LOGGER.log(Level.INFO, "ClienteCompraResource replaceCompras: input: clienteId {0} , compras {1}", new Object[]{clienteId, compras.toString()});
        for (CompraDTO compra : compras) {
            if (compraLogic.getCompra(compra.getId()) == null) {
                throw new WebApplicationException("El recurso /compra/" + compra.getId() + " no existe.", 404);
            }
        }
        List<CompraDTO> lista = comprasListEntity2DTO(clienteCompraLogic.replaceCompras(clienteId, comprasListDTO2Entity(compras)));
        LOGGER.log(Level.INFO, "ClienteCompraResource replaceCompras: output:{0}", lista.toString());
        return lista;
    }

    /**
     * Elimina la conexión entre la compra y el libro recibidos en la URL.
     *
     * @param clienteId El ID del cliente al cual se le va a desasociar la compra
     * @param compraId El ID de la compra que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra la compra.
     */
    @DELETE
    @Path("{compraId: \\d+}")
    public void removeCompra(@PathParam("clienteId") Long clienteId, @PathParam("compraId") Long compraId) {
        LOGGER.log(Level.INFO, "ClienteCompraResource removeCompra: input: clienteId {0} , compraId {1}", new Object[]{clienteId, compraId});
        if (compraLogic.getCompra(compraId) == null) {
            throw new WebApplicationException("El recurso /compra/" + compraId + " no existe.", 404);
        }
        clienteCompraLogic.removeCompra(clienteId, compraId);
        LOGGER.info("ClienteCompraResource removeCompra: output: void");
    }

    /**
     * Convierte una lista de CompraEntity a una lista de CompraDTO.
     *
     * @param entityList Lista de CompraEntity a convertir.
     * @return Lista de CompraDTO convertida.
     */
    private List<CompraDTO> comprasListEntity2DTO(List<CompraEntity> entityList) {
        List<CompraDTO> list = new ArrayList<>();
        for (CompraEntity entity : entityList) {
            list.add(new CompraDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de CompraDTO a una lista de CompraEntity.
     *
     * @param dtos Lista de CompraDTO a convertir.
     * @return Lista de CompraEntity convertida.
     */
    private List<CompraEntity> comprasListDTO2Entity(List<CompraDTO> dtos) {
        List<CompraEntity> list = new ArrayList<>();
        for (CompraDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
}
    
}


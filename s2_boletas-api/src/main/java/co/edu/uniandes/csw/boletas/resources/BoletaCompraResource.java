/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;
import co.edu.uniandes.csw.boletas.dtos.BoletaDTO;
import co.edu.uniandes.csw.boletas.dtos.CompraDetailDTO;
import co.edu.uniandes.csw.boletas.ejb.BoletaCompraLogic;
import co.edu.uniandes.csw.boletas.ejb.BoletaLogic;
import co.edu.uniandes.csw.boletas.ejb.CompraLogic;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 * Clase que implementa el recurso "compras/{id}/boletas".
 *
 * @author Gabriel Hamilton
 */
@Path("boletas/{boletasId: \\d+}/compras")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BoletaCompraResource {

    private static final Logger LOGGER = Logger.getLogger(BoletaCompraResource.class.getName());

    @Inject
    private BoletaLogic boletaLogic; 

    @Inject
    private BoletaCompraLogic boletaCompraLogic; 

    @Inject
    private CompraLogic compraLogic; 

    /**
     * Remplaza la instancia de Compra asociada a un Boleta.
     *
     * @param boletasId Identificador de la boleta que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param compra La compra que será de la boleta.
     * @return JSON {@link BoletaDTO} - El arreglo de boletas guardado en la
     * compra.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la compra o la
     * boleta.
     */
    @PUT
    public BoletaDTO replaceCompra(@PathParam("boletasId") Long boletasId, CompraDetailDTO compra) {
        LOGGER.log(Level.INFO, "BoletaCompraResource replaceCompra: input: boletasId{0} , Compra:{1}", new Object[]{boletasId, compra.toString()});
        if (boletaLogic.getBoleta(boletasId) == null) {
            throw new WebApplicationException("El recurso /boletas/" + boletasId + " no existe.", 404);
        }
        if (compraLogic.getCompra(compra.getId()) == null) {
            throw new WebApplicationException("El recurso /compras/" + compra.getId() + " no existe.", 404);
        }
        BoletaDTO boletaDetailDTO = new BoletaDTO(boletaCompraLogic.replaceCompra(boletasId, compra.getId()));
        LOGGER.log(Level.INFO, "BoletaCompraResource replaceCompra: output: {0}", boletaDetailDTO.toString());
        return boletaDetailDTO;
    }
}

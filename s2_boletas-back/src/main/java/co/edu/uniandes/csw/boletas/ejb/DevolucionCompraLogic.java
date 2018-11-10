/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import co.edu.uniandes.csw.boletas.entities.DevolucionEntity;
import co.edu.uniandes.csw.boletas.persistence.CompraPersistence;
import co.edu.uniandes.csw.boletas.persistence.DevolucionPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Gabriel Hamilton
 */
@Stateless
public class DevolucionCompraLogic {
    private static final Logger LOGGER = Logger.getLogger(BoletaCompraLogic.class.getName());

    @Inject
    private DevolucionPersistence devolucionPersistence;

    @Inject
    private CompraPersistence compraPersistence;

   
 /**
     * Remplazar la compra de una devolucion.
     *
     * @param devolucionesId id de la devolucion que se quiere actualizar.
     * @param comprasId El id de la compra que se será de la devolucion
     * @return la nueva devolucion.
     */
    public DevolucionEntity replaceCompra(Long devolucionesId, Long comprasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar devolucion con id = {0}", devolucionesId);
        CompraEntity compraEntity = compraPersistence.find(comprasId);
        DevolucionEntity devolucionEntity = devolucionPersistence.find(devolucionesId);
        devolucionEntity.setCompra(compraEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar devolucion con id = {0}", devolucionEntity.getId());
        return devolucionEntity;
    }

    /**
     * Borrar un devolucion de una compra. Este metodo se utiliza para borrar la
     * relacion de un devolucion.
     *
     * @param devolucionesId El devolucion que se desea borrar de la compra.
     */
    public void removeCompra(Long devolucionesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la Compra del devolucion con id = {0}", devolucionesId);
        DevolucionEntity devolucionEntity = devolucionPersistence.find(devolucionesId);
        CompraEntity compraEntity = compraPersistence.find(devolucionEntity.getCompra().getId());
        devolucionEntity.setCompra(null);
        compraEntity.setDevolucion(null);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la Compra del devolucion con id = {0}", devolucionEntity.getId());
}
}

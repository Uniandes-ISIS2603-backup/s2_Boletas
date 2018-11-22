/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import co.edu.uniandes.csw.boletas.persistence.BoletaPersistence;
import co.edu.uniandes.csw.boletas.persistence.CompraPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Gabriel Hamilton
 */
@Stateless
public class BoletaCompraLogic {
    private static final Logger LOGGER = Logger.getLogger(BoletaCompraLogic.class.getName());

    @Inject
    private BoletaPersistence boletaPersistence;

    @Inject
    private CompraPersistence compraPersistence;

    /**
     * Remplazar la compra de una boleta.
     *
     * @param boletasId id de la boleta que se quiere actualizar.
     * @param comprasId El id de la compra que se será de la boleta
     * @return la nueva boleta.
     */
    public BoletaEntity replaceCompra(Long boletasId, Long comprasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar boleta con id = {0}", boletasId);
        CompraEntity compraEntity = compraPersistence.find(comprasId);
        BoletaEntity boletaEntity = boletaPersistence.find(boletasId);
        boletaEntity.setCompra(compraEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar boleta con id = {0}", boletaEntity.getId());
        return boletaEntity;
    }

    /**
     * Borrar un boleta de una compra. Este metodo se utiliza para borrar la
     * relacion de un boleta.
     *
     * @param boletasId El boleta que se desea borrar de la compra.
     */
    public void removeCompra(Long boletasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la Compra del boleta con id = {0}", boletasId);
        BoletaEntity boletaEntity = boletaPersistence.find(boletasId);
        CompraEntity compraEntity = compraPersistence.find(boletaEntity.getCompra().getId());
        boletaEntity.setCompra(null);
        compraEntity.getBoletas().remove(boletaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la Compra del boleta con id = {0}", boletaEntity.getId());
}
    
}

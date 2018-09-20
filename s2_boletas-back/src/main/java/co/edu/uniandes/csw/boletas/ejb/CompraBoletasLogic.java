/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.BoletaPersistence;
import co.edu.uniandes.csw.boletas.persistence.CompraPersistence;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Gabriel Hamilton 
 */
@Stateless
public class CompraBoletasLogic {
    
    private static final Logger LOGGER = Logger.getLogger(CompraBoletasLogic.class.getName());

    @Inject
    private BoletaPersistence boletaPersistence;

    @Inject
    private CompraPersistence compraPersistence;

    /**
     * Agregar una boleta a la compra
     *
     * @param boletasId El id de la boleta a guardar
     * @param comprasId El id de la compra en la cual se va a guardar la
     * boleta.
     * @return La boleta creada.
     */
    public BoletaEntity addBoleta( Long comprasId, Long boletasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle una boleta a la compra con id = {0}", comprasId);
        CompraEntity compraEntity = compraPersistence.find(comprasId);
        BoletaEntity boletaEntity = boletaPersistence.find(boletasId);
        compraEntity.getBoletas().add(boletaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle una compra a la compra con id = {0}", comprasId);
	return boletaPersistence.find(boletasId);
    }

    /**
     * Retorna todas las boletas asociadas a una compra
     *
     * @param comprasId El ID de la compra buscada
     * @return La lista de boletas de la compra
     */
    public List<BoletaEntity> getBoletas(Long comprasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las boletas asociadas a la compra con id = {0}", comprasId);
        return compraPersistence.find(comprasId).getBoletas();
    }

    /**
     * Retorna una boleta asociada a una compra
     *
     * @param comprasId El id de la compra a buscar.
     * @param boletasId El id de la boleta a buscar
     * @return La boleta encontrada dentro de la compra.
     * @throws BusinessLogicException Si la boleta no se encuentra en la
     * compra
     */
    public BoletaEntity getBoleta(Long comprasId, Long boletasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la boleta con id = {0} de la compra con id = " + comprasId, boletasId);
        List<BoletaEntity> boletas = compraPersistence.find(comprasId).getBoletas();
        BoletaEntity boletaEntity = boletaPersistence.find(boletasId);
        int index = boletas.indexOf(boletaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la boleta con id = {0} de la compra con id = " + comprasId, boletasId);
        if (index >= 0) {
            return boletas.get(index);
        }
        throw new BusinessLogicException("La boleta no está asociado a la compra");
    }

    /**
     * Remplazar boletas de una compra
     *
     * @param boletas Lista de boletas que serán los de la compra.
     * @param comprasId El id de la compra que se quiere actualizar.
     * @return La lista de boletas actualizada.
     */
    public List<BoletaEntity> putBoletas(Long comprasId, List<BoletaEntity> boletas) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la compra con id = {0}", comprasId);
        CompraEntity compraEntity = compraPersistence.find(comprasId);
        List<BoletaEntity> boletaList = boletaPersistence.findAll();
        for (BoletaEntity boleta : boletaList) {
            if (boletas.contains(boleta)) {
                boleta.setCompra(compraEntity);
            } else if (boleta.getCompra() != null && boleta.getCompra().equals(compraEntity)) {
                boleta.setCompra(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la compra con id = {0}", comprasId);
        return boletas;
    }

    
}

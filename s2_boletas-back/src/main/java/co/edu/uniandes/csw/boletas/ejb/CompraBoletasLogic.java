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
     * metodo que agrega una boleta a una compra
     * @param boletaId, id de la boleta que se va a guardar
     * @param compraId, id de la compra en donde se va a guardar la boleta
     * @return la boleta creada
     */
    public BoletaEntity createBoleta(Long boletaId, Long compraId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle una boleta a la compra con id = {0}", compraId);
        CompraEntity compraEntity = compraPersistence.find(compraId);
        BoletaEntity boletaEntity = boletaPersistence.find(boletaId);
        boletaEntity.setCompra(compraEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle una boleta a la compra con id = {0}", compraId);
        return boletaEntity;
    }
    
    /**
     * Retorna una boleta asociada a una compra
     *
     * @param compraId El id de la compra a buscar.
     * @param boletaId El id de la boleta a buscar
     * @return la boleta encontrada dentro de la compra.
     * @throws BusinessLogicException Si la boleta no se encuentra en la
     * compra
     */
    public BoletaEntity getBoleta(Long compraId, Long boletaId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la boleta con id = {0} de la compra con id = " + compraId, boletaId);
        List<BoletaEntity> boletas = compraPersistence.find(compraId).getBoletas();
        BoletaEntity boletaEntity = boletaPersistence.find(boletaId);
        int index = boletas.indexOf(boletaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la boleta con id = {0} de la compra con id = " + compraId, boletaId);
        if (index >= 0) {
            return boletas.get(index);
        }
        throw new BusinessLogicException("La boleta no está asociada a la compra");
    }
    
     /**
     * Retorna todas las boletas asociadas a una compra
     *
     * @param compraId El ID de la compra buscada
     * @return La lista de boletas de la compra
     */
    public List<BoletaEntity> getBoletas(Long compraId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las boletas asociadas a la compra con id = {0}", compraId);
        return compraPersistence.find(compraId).getBoletas();
    }
    
    
    /**
     * Remplazar boletas de una compra
     *
     * @param boletas Lista de boletas que serán los de la compra.
     * @param compraId El id de la compra que se quiere actualizar.
     * @return La lista de boletas actualizada.
     */
    public List<BoletaEntity> putBoletas(Long compraId, List<BoletaEntity> boletas) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la compra con id = {0}", compraId);
        CompraEntity compraEntity = compraPersistence.find(compraId);
        List<BoletaEntity> boletaList = boletaPersistence.findAll();
        for (BoletaEntity boleta : boletaList) {
            if (boletas.contains(boleta)) {
                boleta.setCompra(compraEntity);
            } else if (boleta.getCompra() != null && boleta.getCompra().equals(compraEntity)) {
                boleta.setCompra(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la compra con id = {0}", compraId);
        return boletas;
    }
    
    
    /**
     * Desasocia una boleta existente de una compra existente
     *
     * @param compraId Identificador de la instancia de Compra
     * @param boletaId Identificador de la instancia de Boleta
     */
    public void deleteBoleta(Long compraId, Long boletaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar una boleta de la compra con id = {0}", compraId);
        CompraEntity compraEntity = compraPersistence.find(compraId);
        BoletaEntity boletaEntity = boletaPersistence.find(boletaId);
        
        boletaEntity.setCompra(null);
        
        List<BoletaEntity> boletas = compraEntity.getBoletas();
        int index = boletas.indexOf(boletaEntity);
        
        boletas.remove(index);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un libro del author con id = {0}", compraId);
    }
    
    
    /**
     * Desasocia una boleta existente de una compra existente
     *
     * @param compraId Identificador de la instancia de Compra
     * @param boletaId Identificador de la instancia de Boleta
     */
    public void deleteBoletas(Long compraId, Long boletaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar una boleta de la compra con id = {0}", compraId);
        CompraEntity compraEntity = compraPersistence.find(compraId);
        BoletaEntity boletaEntity = boletaPersistence.find(boletaId);
        
        boletaEntity.setCompra(null);
        
        List<BoletaEntity> boletas = compraEntity.getBoletas();
        int index = boletas.indexOf(boletaEntity);
        
        boletas.remove(index);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un libro del author con id = {0}", compraId);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
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
public class CompraLogic
{
    private static final Logger LOGGER = Logger.getLogger(CompraLogic.class.getName());
    
    @Inject
    private CompraPersistence persistence; 
    
    /**
     * Crear la Compra en persistencia
     * @param compraEntity
     * @return compraEntity, la entity de la compra despues de la persistencia
     * @throws BusinessLogicException 
     */
    public CompraEntity createCompra(CompraEntity compraEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la compra");
        persistence.create(compraEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la compra");
        return compraEntity;
    }

    
    /**
     * Busca la compra con el id ingresado por parametro
     * @param compraId
     * @return compraEntity, la compra con el id buscado
     */
    public CompraEntity getCompra(Long compraId)
    {
       LOGGER.log(Level.INFO, "Inicia proceso de consultar la compra con id = {0}", compraId);
       CompraEntity compraEntity = persistence.find(compraId);
       if (compraEntity == null) 
       {
           LOGGER.log(Level.SEVERE, "La compra con el id = {0} no existe", compraId);
       }
       LOGGER.log(Level.INFO, "Termina proceso de consultar la compra con id = {0}", compraId);
       return compraEntity;
    }
     
     
    /**
     * Actualiza una compra
     * @param compraId
     * @param compraEntity
     * @return newEntity, la nueva entity con los cambios realizados
     */
    public CompraEntity updateCompra(Long compraId, CompraEntity compraEntity)
    {
       LOGGER.log(Level.INFO, "Inicia proceso de actualizar la compra con id = {0}", compraId);
       CompraEntity newEntity = persistence.update(compraEntity);
       LOGGER.log(Level.INFO, "Termina proceso de actualizar la compra con id = {0}", compraEntity.getId());
       return newEntity;
    }
    
    
    /**
     * Borra una compra con el id ingresado por parametro
     * @param compraId
     * @throws BusinessLogicException 
     */
    public void deleteCompra(Long compraId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la compra con id = {0}", compraId);
        //se queda la siguiente linea hasta confirmar una regla de negocio
        //CompraEntity compra = getCompra(compraId);
        persistence.delete(compraId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la compra con id = {0}", compraId);
    }
}
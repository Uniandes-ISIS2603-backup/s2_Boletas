/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
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
public class CompraLogic
{
    private static final Logger LOGGER = Logger.getLogger(CompraLogic.class.getName());
    
    @Inject
    private CompraPersistence persistence; 
    
    /**
     * Crear la Compra en persistencia
     * @param compraEntity
     * @return compraEntity, la entity de la compra despues de la persistencia 
     * @throws BusinessLogicException Si la compra a persistir ingresa con estado de eliminada.
     */
    public CompraEntity createCompra(CompraEntity compraEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la compra");
        if(!compraEntity.getEstado())
        {
            throw new BusinessLogicException("No se puede crear la compra con estado cancelada (estado = false) ");
        }
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
     *
     * Obtener todas las compras existentes en la base de datos.
     *
     * @return una lista de compras.
     */
    public List<CompraEntity> getCompras() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las compras");
        List<CompraEntity> compras = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las compras");
        return compras;
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
     * Borra una compra con el id ingresado por parametro, borrar en compra es cambiar el estado de TRUE a FALSE.
     * @param compraId
     * @throws BusinessLogicException 
     */
    public void deleteCompra(Long compraId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la compra con id = {0}", compraId);
        CompraEntity compra = getCompra(compraId);
        if (compra != null && !compra.getBoletas().isEmpty()) {
            throw new BusinessLogicException("No se puede borrar la compra con id = " + compraId + " porque tiene boletas asociadas");
        }
        if(compra!=null && !compra.getEstado())
        {
            throw new BusinessLogicException("No se puede borrar la compra con id = " + compraId + " porque esta ya fue eliminada");
        }
        persistence.delete(compraId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la compra con id = {0}", compraId);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import co.edu.uniandes.csw.boletas.entities.DevolucionEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
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
public class CompraDevolucionLogic {
    
    private static final Logger LOGGER = Logger.getLogger(CompraDevolucionLogic.class.getName());
    
    //Injeccion de dependencias de la persistencia 
    @Inject 
    private CompraPersistence compraPersistence;
    
    @Inject 
    private DevolucionPersistence devolucionPersistence;
    
    /**
     * Asocia una devolucion  existente a un Compra existente
     *
     * @param compraId Identificador de la instancia de Compra
     * @param devolucionId Identificador de la instancia de devolucion
     * @return Instancia de Devolucion Entity que fue asociada a devolucion
     */
    public DevolucionEntity addDevolucion (Long devolucionId, Long compraId)
    {
        //Busca la devolucion con el id dado 
        DevolucionEntity devolucion= devolucionPersistence.find(devolucionId);
        
        //Busca el compra con el id dado
        CompraEntity compra= compraPersistence.find(compraId);
        
        //Asocia la devolucion a la compra. y la compra a la devolucion.
        compra.setDevolucion(devolucion);
        devolucion.setCompra(compra);
        
        return devolucion;
    }
    
    /**
     * Obtiene la instancia de DevolucionEntity asociada a una instancia de Compra
     *
     * @param comprasId Identificador de la instancia de Book
     * @param devolucionId Identificador de la instancia de Devolucion
     * @return La entidad de la devolucion asociada al compra
     * @throws BusinessLogicException Si el libro no se encuentra en la
     * editorial
     */
    public DevolucionEntity getDevolucion(Long comprasId)throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la devolucion de la compra con id = {0}", comprasId);
        CompraEntity compraEntity = compraPersistence.find(comprasId);
        DevolucionEntity devolucionEntity = devolucionPersistence.find(compraEntity.getDevolucion().getId());
        LOGGER.log(Level.INFO, "Termina proceso de consultar un devolucion del compra con id = {0}", comprasId);
        if (devolucionEntity != null) {
            return devolucionEntity;
        }
        throw new BusinessLogicException("La compra no tiene una devolucion asociada");
    }
      
            /**
     * Remplaza la instancia de devolucion  asociadas a una instancia de Compra
     *
     * @param compraId Identificador de la instancia de Compra
     * @param list Colección de instancias de DevolucionEntity  a asociar a instancia
     * de compra
     * @return Nueva colección de DevolucionEntity asociada a la instancia de Compra
     */
     public DevolucionEntity updateDevolucion(Long compraId, DevolucionEntity devolucion)
     {
         LOGGER.log(Level.INFO, "Inicia proceso de actualizar la devolucion de la compra con id = {0}", compraId);
         CompraEntity compraEntity = compraPersistence.find(compraId);
         devolucion.setCompra(compraEntity);
         compraEntity.setDevolucion(devolucion);
         LOGGER.log(Level.INFO, "Termina proceso de actualizar las devolucions del compra con id = {0}", compraId);
         return devolucion;
     }
               
                               
     /**
     * Desasocia una devolucion existente de un compra existente
     *
     * @param compraId Identificador de la instancia de Compra
     * @param devolucionId Identificador de la instancia de Devolucion
     */
    public void removeDevolucion(Long compraId, Long devolucionId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar una devolucion del compra con id = {0}", compraId);
        CompraEntity compraEntity = compraPersistence.find(compraId);
        DevolucionEntity devolucionEntity = devolucionPersistence.find(devolucionId);
        
        devolucionEntity.setCompra(null);
        compraEntity.setDevolucion(null);
        LOGGER.log(Level.INFO, "Termina proceso de borrar una devolucion del compra con id = {0}", compraId);
    }     
            
            
    
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.DevolucionEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.DevolucionPersistence;
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
public class DevolucionLogic {
    
    private static final Logger LOGGER = Logger.getLogger(DevolucionLogic.class.getName());
    
    @Inject
    private DevolucionPersistence persistence; 
    
    /**
     * Crear la Devolucion en persistencia
     * @param devolucionEntity
     * @return devolucionEntity, la entity de la devolucion despues de la persistencia 
     */
    public DevolucionEntity createDevolucion(DevolucionEntity devolucionEntity) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la devolucion");
        persistence.create(devolucionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la devolucion");
        return devolucionEntity;
    }

    
    /**
     * Busca la devolucion con el id ingresado por parametro
     * @param devolucionId
     * @return devolucionEntity, la devolucion con el id buscado
     */
    public DevolucionEntity getDevolucion(Long devolucionId)
    {
       LOGGER.log(Level.INFO, "Inicia proceso de consultar la devolucion con id = {0}", devolucionId);
       DevolucionEntity devolucionEntity = persistence.find(devolucionId);
       if (devolucionEntity == null) 
       {
           LOGGER.log(Level.SEVERE, "La devolucion con el id = {0} no existe", devolucionId);
       }
       LOGGER.log(Level.INFO, "Termina proceso de consultar la devolucion con id = {0}", devolucionId);
       return devolucionEntity;
    }
     
     /**
     *
     * Obtener todas las devoluciones existentes en la base de datos.
     *
     * @return una lista de devoluciones.
     */
    public List<DevolucionEntity> getDevoluciones() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las devoluciones");
        List<DevolucionEntity> devoluciones = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las devoluciones");
        return devoluciones;
    }
    
     
    /**
     * Actualiza una devolucion
     * @param devolucionId
     * @param devolucionEntity
     * @return newEntity, la nueva entity con los cambios realizados
     */
    public DevolucionEntity updateDevolucion(Long devolucionId, DevolucionEntity devolucionEntity)
    {
       LOGGER.log(Level.INFO, "Inicia proceso de actualizar la devolucion con id = {0}", devolucionId);
       DevolucionEntity newEntity = persistence.update(devolucionEntity);
       LOGGER.log(Level.INFO, "Termina proceso de actualizar la devolucion con id = {0}", devolucionEntity.getId());
       return newEntity;
    }
    
    
    /**
     * Borra una devolucion con el id ingresado por parametro.
     * @param devolucionId
     * @throws BusinessLogicException 
     */
    public void deleteDevolucion(Long devolucionId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la devolucion con id = {0}", devolucionId);
//        DevolucionEntity devolucion = getDevolucion(devolucionId);
//        if (devolucion != null && !devolucion.getBoletas().isEmpty()) {
//            throw new BusinessLogicException("No se puede borrar la devolucion con id = " + devolucionId + " porque tiene boletas asociadas");
//        }
        persistence.delete(devolucionId); // AQUI
        LOGGER.log(Level.INFO, "Termina proceso de borrar la devolucion con id = {0}", devolucionId);
    }
    
}

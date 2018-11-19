/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.ClienteEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.ClientePersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Vilma Tirado Gomez
 */
@Stateless
public class ClienteLogic {
        private static final Logger LOGGER = Logger.getLogger(ClienteLogic.class.getName());
    
    /**
     * Persistencia 
     */
    @Inject 
    ClientePersistence persistence;
    
    
    /**
     * POST
     * Recibe un entity como parametro y llama a la persistencia para 
     * guardar el cliente en la tabla creandole un id 
     * @param entity
     * @return la misma entidad que recibio con el id asignado por la persistencia 
     * @throws BusinessLogicException 
     */
    public ClienteEntity createCliente (ClienteEntity entity ) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "incia la creacion del cliente");
        //Verifica que se cumpla la regla de negocio 
        if(persistence.findByUser(entity.getUsuario())!=null)
        {
            throw new BusinessLogicException("Ya existe un cliente  con el nombre"+ entity.getNombre());
        }
        
        //Pone el objeto en la persistencia el cual le asigna un id
        
        persistence.create(entity);
        
        LOGGER.log(Level.INFO, "saliendo de crear cliente");
        
        //retorna la entidad con el id asignado 
        return entity;
    }
    
        /**
     *GET
     *Recibe el id de un cliente como paramentro y devuelve la entidad
     * si no existe retorna null
     * @param clienteId
     * @return el cliente con ese id
     * 
     */
    public ClienteEntity getCliente (Long clienteId)
    {
        LOGGER.log(Level.INFO, "Obtengo el cliente con id: "+ clienteId);
        
        ClienteEntity entity =persistence.find(clienteId);
        
        LOGGER.log(Level.INFO, "Salgo de obtener el cliente cpn id: "+ clienteId);
        
        return entity;
        
    }
    
     /**
     *GET ALL
     *Retorna todos los clientes 
     * @return todos los clientes 
     * 
     */
    public List<ClienteEntity> getClientes ()
    {
        LOGGER.log(Level.INFO, "Traigo todos los clientes existentes");
        
        //Se llama a la persistencia
        return persistence.findAll();        
    }
    
    /**
     *PUT 
     *Actualiza el entity recibido como parametro 
     * @param clienteId
     * @param clienteEntity La entidad que se quiere actualizar
     * @return La entidad actualizada 
     * 
     */
    public ClienteEntity updateCliente( Long clienteId,ClienteEntity clienteEntity)
    {
        LOGGER.log(Level.INFO, "Entrando a actualizar el cliente con el con id = {0}", clienteId);
        ClienteEntity newEntity= persistence.update(clienteEntity);
        LOGGER.log(Level.INFO, "saliendo de actualizar el cliente con el el con id = {0}", clienteId);
        return newEntity;
    }
    
    /**DELETE
     *Borra el entity con el id recibido como parametro 
     * @param clienteId. Id del cliente a borrar.
     * 
     */
    public void delete (Long clienteId)
    {
        LOGGER.log(Level.INFO, "Entrando a borrar el cliente");
        persistence.delete(clienteId);
        LOGGER.log(Level.INFO, "Saliendo de borrar el cliente");
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.OrganizadorEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.OrganizadorPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Vilma Tirado Gomez
 */
@Stateless
public class OrganizadorLogic {
    private static final Logger LOGGER = Logger.getLogger(OrganizadorLogic.class.getName());
    
    /**
     * Persistencia 
     */
    @Inject 
    OrganizadorPersistence persistence;
    
    
    /**
     * POST
     * Recibe un entity como parametro y llama a la persistencia para 
     * guardar el organizador en la tabla creandole un id 
     * @param entity
     * @return la misma entidad que recibio con el id asignado por la persistencia 
     * @throws BusinessLogicException 
     */
    public OrganizadorEntity createOrganizador (OrganizadorEntity entity ) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "incia la creacion del organizador");
        //Verifica que se cumpla la regla de negocio 
        if(persistence.findByUser(entity.getUsuario())!=null)
        {
            throw new BusinessLogicException("Ya existe una editorial con el nombre"+ entity.getNombre());
        }
        
        //Pone el objeto en la persistencia el cual le asigna un id
        
        persistence.create(entity);
        
        LOGGER.log(Level.INFO, "saliendo de crear organizador");
        
        //retorna la entidad con el id asignado 
        return entity;
    }
    
        /**
     *GET
     *Recibe el id de un organizador como paramentro y devuelve la entidad
     * si no existe retorna null
     * @param organizadorId
     * @return el organizador con ese id
     * 
     */
    public OrganizadorEntity getOrganizador (Long organizadorId)
    {
        LOGGER.log(Level.INFO, "Obtengo el organizador: "+ organizadorId);
        
        OrganizadorEntity entity =persistence.find(organizadorId);
        
        LOGGER.log(Level.INFO, "Salgo de obtener el organizador: "+ organizadorId);
        
        return entity;
        
    }
    
     /**
     *GET ALL
     *Retorna todos los organizadores 
     * @return todos los organizadores 
     * 
     */
    public List<OrganizadorEntity> getOrganizadores ()
    {
        LOGGER.log(Level.INFO, "Traigo todos los organizadores existentes");
        
        //Se llama a la persistencia
        return persistence.findAll();      
    }
    
    /**
     *PUT 
     *Actualiza el entity recibido como parametro 
     * @param entity. La entidad que se quiere actualizar
     * @return La entidad actualizada 
     * 
     */
    public OrganizadorEntity update(OrganizadorEntity entity)
    {
        LOGGER.log(Level.INFO, "Entrando a actualizar el organizador con el nombre: "+ entity.getNombre());
        entity=persistence.update(entity);
        LOGGER.log(Level.INFO, "saliendo de actualizar el organizador con el nombre: "+ entity.getNombre());
        return entity;
    }
    
    /**DELETE
     *Borra el entity con el id recibido como parametro 
     * @param organizadorId. Id del organizador a borrar.
     * 
     */
    public void delete (Long organizadorId)
    {
        LOGGER.log(Level.INFO, "Entrando a borrar el organizador");
        persistence.delete(organizadorId);
        LOGGER.log(Level.INFO, "Saliendo de borrar el organizador");
    }
}

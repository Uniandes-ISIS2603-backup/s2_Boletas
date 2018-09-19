/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.EspectaculoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Sebastian Rodriguez Beltran
 */

@Stateless
public class EspectaculoLogic
{
    private static final Logger  LOGGER = Logger.getLogger(EspectaculoLogic.class.getName());
    
    @Inject
    private EspectaculoPersistence persistence;
    
    
    /**
     * Metodo para crear un espectaculo, tiene conexion con la persistencia
     * @param espec El espectaculo a crear en la base de datos
     * @return El espectaculo creado, con un nuevo id asignado por la base de datos
     * @throws BusinessLogicException En caso de que ya exista 
     */
    public EspectaculoEntity createEntity(EspectaculoEntity espec) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia el proceso de crear un espectaculo");
        
        persistence.create(espec);
        
        LOGGER.log(Level.INFO, "Se creo el espectaculo satisfactoriamente");
        
        
        return espec;
    }
    
    /**
     * Metodo para encontrar todos los espectaculos que existen en el programa
     * @return 
     */
     public List<EspectaculoEntity> getEspectaculos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los espectaculos");
        List<EspectaculoEntity> espectaculos = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los espectaculos");
        return espectaculos;
    }
     
     
    /**
     * Obtener un espectaculo segun su Id
     * @param espectaculosId
     * @return 
     */
    public EspectaculoEntity getEspectaculo(Long espectaculosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el espectaculo con id = {0}", espectaculosId);
        EspectaculoEntity editorialEntity = persistence.find(espectaculosId);
        if (editorialEntity == null) {
            LOGGER.log(Level.SEVERE, "El espectaculo con el id = {0} no existe", espectaculosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el espectaculo con id = {0}", espectaculosId);
        return editorialEntity;
    } 
    
    /**
     * Metodo que busca un espectaculo con el id dado, y lo actualiza con los datos de espectaculoEntity
     * @param espectaculosId El espectaculo a buscar
     * @param espectaculoEntity Objeto tipo EspectaculoEntity que contiene los datos a actualizar 
     * @return El espectaculo entity con los datos actualizados
     */
    public EspectaculoEntity updateEspectaculo(Long espectaculosId, EspectaculoEntity espectaculoEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la editorial con id = {0}", espectaculosId);
        
        EspectaculoEntity newEntity = persistence.update(espectaculoEntity);
        
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la editorial con id = {0}", espectaculoEntity.getId());
        
        return newEntity;
    }
    
    
    /**
     * Metodo que elimina un espectaculo de la base de datos
     * @param espectaculosId Id del espectaculo a eliminar
     */
    public void deleteEspectaculo(Long espectaculosId) 
    {
        
        
        LOGGER.log(Level.INFO, "Inicia proceso de eliminar el espectaculo con id = {0}", espectaculosId);
        
       //Como al borrar el espectaculo tambien se borran las boletas, no se esta haciendo la verificacion 
       //de si tiene boletas asociadas
        
        persistence.delete(espectaculosId);
        
        LOGGER.log(Level.INFO, "Se termina el proceso de eliminar un espectaculo con id = {0}", espectaculosId);
        
        
    }
    
}

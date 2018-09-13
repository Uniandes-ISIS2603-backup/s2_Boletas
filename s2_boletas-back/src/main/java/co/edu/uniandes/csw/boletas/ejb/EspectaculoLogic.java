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
    
    public EspectaculoEntity createEntity(EspectaculoEntity espec) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia el proceso de crear un espectaculo");
        
        if(persistence.findByName(espec.getNombre()) != null)
        {
            throw new BusinessLogicException("La entidad con el nombre "+espec.getNombre()+" ya existe");
        }
        
        persistence.create(espec);
        
        LOGGER.log(Level.INFO, "Se creo el espectaculo satisfactoriamente");
        
        return espec;
    }
    
     public List<EspectaculoEntity> getEspectaculos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los espectaculos");
        List<EspectaculoEntity> espectaculos = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los espectaculos");
        return espectaculos;
    }
     
    public EspectaculoEntity getEspectaculo(Long espectaculosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el espectaculo con id = {0}", espectaculosId);
        EspectaculoEntity editorialEntity = persistence.find(espectaculosId);
        if (editorialEntity == null) {
            LOGGER.log(Level.SEVERE, "El espectaculo con el id = {0} no existe", espectaculosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el espectaculo con id = {0}", espectaculosId);
        return editorialEntity;
    } 
    
    
    public EspectaculoEntity updateEspectaculo(Long espectaculosId, EspectaculoEntity espectaculoEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la editorial con id = {0}", espectaculosId);
        
        EspectaculoEntity newEntity = persistence.update(espectaculoEntity);
        
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la editorial con id = {0}", espectaculoEntity.getId());
        
        return newEntity;
    }
    
    public void deleteEspectaculo(Long espectaculosId) throws BusinessLogicException
    {
        
        List<BoletaEntity> boletas = getEspectaculo(espectaculosId).getBoletas();
        
        if(boletas!= null && !boletas.isEmpty())
        {
            throw new BusinessLogicException("El espectaculo que se quiere eliminar tiene boletas asociadas");
        }
        
        persistence.delete(espectaculosId);
        
        
    }
    
}

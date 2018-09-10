/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.EspectaculoPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
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
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.LugarEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.LugarPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class SillaLugaresLogic {
    private static final Logger LOGGER = Logger.getLogger(SillaLugaresLogic.class.getName());
    
    @Inject
    private LugarPersistence lugarPersistence;
    
    /**
     * Método que retorna una lista de lugares con un número de sillas mayor o igual al dado por parámetro
     * @param numSillas
     * @return
     * @throws BusinessLogicException 
     */
    public List<LugarEntity> getLugaresByNumSillas(Integer numSillas)throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Iniciando proceso de dar lugares con un número de sillas mayor o igual a: " + numSillas);
        if(numSillas <= 0)
            throw new BusinessLogicException("El número de sillas introducido no es válido.");
        LOGGER.log(Level.INFO, "Terminado proceso de dar lugares con un número de sillas mayor o igual a: " + numSillas);
        return lugarPersistence.findByNSillas(numSillas);
    }
}

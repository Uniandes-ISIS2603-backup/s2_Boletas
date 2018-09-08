/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.BoletaPersistence;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class BoletaLogic {
    private static final Logger LOGGER = Logger.getLogger(BoletaLogic.class.getName());
    @Inject
    private BoletaPersistence persistence;
    
   /* public BoletaEntity createBoleta(BoletaEntity boleta) throws BusinessLogicException
    {
        
    }
    */
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.BoletaPersistence;
import co.edu.uniandes.csw.boletas.persistence.EspectaculoPersistence;
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
public class EspectaculoBoletasLogic {
    
    private static final Logger LOGGER = Logger.getLogger(EspectaculoBoletasLogic.class.getName());
    
    @Inject
    private EspectaculoPersistence espectaculoPersistence;
    
    @Inject
    private BoletaPersistence boletaPersistence;
    
    public BoletaEntity addBoleta(Long boletasId, Long espectaculosId)
    {
       LOGGER.log(Level.INFO, "Inicia el proceso de agregarle una boleta a el espectaculo con id = {0}", espectaculosId); 
       EspectaculoEntity espectaculoEntity = espectaculoPersistence.find(espectaculosId);
       BoletaEntity boletaEntity = boletaPersistence.find(boletasId);
       boletaEntity.setEspectaculo(espectaculoEntity);
       LOGGER.log(Level.INFO, "Finaliza el proceso de agregarle una boleta a el espectaculo con id = {0}", espectaculosId); 
       return boletaEntity;
    }
    
    public List<BoletaEntity> getBoletas(Long espectaculosId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las boletas asociadas a el espectaculo con id = {0}", espectaculosId);
        return espectaculoPersistence.find(espectaculosId).getBoletas();
    }
    
    public BoletaEntity getBoleta(Long espectaculosId,Long boletasId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la boleta con id = {0} del espectaculo con id = " + espectaculosId, boletasId);
        List<BoletaEntity> boletas = espectaculoPersistence.find(espectaculosId).getBoletas();
        BoletaEntity boletaEntity = boletaPersistence.find(boletasId);
        int index = boletas.indexOf(boletaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la boleta con id = {0} del espectaculo con id = " + espectaculosId, boletasId);
        if (index >= 0) {
            return boletas.get(index);
        }
        throw new BusinessLogicException("El libro no está asociado a la editorial");
    }
    
    public List<BoletaEntity> replaceBoletas(Long espectaculosId, List<BoletaEntity> boletas)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el espectaculo con Id = {0}", espectaculosId);
        EspectaculoEntity espectaculoEntity = espectaculoPersistence.find(espectaculosId);
        List<BoletaEntity> boletaList = boletaPersistence.findAll();
        for(BoletaEntity boleta: boletaList)
        {
            if(boletas.contains(boleta))
            {
                boleta.setEspectaculo(espectaculoEntity);
            }
            else if(boleta.getEspectaculo() != null && boleta.getEspectaculo().equals(espectaculoEntity))
            {
                boleta.setEspectaculo(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el espectaculo con id = {0}", espectaculosId);
        return boletas;
        
    }
    
}

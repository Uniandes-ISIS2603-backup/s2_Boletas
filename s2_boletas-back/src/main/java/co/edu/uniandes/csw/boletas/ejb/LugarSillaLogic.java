/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.LugarEntity;
import co.edu.uniandes.csw.boletas.entities.SillaEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.LugarPersistence;
import co.edu.uniandes.csw.boletas.persistence.SillaPersistence;
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
public class LugarSillaLogic {
    private static final Logger LOGGER = Logger.getLogger(LugarSillaLogic.class.getName());
    
    @Inject
    private LugarPersistence lugarPersistence;
    
    @Inject
    private SillaPersistence sillaPersistence;
    
    public SillaEntity addSilla(long lugarId, long sillaId)
    {
         LOGGER.log(Level.INFO, "Inicia proceso de asociarle una silla al lugar con id = {0}", lugarId);
         LugarEntity lugarEntity = lugarPersistence.find(lugarId);
         SillaEntity sillaEntity = sillaPersistence.find(sillaId);
         
         lugarEntity.setSilla(sillaEntity);
         sillaEntity.setLugar(lugarEntity);
         
         LOGGER.log(Level.INFO, "Terminan proceso de asociarle una silla al lugar con id = {0}", lugarId);
         
         
        return sillaPersistence.find(sillaId);
    }
    
    public List<SillaEntity> getSillas(long lugarId)throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de retornar sillas pertenecientes al lugar con id = {0}", lugarId);
        if(lugarPersistence.find(lugarId)==null)
            throw new BusinessLogicException("El lugar con el id dado por parámetro no existe.");
        List<SillaEntity> sillas = lugarPersistence.find(lugarId).getSillas();
        LOGGER.log(Level.INFO, "Termina proceso de retornar sillas pertenecientes al lugar con id = {0}", lugarId);
        return sillas;
    }
}

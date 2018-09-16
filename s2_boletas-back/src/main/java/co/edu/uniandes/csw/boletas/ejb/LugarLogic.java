/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.LugarEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.LugarPersistence;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @ja.amortegui10 estudiante
 */
@Stateless
public class LugarLogic {
    
    private static final Logger LOGGER  = Logger.getLogger(LugarLogic.class.getName());
    
    @Inject
    private LugarPersistence persistence;
    
    public LugarEntity getLugarByName(String name)
    {
        return persistence.findByName(name);
    }
    
    public LugarEntity createLugar(LugarEntity entity)throws BusinessLogicException
    {
        LugarEntity alreadyExists = getLugarByName(entity.getNombre());
        if(alreadyExists != null)
            throw new BusinessLogicException("Ya existe un lugar con el nombre dado.");
        
        return persistence.create(entity);
    }
    
    public List<LugarEntity> getLugares()
    {
        return persistence.findAll();
    }
    
    public LugarEntity getLugarById(Long lugarId)
    {
        LOGGER.log(Level.INFO, "Iniciando proceso de retornar lugar por id.");
        LugarEntity lugar = persistence.find(lugarId);
        LOGGER.log(Level.INFO, "Terminando proceso de retornar lugar por id.");
        return lugar;
    }
    
    public LugarEntity updateLugar(Long lugarId, LugarEntity lugarAModificar)throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Iniciando proceso de modificar lugar.");
        //Falta implementar Reglas Negocio.
        if(getLugarById(lugarId) == null)
            throw new BusinessLogicException("No existe el elemento lugar que se intenta modificar.");
        LugarEntity modificado = persistence.update(lugarAModificar);
        LOGGER.log(Level.INFO, "Terminando proceso de modificar lugar.");
        return modificado;
    }
    
    public LugarEntity deleteLugar(Long lugarId)throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Iniciando proceso de remover lugar.");
        LugarEntity lugarAEliminar = getLugarById(lugarId);
        if(lugarAEliminar == null)
            throw new BusinessLogicException("El elemento lugar que se intenta remover no existe en el sistema.");
        //Falta implementar reglas negocio.
        persistence.delete(lugarId);
        LOGGER.log(Level.INFO,"Terminando proceso de remover lugar.");
        return lugarAEliminar;
    }
}

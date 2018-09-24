/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.SillaEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.SillaPersistence;
import java.util.logging.Logger;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author ja.amortegui10
 */
@Stateless
public class SillaLogic {
    
    private static final Logger LOGGER = Logger.getLogger(SillaLogic.class.getName());
    
    @Inject
    private SillaPersistence persistence;
    
    /**
     * Método que retorna una entidad Silla según su id.
     * @param id
     * @return 
     */
    public SillaEntity getSillaById(Long id)
    {
        return persistence.find(id);
    }
    
    /**
     * Método que retorna una entidad silla según su número.
     * @param numero
     * @param lugarId
     * @return 
     */
    public SillaEntity getSillaByNumero(String numero, Long lugarId)
    {
        return persistence.findByNumeroAndLugar(numero, lugarId);
    }
    
    /**
     * Método que crea una entidad silla en la base de datos.
     * @param entity
     * @return
     * @throws BusinessLogicException 
     */
    public SillaEntity createSilla(SillaEntity entity) throws BusinessLogicException
    {
        SillaEntity alreadyExists = null;
        if(entity.getLugar() != null)
             alreadyExists = getSillaByNumero(entity.getNumero(), entity.getLugar().getId());
        if(alreadyExists != null)
            throw new BusinessLogicException("Ya existe una silla con ese número en el lugar especificado.");
        String tipoSilla = entity.getTipo();
        if(tipoSilla == null)
            throw new BusinessLogicException("La silla debe tener un tipo.");
        if(!(tipoSilla.equals("palcos") || tipoSilla.equals("general") || tipoSilla.equals("vip")))
            throw new BusinessLogicException("El tipo de silla no concuerda con las reglas del negocio del sistema.");
        return persistence.create(entity);
       
    }
    
    /**
     * Método que retorna una lista con todas  las entidades Silla en la base de datos.
     * @return 
     */
    public List<SillaEntity> getSillas()
    {
        return persistence.findAll();
    }
    
    /**
     * Método que altera una entidad Silla.
     * @param sillaId
     * @param sillaAModificar
     * @return
     * @throws BusinessLogicException 
     */
    public SillaEntity updateSilla(Long sillaId, SillaEntity sillaAModificar)throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicando proceso de modificar una silla.");
        if(getSillaById(sillaId) == null)
            throw new BusinessLogicException("El elemento silla que intenta modificar no existe en el sistema.");
        SillaEntity modificada = persistence.update(sillaAModificar);
        LOGGER.log(Level.INFO, "Terminando proceso de modificar una silla.");
        return modificada;
    }
    
    /**
     * Método que elimina una entidad Silla.
     * @param sillaId
     * @return
     * @throws BusinessLogicException 
     */
    public SillaEntity deleteSilla(Long sillaId)throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Iniciando proceso de eliminar silla.");
        SillaEntity sillaAEliminar = getSillaById(sillaId);
        //Falta implementar reglas negocio.
        if(sillaAEliminar == null)
            throw new BusinessLogicException("El elemento que intenta remover no existe en el sistema.");
        persistence.delete(sillaId);
        LOGGER.log(Level.INFO, "Terminando proceso de eliminar silla.");
        return sillaAEliminar;
    }
}

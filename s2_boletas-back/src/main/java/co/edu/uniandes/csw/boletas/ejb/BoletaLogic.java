/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.BoletaPersistence;
import co.edu.uniandes.csw.boletas.persistence.EspectaculoPersistence;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *Clase que implementa la conexion con la persistencia para la entidad de boleta
 * 
 * @author Diego Camacho
 */
@Stateless
public class BoletaLogic {
    private static final Logger LOGGER = Logger.getLogger(BoletaLogic.class.getName());
    @Inject
    private BoletaPersistence persistence;
    
    @Inject
    
    private EspectaculoPersistence espectaculoPersistence;
    
    /**
     * Guardar una nueva boleta
     * 
     * @param boleta la entidad de tipo de boleta de la nueva boleta a persistir.
     * @return La entidad luego de persistirla
     * @throws BusinessLogicException Si boleta no tiene un espectaculo asociado
     */
    public BoletaEntity createBoleta(BoletaEntity boleta) throws BusinessLogicException
    {
        
        LOGGER.log(Level.INFO, "Inicia el proceso de la creación de la boleta");
        if(boleta.getEspectaculo()==null|| espectaculoPersistence.find(boleta.getEspectaculo().getId())==null)
        {
            throw new BusinessLogicException("El espectaculo es invalido");
        }
        
        if(boleta.getFecha()!=null && espectaculoPersistence.find(boleta.getEspectaculo().getId()).getFecha()!=null &&(boleta.getFecha().compareTo(espectaculoPersistence.find(boleta.getEspectaculo().getId()).getFecha())!=0))
        {
            throw new BusinessLogicException("La fecha de la boleta debe corresponder con la prueba del espectaculo");
        }
        persistence.create(boleta);
        LOGGER.log(Level.INFO, "Termina proceso de la creación de la boleta");
        return boleta;
    }
    
    /**
     * Elimina una boleta por id
     * 
     * @param boletaId El id de la boleta a eliminar
     */
    public void deleteBoleta(Long boletaId)
    {
        LOGGER.log(Level.INFO, "Inicia el proceso de la eliminación de una boleta con id = {0}", boletaId);
        persistence.delete(boletaId);
        LOGGER.log(Level.INFO, "Termina proceso de eliminar una boleta con id = {0}", boletaId);
    }
    
    /**
     * Devuelve todas las boletas que hay en la base de datos
     * 
     * @return Lista de entidades de tipo boleta
     */
    public List<BoletaEntity> getBoletas() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las boletas");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<BoletaEntity> boletas = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las boletas");
        return boletas;
    }
    
    /**
     * Busca una boleta por un id
     * @param boletaId El id de la boleta a buscar
     * @return La boleta encontrada, null si no la encuentra.
     */
    public BoletaEntity getBoleta(Long boletaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la boleta con id = {0}", boletaId);
        BoletaEntity boletaEntity = persistence.find(boletaId);
        if (boletaEntity == null) {
            LOGGER.log(Level.SEVERE, "La boleta con el id = {0} no existe", boletaId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la boleta con id = {0}", boletaId);
        return boletaEntity;
    }
    
    /**
     * Actualizar una boleta por un id
     * 
     * @param boletasId El id de la boleta a actualizar
     * @param boletaEntity La entidad de la boleta con los cambios deseados
     * @return La entidad de la boleta luego de actualizarla
     */
    public BoletaEntity updateBoleta(Long boletasId, BoletaEntity boletaEntity)
    {
      LOGGER.log(Level.INFO, "Inicia proceso de actualizar la boleta con id = {0}", boletasId);
        BoletaEntity newEntity = persistence.update(boletaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la boleta con id = {0}", boletaEntity.getId());
        return newEntity;  
    }
    
}

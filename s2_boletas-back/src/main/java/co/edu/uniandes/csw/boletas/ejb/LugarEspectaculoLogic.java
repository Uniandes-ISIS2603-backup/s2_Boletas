    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;


import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import co.edu.uniandes.csw.boletas.entities.LugarEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.EspectaculoPersistence;
import co.edu.uniandes.csw.boletas.persistence.LugarPersistence;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;


/**
 *
 * @author ja.amortegui10 y Vilma Tirado
 */

@Stateless
public class LugarEspectaculoLogic {
     
    private static final Logger LOGGER  = Logger.getLogger(LugarEspectaculoLogic.class.getName());
    
    @Inject
    private LugarPersistence lugarPersistence;
    @Inject 
    private EspectaculoPersistence espectaculoPersistence;
    
    /**
     * Agregar un espectaculo a la lugar
     *
     * @param espectaculosId El id libro a guardar
     * @param lugarsId El id de la lugar en la cual se va a guardar el
     * libro.
     * @return El libro creado.
     */
    public EspectaculoEntity addEspectaculo(Long espectaculosId, Long lugarsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un libro a la lugar con id = {0}", lugarsId);
        LugarEntity lugarEntity = lugarPersistence.find(lugarsId);
        EspectaculoEntity espectaculoEntity = espectaculoPersistence.find(espectaculosId);
        espectaculoEntity.setLugar(lugarEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un libro a la lugar con id = {0}", lugarsId);
        return espectaculoEntity;
    }

    /**
     * Retorna todos los espectaculos asociados a una lugar
     *
     * @param lugarsId El ID de la lugar buscada
     * @return La lista de libros de la lugar
     */
    public List<EspectaculoEntity> getEspectaculos(Long lugarsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los libros asociados a la lugar con id = {0}", lugarsId);
        return lugarPersistence.find(lugarsId).getEspectaculos();
    }

    /**
     * Retorna un espectaculo asociado a una lugar
     *
     * @param lugarsId El id de la lugar a buscar.
     * @param espectaculosId El id del libro a buscar
     * @return El libro encontrado dentro de la lugar.
     * @throws BusinessLogicException Si el libro no se encuentra en la
     * lugar
     */
    public EspectaculoEntity getEspectaculo(Long lugarsId, Long espectaculosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el libro con id = {0} de la lugar con id = " + lugarsId, espectaculosId);
        List<EspectaculoEntity> espectaculos = lugarPersistence.find(lugarsId).getEspectaculos();
        EspectaculoEntity espectaculoEntity = espectaculoPersistence.find(espectaculosId);
        int index = espectaculos.indexOf(espectaculoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el libro con id = {0} de la lugar con id = " + lugarsId, espectaculosId);
        if (index >= 0) {
            return espectaculos.get(index);
        }
        throw new BusinessLogicException("El libro no está asociado a la lugar");
    }

    /**
     * Remplazar espectaculos de una lugar
     *
     * @param espectaculos Lista de libros que serán los de la lugar.
     * @param lugarsId El id de la lugar que se quiere actualizar.
     * @return La lista de libros actualizada.
     */
    public List<EspectaculoEntity> replaceEspectaculos(Long lugarsId, List<EspectaculoEntity> espectaculos) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la lugar con id = {0}", lugarsId);
        LugarEntity lugarEntity = lugarPersistence.find(lugarsId);
        List<EspectaculoEntity> espectaculoList = espectaculoPersistence.findAll();
        for (EspectaculoEntity espectaculo : espectaculoList) {
            if (espectaculos.contains(espectaculo)) {
                espectaculo.setLugar(lugarEntity);
            } else if (espectaculo.darLugar() != null && espectaculo.darLugar().equals(lugarEntity)) {
                espectaculo.setLugar(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la lugar con id = {0}", lugarsId);
        return espectaculos;
    }

     /**
     * Método que retorna los lugares disponibles en la fecha dada por parámetro.
     * @param fecha
     * @return
     * @throws BusinessLogicException 
     */
    public List<LugarEntity> getLugaresDisponiblles(Date fecha)throws BusinessLogicException
    {
        List<LugarEntity> lugares = lugarPersistence.findAll();
        List<LugarEntity> lugaresDisponibles = new ArrayList<>();
        for(LugarEntity actual: lugares)
            if(estaDisponible(fecha, actual))
                lugaresDisponibles.add(actual);
        return lugaresDisponibles;
    }
    
    
    /**
     * Método que verifica si un lugar específico está disponible en una fecha dada.
     * @param fecha
     * @param lugarId
     * @return
     * @throws BusinessLogicException 
     */
    public boolean estaDisponible(Date fecha, LugarEntity lugar)throws BusinessLogicException
    {
         if(lugar == null)
             throw new BusinessLogicException("Ocurrió un problema con la lista de lugares..");
         
         List<EspectaculoEntity> espectaculos = lugar.getEspectaculos();
         for(EspectaculoEntity espectaculoActual: espectaculos)
             if(espectaculoActual.getFecha().equals(fecha))// falta implementar la duración de cada espectáculo <- <- <- <- 
                 return false;
        return true;
    }

}

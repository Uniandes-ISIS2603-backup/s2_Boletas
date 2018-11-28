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
 * @author estudiante
 */
@Stateless
public class LugarEspectaculoLogic {
     
    private static final Logger LOGGER  = Logger.getLogger(LugarEspectaculoLogic.class.getName());
    
    @Inject
    private LugarPersistence lugarPersistence;
    @Inject 
    private EspectaculoPersistence espectaculoPersistence;
    
    public LugarEntity addEspectaculo(Long lugarId, Long espectaculoId)throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de agregar espectaculo y enlazarlo con un lugar.");
        LugarEntity lugar = lugarPersistence.find(lugarId);
        EspectaculoEntity espectaculo = espectaculoPersistence.find(espectaculoId);
        if(lugar == null )
            throw new BusinessLogicException("El lugar con el id dado no existe.");
        if(espectaculo == null)
            throw new BusinessLogicException("El espectaculo con el id dado no existe.");
        List<EspectaculoEntity> espectaculos = lugar.getEspectaculos();
        if(espectaculos == null)
            espectaculos = new ArrayList<EspectaculoEntity>();
        espectaculos.add(espectaculo);
        espectaculo.setLugar(lugar);
        return lugar;
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
        List<LugarEntity> lugaresDisponibles = new ArrayList<LugarEntity>();
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

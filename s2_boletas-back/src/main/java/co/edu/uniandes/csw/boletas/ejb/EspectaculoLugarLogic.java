/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import co.edu.uniandes.csw.boletas.entities.LugarEntity;
import co.edu.uniandes.csw.boletas.persistence.EspectaculoPersistence;
import co.edu.uniandes.csw.boletas.persistence.LugarPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Vilma Tirado y Jhonatan amortegui
 * 
 */
@Stateless
public class EspectaculoLugarLogic {
        private static final Logger LOGGER = Logger.getLogger(EspectaculoLugarLogic.class.getName());

    @Inject
    private EspectaculoPersistence espectaculoPersistence;

    @Inject
    private LugarPersistence lugarPersistence;

    /**
     * Remplazar la lugar de un espectaculo.
     *
     * @param espectaculosId id del libro que se quiere actualizar.
     * @param lugarsId El id de la lugar que se será del libro.
     * @return el nuevo libro.
     */
    public EspectaculoEntity replaceLugar(Long espectaculosId, Long lugarsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar libro con id = {0}", espectaculosId);
        LugarEntity lugarEntity = lugarPersistence.find(lugarsId);
        EspectaculoEntity espectaculoEntity = espectaculoPersistence.find(espectaculosId);
        espectaculoEntity.setLugar(lugarEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar libro con id = {0}", espectaculoEntity.getId());
        return espectaculoEntity;
    }

    /**
     * Borrar un espectaculo de una lugar. Este metodo se utiliza para borrar la
     * relacion de un libro.
     *
     * @param espectaculosId El libro que se desea borrar de la lugar.
     */
    public void removeLugar(Long espectaculosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la Lugar del libro con id = {0}", espectaculosId);
        EspectaculoEntity espectaculoEntity = espectaculoPersistence.find(espectaculosId);
        LugarEntity lugarEntity = lugarPersistence.find(espectaculoEntity.getLugar().getId());
        espectaculoEntity.setLugar(null);
        lugarEntity.getEspectaculos().remove(espectaculoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la Lugar del libro con id = {0}", espectaculoEntity.getId());
    }
}
   

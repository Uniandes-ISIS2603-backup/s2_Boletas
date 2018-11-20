/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import co.edu.uniandes.csw.boletas.entities.OrganizadorEntity;
import co.edu.uniandes.csw.boletas.persistence.EspectaculoPersistence;
import co.edu.uniandes.csw.boletas.persistence.OrganizadorPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author Vilma Tirado Gomez
 */
public class EspectaculoOrganizadorLogic {
    
        private static final Logger LOGGER = Logger.getLogger(EspectaculoOrganizadorLogic.class.getName());

    @Inject
    private EspectaculoPersistence espectaculoPersistence;

    @Inject
    private OrganizadorPersistence organizadorPersistence;

    /**
     * Remplazar la organizador de un espectaculo.
     *
     * @param espectaculosId id del libro que se quiere actualizar.
     * @param organizadorsId El id de la organizador que se será del libro.
     * @return el nuevo libro.
     */
    public EspectaculoEntity replaceOrganizador(Long espectaculosId, Long organizadorsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar libro con id = {0}", espectaculosId);
        OrganizadorEntity organizadorEntity = organizadorPersistence.find(organizadorsId);
        EspectaculoEntity espectaculoEntity = espectaculoPersistence.find(espectaculosId);
        espectaculoEntity.setOrganizador(organizadorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar libro con id = {0}", espectaculoEntity.getId());
        return espectaculoEntity;
    }

    /**
     * Borrar un espectaculo de una organizador. Este metodo se utiliza para borrar la
     * relacion de un libro.
     *
     * @param espectaculosId El libro que se desea borrar de la organizador.
     */
    public void removeOrganizador(Long espectaculosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la Organizador del libro con id = {0}", espectaculosId);
        EspectaculoEntity espectaculoEntity = espectaculoPersistence.find(espectaculosId);
        OrganizadorEntity organizadorEntity = organizadorPersistence.find(espectaculoEntity.getOrganizador().getId());
        espectaculoEntity.setOrganizador(null);
        organizadorEntity.getEspectaculos().remove(espectaculoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la Organizador del libro con id = {0}", espectaculoEntity.getId());
    }
}


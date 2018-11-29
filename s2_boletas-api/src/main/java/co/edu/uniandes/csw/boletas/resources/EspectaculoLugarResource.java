/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.biblioteca.mappers.WebApplicationExceptionMapper;
import co.edu.uniandes.csw.boletas.dtos.EspectaculoDetailDTO;
import co.edu.uniandes.csw.boletas.ejb.EspectaculoLugarLogic;
import co.edu.uniandes.csw.boletas.dtos.LugarDTO;
import co.edu.uniandes.csw.boletas.ejb.EspectaculoLogic;
import co.edu.uniandes.csw.boletas.ejb.LugarLogic;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Vilma Tirado y Jhonatan Amortegui 
 */
public class EspectaculoLugarResource {
        private static final Logger LOGGER = Logger.getLogger(EspectaculoLugarResource.class.getName());

    @Inject
    private EspectaculoLogic espectaculoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private EspectaculoLugarLogic espectaculoLugarLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private LugarLogic lugarLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Remplaza la instancia de Lugar asociada a un Espectaculo.
     *
     * @param espectaculosId Identificador del libro que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param lugar La lugar que se será del libro.
     * @return JSON {@link EspectaculoDetailDTO} - El arreglo de libros guardado en la
     * lugar.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la lugar o el
     * libro.
     */
    @PUT
    public EspectaculoDetailDTO replaceLugar(@PathParam("espectaculosId") Long espectaculosId, LugarDTO lugar) {
        LOGGER.log(Level.INFO, "EspectaculoLugarResource replaceLugar: input: espectaculosId{0} , Lugar:{1}", new Object[]{espectaculosId, lugar});
        if (espectaculoLogic.getEspectaculo(espectaculosId) == null) {
            throw new WebApplicationException("El recurso /espectaculos/" + espectaculosId + " no existe.", 404);
        }
        if (lugarLogic.getLugarById(lugar.getId()) == null) {
            throw new WebApplicationException("El recurso /lugars/" + lugar.getId() + " no existe.", 404);
        }
        EspectaculoDetailDTO espectaculoDetailDTO = new EspectaculoDetailDTO(espectaculoLugarLogic.replaceLugar(espectaculosId, lugar.getId()));
        LOGGER.log(Level.INFO, "EspectaculoLugarResource replaceLugar: output: {0}", espectaculoDetailDTO);
        return espectaculoDetailDTO;
    }
}
    

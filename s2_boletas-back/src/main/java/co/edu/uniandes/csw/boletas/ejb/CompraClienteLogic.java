/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.ClienteEntity;
import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.ClientePersistence;
import co.edu.uniandes.csw.boletas.persistence.CompraPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author Gabriel Hamilton Vilma Tirado
 */
public class CompraClienteLogic {
    private static final Logger LOGGER = Logger.getLogger(CompraClienteLogic.class.getName());

    @Inject
    private CompraPersistence compraPersistence;

    @Inject
    private ClientePersistence clientePersistence;

    /**
     * Remplazar la cliente de un compra.
     *
     * @param comprasId id del libro que se quiere actualizar.
     * @param clientesId El id de la cliente que se será del libro.
     * @return el nuevo libro.
     */
    public CompraEntity replaceCliente(Long comprasId, Long clientesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar libro con id = {0}", comprasId);
        ClienteEntity clienteEntity = clientePersistence.find(clientesId);
        CompraEntity compraEntity = compraPersistence.find(comprasId);
        compraEntity.setCliente(clienteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar libro con id = {0}", compraEntity.getId());
        return compraEntity;
    }

    /**
     * Borrar un compra de una cliente. Este metodo se utiliza para borrar la
     * relacion de un libro.
     *
     * @param comprasId El libro que se desea borrar de la cliente.
     */
    public void removeCliente(Long comprasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la Cliente del libro con id = {0}", comprasId);
        CompraEntity compraEntity = compraPersistence.find(comprasId);
        ClienteEntity clienteEntity = clientePersistence.find(compraEntity.getCliente().getId());
        compraEntity.setCliente(null);
        clienteEntity.getCompras().remove(compraEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la Cliente del libro con id = {0}", compraEntity.getId());
    }
    
}

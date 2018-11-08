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
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Gabriel Hamilton Vilma Tirado
 */
@Stateless
public class CompraClienteLogic {
    private static final Logger LOGGER = Logger.getLogger(CompraClienteLogic.class.getName());

    @Inject
    private CompraPersistence compraPersistence;

    @Inject
    private ClientePersistence clientePersistence;

    /**
     * Remplazar la compra de un cliente.
     *
     * @param comprasId id de la compra que se quiere actualizar.
     * @param clientesId El id del cliente que del cual será la compra.
     * @return la nueva compra.
     */
    public CompraEntity replaceCliente(Long comprasId, Long clientesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la compra con id = {0}", comprasId);
        ClienteEntity clienteEntity = clientePersistence.find(clientesId);
        CompraEntity compraEntity = compraPersistence.find(comprasId);
        compraEntity.setCliente(clienteEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la compra con id = {0}", compraEntity.getId());
        return compraEntity;
    }

    /**
     * Borrar un compra de un cliente. Este metodo se utiliza para borrar la
     * relacion de una compra.
     *
     * @param comprasId La compra que se desea borrar del cliente.
     */
    public void removeCliente(Long comprasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el Cliente de la compra con id = {0}", comprasId);
        CompraEntity compraEntity = compraPersistence.find(comprasId);
        System.out.println("LLEGO CON "+compraEntity.getCliente().getId() );
        ClienteEntity clienteEntity = clientePersistence.find(compraEntity.getCliente().getId());
        System.out.println("PASO POR AQUI");
        compraEntity.setCliente(null);
        System.out.println(compraEntity.getCliente()+ " SOY ??");
       clienteEntity.getCompras().remove(compraEntity);
        System.out.println(compraEntity.getCliente()+ " SIGO ??");
       LOGGER.log(Level.INFO, "Termina proceso de borrar el Cliente de la compra con id = {0}", compraEntity.getId());
    }
    
}

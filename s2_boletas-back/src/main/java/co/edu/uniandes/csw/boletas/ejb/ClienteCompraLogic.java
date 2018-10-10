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
 * @author Vilma Tirado y Gabriel Hamilton
 */
public class ClienteCompraLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ClienteCompraLogic.class.getName());
    
    //Injeccion de dependencias de la persistencia 
    @Inject 
    private ClientePersistence clientePersistence;
    
    @Inject 
    private CompraPersistence compraPersistence;
    
       /**
     * Asocia una compra  existente a un Cliente existente
     *
     * @param clienteId Identificador de la instancia de Cliente
     * @param compraId Identificador de la instancia de compra
     * @return Instancia de Compra Entity que fue asociada a compra
     */
    public CompraEntity addCompra (Long compraId, Long clienteId)
    {
        //Busca la compra con el id dado 
        CompraEntity compra= compraPersistence.find(compraId);
        
        //Busca el cliente con el id dado
        ClienteEntity cliente= clientePersistence.find(clienteId);
        
        //Asocia a la lista de compras del cliente la compra
        cliente.getCompras().add(compra);
        
        return compra;
    }
    
        /**
     * Obtiene una instancia de CompraEntity asociada a una instancia de Cliente
     *
     * @param clientesId Identificador de la instancia de Book
     * @param compraId Identificador de la instancia de Compra
     * @return La entidad de la compra asociada al cliente
     * @throws BusinessLogicException Si el libro no se encuentra en la
     * editorial
     */
    public CompraEntity getCompra(Long clientesId, Long compraId)throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar una compra del cliente con id = {0}", clientesId);
        List<CompraEntity> compras = clientePersistence.find(clientesId).getCompras();
        CompraEntity compraEntity = compraPersistence.find(compraId);
        int index = compras.indexOf(compraEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar un compra del cliente con id = {0}", clientesId);
        if (index >= 0) {
            return compras.get(index);
        }
        throw new BusinessLogicException("La compra no está asociada al cliente");
    }
      
            /**
     * Remplaza las instancias de compra  asociadas a una instancia de Cliente
     *
     * @param clienteId Identificador de la instancia de Cliente
     * @param list Colección de instancias de CompraEntity  a asociar a instancia
     * de cliente
     * @return Nueva colección de CompraEntity asociada a la instancia de Cliente
     */
            public List<CompraEntity> updateCompras(Long clienteId, List<CompraEntity> listaNueva )
            {
                LOGGER.log(Level.INFO, "Inicia proceso de actualizar las compras del cliente con id = {0}", clienteId);
                ClienteEntity clienteEntity= clientePersistence.find(clienteId);
                List<CompraEntity> compraList = compraPersistence.findAll();
                for (CompraEntity compra : compraList) {
                        if (listaNueva.contains(compra)) {
                        compra.setCliente(clienteEntity);
                        } else if (compra.getCliente() != null && compra.getCliente().equals(clienteEntity)) {
                        compra.setCliente(null);
                        }
                }
                clienteEntity.setCompras(listaNueva);
                LOGGER.log(Level.INFO, "Termina proceso de actualizar las compras del cliente con id = {0}", clienteId);
                return listaNueva;
            }
               
            
              
          /**
     * Remplazar el cliente de unas compras
     *
     * @param compras  Lista de compras  que serán las del cliente.
     * @param clienteId  El id del cliente que se quiere actualizar.
     * @return La lista de compras actualizados.
     */
      
      public List<CompraEntity> updateClientesCompras (List<CompraEntity> compras,Long clienteId)
      {
          ClienteEntity clienteEntity= clientePersistence.find(clienteId);
          List<CompraEntity> listaCompras= compraPersistence.findAll();
          for (CompraEntity comp: listaCompras)
          {
              if (compras.contains(comp))
              {
                  comp.setCliente(clienteEntity);
              }
              else if (comp.getCliente()!=null && comp.getCliente().equals(clienteEntity)  )
              {
                  comp.setCliente(null);
              }
          }
          return compras;
      }
            
      
    /**
     * Retorna todas las compras asociadas a un cliente
     *
     * @param cliente El ID del cliente buscado
     * @return La lista de compras del cliente
     */
    public List<CompraEntity> getCompras(Long clienteId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las compras asociadas al cliente con id = {0}", clienteId);
        return clientePersistence.find(clienteId).getCompras();
    }
            
     /**
     * Desasocia una compra existente de un cliente existente
     *
     * @param clienteId Identificador de la instancia de Cliente
     * @param compraId Identificador de la instancia de Compra
     */
    public void removeCompra(Long clienteId, Long compraId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar una compra del cliente con id = {0}", clienteId);
        ClienteEntity clienteEntity = clientePersistence.find(clienteId);
        CompraEntity compraEntity = compraPersistence.find(compraId);
        compraEntity.setCliente(null);
        List<CompraEntity> nuevaLista = clienteEntity.getCompras();
        nuevaLista.remove(compraEntity);
        updateCompras(clienteId, nuevaLista);
        LOGGER.log(Level.INFO, "Termina proceso de borrar una compra del cliente con id = {0}", clienteId);
    }     
            
            
    
    
}

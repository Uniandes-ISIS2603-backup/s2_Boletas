/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.persistence;

import co.edu.uniandes.csw.boletas.entities.ClienteEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Vilma Marcela Tirado Gomez
 */
@Stateless
public class ClientePersistence {
    private static final Logger LOGGER = Logger.getLogger(ClientePersistence.class.getName());

     @PersistenceContext(unitName = "DnsPU")
    protected EntityManager em;
     
     public ClienteEntity create( ClienteEntity clienteEntity)
{
  LOGGER.log(Level.INFO, "Creando un cliente nuevo");

em.persist(clienteEntity);
LOGGER.log(Level.INFO, "Saliendo de crear un cliente nuevo");
return clienteEntity;
}
     
     public List<ClienteEntity> findAll(){
         LOGGER.log(Level.INFO, "Buscando todos los clientes");
         TypedQuery query =em.createQuery("Select all clientes" ,ClienteEntity.class);
         return query.getResultList();
     }
     
     public ClienteEntity find (Long clienteId){
         LOGGER.log(Level.INFO, "Consultando cliente con id={0}", clienteId);
         return em.find(ClienteEntity.class, clienteId);
     }
     
     public ClienteEntity update (ClienteEntity clienteEntity ){
         LOGGER.log(Level.INFO, "Actualizando el cliente con el id ={0}", clienteEntity.getId());
         LOGGER.log(Level.INFO, "Saliendo de actualizar el cliente con el id ={0}", clienteEntity.getId());
         return em.merge(clienteEntity);
     }
     
     
     public void delete (Long clienteId ){
         LOGGER.log(Level.INFO, "Borrando el cliente con el id ={0}", clienteId);
         ClienteEntity entity = em.find(ClienteEntity.class, clienteId);
         em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar  el cliente con el id ={0}", clienteId);
     }
    
}



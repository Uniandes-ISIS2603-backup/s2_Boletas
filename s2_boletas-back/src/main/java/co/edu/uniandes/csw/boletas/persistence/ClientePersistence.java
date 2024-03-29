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

    //Entity manager
     @PersistenceContext(unitName = "DnsPU")
    protected EntityManager em;
     
     
     /**
      * PERSIST persiste un objeto en la base de datos 
      * @param clienteEntity
      * @return la entidad del cliente con el id asignado 
      */
     public ClienteEntity create( ClienteEntity clienteEntity)
{
  LOGGER.log(Level.INFO, "Creando un cliente nuevo");

em.persist(clienteEntity);
LOGGER.log(Level.INFO, "Saliendo de crear un cliente nuevo");
return clienteEntity;
}
     
          /**
      * Busca todos los clientes en la base de datos
      * @return  Lista con todos los clientes
      */
     public List<ClienteEntity> findAll(){
         LOGGER.log(Level.INFO, "Buscando todos los clientes");
         TypedQuery query =em.createQuery("Select u from ClienteEntity u" ,ClienteEntity.class);
         return query.getResultList();
     }
     
               /**
      * Busca un c liente por el id dado
      * @return  La entidad del cliente
      */
     public ClienteEntity find (Long clienteId){
         LOGGER.log(Level.INFO, "Consultando cliente con id={0}", clienteId);
         return em.find(ClienteEntity.class, clienteId);
     }
     
     public ClienteEntity findByUser(String usuario)
     {
         LOGGER.log(Level.INFO, "Entrando a buscar el usuario: {0}",usuario);
         
         TypedQuery query= em.createQuery("Select c From ClienteEntity c Where c.usuario=:usuario",ClienteEntity.class);
         
          //Se remplaza el placeholder :usuario por el valor del parametro 
                  query = query.setParameter("usuario", usuario);
                  
                  
                    //Lista en donde se guardan los resultados del Query 
                  List<ClienteEntity > answer= query.getResultList();
                  ClienteEntity result;
                  
                  if (answer==null)
                      result=null;
                  else if (answer.isEmpty())
                      result=null;
                  else 
                      result=answer.get(0);
                  
                  LOGGER.log(Level.INFO, "Saliendo de buscar por usuario: {0}", usuario);
                  
                  return result;
               
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
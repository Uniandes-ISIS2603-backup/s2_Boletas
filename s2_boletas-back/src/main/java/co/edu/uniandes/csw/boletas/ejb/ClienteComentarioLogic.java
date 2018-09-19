/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.ClienteEntity;
import co.edu.uniandes.csw.boletas.entities.ComentarioEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.ClientePersistence;
import co.edu.uniandes.csw.boletas.persistence.ComentarioPersistence;
import co.edu.uniandes.csw.boletas.persistence.OrganizadorPersistence;
import java.util.logging.Logger;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;

/**
 * Esta clase modela la relacion entre un
 * cliente y un comentario
 * @author Juan Camacho y Vilma Tirado
 */
public class ClienteComentarioLogic {
    
    
    private static final Logger LOGGER = Logger.getLogger(ClienteComentarioLogic.class.getName());
    
//Injeccion de dependencias 
    @Inject 
    private ClientePersistence clientePersistence;
    
    @Inject 
    private ComentarioPersistence comentarioPersistence;
    
         
      
          /**
     * Agregar un comentario  al la lista del cliente 
     *
     * @param comentarioId El id del comentario 
     * @param clienteId El id del cliente que ofrece el comentario
     * 
     * @return El comentario con el cliente añadido.
     */
      
      public ComentarioEntity addComentario (Long comentarioId, Long clienteId)
      {
          LOGGER.log(Level.INFO, "Inicia proceso de agregarle un comentario al cliente con id = {0}", clienteId);
          //Busca el cliente en la base de datos 
          ClienteEntity clienteEntity= clientePersistence.find(clienteId);
          
          //Busca el comentario en la base de datos 
          ComentarioEntity comentarioEntity = comentarioPersistence.find(comentarioId);
          
          //Le asigna al comentario su cliente
          comentarioEntity.setCliente(clienteEntity);
          
           LOGGER.log(Level.INFO, "Inicia proceso de agregarle un comentario al cliente con id = {0}", clienteId);
           
           return comentarioEntity;
      }
      
          /**
     * Retorna todos los comentarios  asociados a un cliente
     *
     * @param clienteId El ID del cliente buscado
     * @return La lista de comentarios de un cliente.
     */
      
      public List<ComentarioEntity> getComentarios (Long clienteId)
      {
           LOGGER.log(Level.INFO, "Inicia proceso de consultar los comentarios  asociados al cliente con id = {0}", clienteId);
           
           
           return clientePersistence.find(clienteId).getComentarios();
      }
      
          /**
     * Retorna un comentario asociado a un cliente
     *
     * @param clienteId El id del comentario que se va a buscar
     * @param comentarioId El id del comentario a buscar
     * @return El comentario encontrado dentro del cliente.
     * @throws BusinessLogicException Si el comentario no es 
     * de ese cliente 
     */
      
      public ComentarioEntity getComentario (Long clienteId, Long comentarioId) throws BusinessLogicException
      {
          //Se busca la entidad de comentario en la persistencia
          ComentarioEntity comentarioEntity= comentarioPersistence.find(comentarioId);
          //Se busca la lista de comentarios del cliente 
          List<ComentarioEntity> comentarios  = clientePersistence.find(clienteId).getComentarios();
         // Se busca el indice del comentario dentro de la lista del cliente
        int index= comentarios.indexOf(comentarioEntity);
        //Se comprueba que si exista ese comentario en la lista, sino manda excepcion 
        
        
        if(index<0)
        {
        throw new BusinessLogicException("El comentario no pertenece a este cliente");
        }
        
          return  comentarios.get(index);

        
      }
      
          /**
     * Rempla        throw new BusinessLogicException("El comentario no pertenece a este cliente");
zar los comentarios de un cliente
     *
     * @param comentarios  Lista de comentarios  que serán los del cliente.
     * @param clienteId  El id del cliente que se quiere actualizar.
     * @return Lalista de comentarios actualizados.
     */
      
      public List<ComentarioEntity> updateComentarios (List<ComentarioEntity> comentarios,Long clienteId)
      {
          ClienteEntity clienteEntity= clientePersistence.find(clienteId);
          List<ComentarioEntity> listaComentarios= comentarioPersistence.findAll();
          for (ComentarioEntity espec: listaComentarios)
          {
              if (comentarios.contains(espec))
              {
                  espec.setCliente(clienteEntity);
              }
              else if (espec.getCliente()!=null && espec.getCliente().equals(clienteEntity)  )
              {
                  espec.setCliente(null);
              }
          }
          return comentarios;
      }
      
      
      
    
}

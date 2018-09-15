/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.persistence;
;
import co.edu.uniandes.csw.boletas.entities.OrganizadorEntity;
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
public class OrganizadorPersistence {
    
   private static final Logger LOGGER = Logger.getLogger(OrganizadorPersistence.class.getName());
     @PersistenceContext(unitName = "DnsPU")
    protected EntityManager em;
     
          public OrganizadorEntity create( OrganizadorEntity organizadorEntity)
{
  LOGGER.log(Level.INFO, "Creando un organizador nuevo");

em.persist(organizadorEntity);
LOGGER.log(Level.INFO, "Saliendo de crear un organizador nuevo");
return organizadorEntity;
}
           public List<OrganizadorEntity> findAll(){
         LOGGER.log(Level.INFO, "Buscando todos los organizadores");
         TypedQuery query =em.createQuery("Select all organizadores" ,OrganizadorEntity.class);
         return query.getResultList();
     }
           /**
            * Busca un organizador con el nombre dado por parametro
            * @param nombre
            * @return Null si el organizador no existe, el organizador si este ya existe.
            */
           public OrganizadorEntity findByName(String nombre)
           {
                 LOGGER.log(Level.INFO, "Consultando editorial por nombre ", nombre);
                  //Crea un Query para buscar organizadores con el nombre dado por parametro, en este caso nobre es un placeholder
                  TypedQuery query =em.createQuery("Select o From OrganizadorEntity o  Where o.nombre= :nombre ", OrganizadorEntity.class );
                  
                  //Se remplaza el placeholder :nombre por el valor del parametro 
                  query = query.setParameter("nombre", nombre);
                  
                  //Lista en donde se guardan los resultados del Query 
                  List<OrganizadorEntity> answer= query.getResultList();
                  
                  // variable donde se guarda el resultado 
                  OrganizadorEntity result;
                  if (answer==null)
                      result=null;
                  else if (answer.isEmpty())
                      result=null;
                  else 
                      result=answer.get(0);
                  
                  LOGGER.log(Level.INFO, "Saliendo de buscar por nombre ", nombre);
                  
                  return result;
                  
           }
           public OrganizadorEntity find(Long organizadorId){
               
               LOGGER.log(Level.INFO, "Consultando organizador con id={0}", organizadorId);
               return em.find(OrganizadorEntity.class, organizadorId);
           } 
     
     public OrganizadorEntity update (OrganizadorEntity organizadorEntity ){
         LOGGER.log(Level.INFO, "Actualizando organizador con id={0}", organizadorEntity.getId());
          LOGGER.log(Level.INFO, "Saliendo de actualizar organizador con id={0}", organizadorEntity.getId());
          return em.merge(organizadorEntity);
     }
     
     
        public void delete (Long organizadorId){
         LOGGER.log(Level.INFO, "Borrando organizador con id={0}", organizadorId);
         OrganizadorEntity entity= em.find(OrganizadorEntity.class, organizadorId);
         em.remove(entity);
         LOGGER.log(Level.INFO, "Saliendo de borrar organizador con id={0}", organizadorId);
         
     }
    
    
    
}

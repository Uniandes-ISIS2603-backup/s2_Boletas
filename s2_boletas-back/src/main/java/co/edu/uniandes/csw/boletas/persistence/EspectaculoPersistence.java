/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.persistence;

import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Sebastian Rodriguez Beltran  
 */

@Stateless
public class EspectaculoPersistence
{
    
    private static final Logger LOGGER = Logger.getLogger(EspectaculoPersistence.class.getName());
    
    @PersistenceContext(unitName = "DnsPU")
    protected EntityManager em;
    
    /**
     * Metodo para crear un espectaculo en la base de datos
     * Es llamado por la logica 
     * @param espectaculo
     * @return Un espectaculo
     */
    public EspectaculoEntity create(EspectaculoEntity espectaculo)
    {
        LOGGER.log(Level.INFO, "Creando un nuevo espectaculo");
        
        em.persist(espectaculo);
        
        LOGGER.log(Level.INFO, "Saliendo de crear un nuevo espectaculo");
        
       
        return espectaculo;
    }
    
    
    /**
     * Metodo para encontrar un espectaculo dado su id
     * @param id, el id del espectaculo
     * @return Un espectaculoEntity con los valores recuperados de las tablas
     * 
     */
    public EspectaculoEntity find(Long id)
    {
        
        LOGGER.log(Level.INFO, "EspectaculoPersistence encontrar un espectaculo con id :{0}", id);
        return em.find(EspectaculoEntity.class, id);
    }
    
    
    /**
     * Metodo que busca en la base de datos y devuelve todas las tuplas que 
     * se encuentran en esta
     * @return Una lista de valores tipo EspectaculoEntity
     */
    public List<EspectaculoEntity> findAll()
    {
        
        LOGGER.log(Level.INFO, "EspectaculoPersistence findAll espectaculos");
        TypedQuery<EspectaculoEntity> query = em.createQuery("Select u From EspectaculoEntity u",EspectaculoEntity.class);
        return query.getResultList();
    }
         
    /**
     * Metodo que busca un espectaculo en la base de datos dado su nombre
     * @param name, el nombre del espectaculo a buscar
     * @return Un espectaculoEntity
     */
    public EspectaculoEntity findByName(String name)
    {
        
        LOGGER.log(Level.INFO, "EspectaculoPersistence findByName al espectaculo con nombre: {0}", name);
        TypedQuery query = em.createQuery("Select e From EspectaculoEntity e where e.nombre =:nombre", EspectaculoEntity.class);
        
        query = query.setParameter("nombre",name);
        
        List<EspectaculoEntity> lista = query.getResultList();
        
        EspectaculoEntity espec = null;
        
        if(!lista.isEmpty())
        {
            espec = lista.get(0);
        }
        
        LOGGER.log(Level.INFO, "Saliendo de findByName en EspectaculoPersistence");
        return espec;
    }
    
    
    /**
     * Metodo para actualizar un espectaculo 
     * @param entity, Este entity contiene la informacion a modificar del espectaculo
     * @return Un espectaculoEntity, con los datos modificados
     */
    public EspectaculoEntity update(EspectaculoEntity entity)
    {
        
        LOGGER.log(Level.INFO, "Espectaculo Entity update espectaculo con id :{0}", entity.getId());
        return em.merge(entity);
    }
    
    /**
     * Metodo para eliminar un espectaculo de la base de datos
     * @param id , el Id del espectaculo a eliminar
     */
    public void delete(Long id)
    {
        
        LOGGER.log(Level.INFO, "EspectaculoPersistence delete borrar espectaculo con id:{0}", id);
        EspectaculoEntity entity = em.find(EspectaculoEntity.class,id);
        
        em.remove(entity);
        LOGGER.log(Level.INFO, "EspectaculoPersistence saliendo de delete");
    }
}

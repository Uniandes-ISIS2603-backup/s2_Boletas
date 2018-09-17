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
    
    public EspectaculoEntity find(Long id)
    {
        return em.find(EspectaculoEntity.class, id);
    }
    
    public List<EspectaculoEntity> findAll()
    {
        TypedQuery<EspectaculoEntity> query = em.createQuery("Select u From EspectaculoEntity u",EspectaculoEntity.class);
        return query.getResultList();
    }
         
    public EspectaculoEntity findByName(String name)
    {
        TypedQuery query = em.createQuery("Select e From EspectaculoEntity e where e.nombre =:nombre", EspectaculoEntity.class);
        
        query = query.setParameter("nombre",name);
        
        List<EspectaculoEntity> lista = query.getResultList();
        
        EspectaculoEntity espec = null;
        
        if(!lista.isEmpty())
        {
            espec = lista.get(0);
        }
        return espec;
    }
    
    public EspectaculoEntity update(EspectaculoEntity entity)
    {
        
        LOGGER.log(Level.INFO, "Espectaculo Entity update espectaculo con id :" + entity.getId());
        return em.merge(entity);
    }
    
    public void delete(Long id)
    {
        EspectaculoEntity entity = em.find(EspectaculoEntity.class,id);
        
        em.remove(entity);
    }
}

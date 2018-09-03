/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.persistence;

/**
 *
 * @author estudiante
 */
import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class BoletaPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(EspectaculoPersistence.class.getName());
    
    @PersistenceContext(unitName = "DnsPU")
    protected EntityManager em;
    
    
    public BoletaEntity create(BoletaEntity boleta)
    {
        LOGGER.log(Level.INFO, "Creando una nueva boleta");
        
        em.persist(boleta);
        
        LOGGER.log(Level.INFO, "Saliendo de crear una nueva boleta");
        
        return boleta;
    }
    
    public BoletaEntity find(Long id)
    {
        return em.find(BoletaEntity.class, id);
    }
    
    public List<BoletaEntity> findAll()
    {
        TypedQuery<BoletaEntity> query = em.createQuery("",BoletaEntity.class);
        return query.getResultList();
    }
         
    public BoletaEntity findByName(String name)
    {
        TypedQuery query = em.createQuery("Select e From BoletaEntity e where e.id =:id", BoletaEntity.class);
        
        query = query.setParameter("name",name);
        
        List<BoletaEntity> lista = query.getResultList();
        
        BoletaEntity espec = null;
        
        if(!lista.isEmpty())
        {
            espec = lista.get(0);
        }
        return espec;
    }
    
    public BoletaEntity update(BoletaEntity entity)
    {
        return em.merge(entity);
    }
    
    public void delete(Long id)
    {
        BoletaEntity entity = em.find(BoletaEntity.class,id);
        
        em.remove(entity);
    }
}

 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.persistence;

/**
 *
 * @author Diego Camacho
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
    
    private static final Logger LOGGER = Logger.getLogger(BoletaPersistence.class.getName());
    
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
    
    public BoletaEntity update(BoletaEntity boletaEntity)
    {
        LOGGER.log(Level.INFO, "Actualizando boleta con id 0 {0}", boletaEntity.getId());
        LOGGER.log(Level.INFO, "Saliendo de actualizar la boleta con id = {0}", boletaEntity.getId());
        return em.merge(boletaEntity);
    }
    

    
    public void delete(Long id)
    {
        LOGGER.log(Level.INFO, "Borrando boleta con id = {0}", id);
        BoletaEntity entity = em.find(BoletaEntity.class,id);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la boleta con id = {0}", id);
    }
    
    public List<BoletaEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las boletas");
        
        TypedQuery query = em.createQuery("select u from BoletaEntity u", BoletaEntity.class);
        
        return query.getResultList();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.persistence;

import co.edu.uniandes.csw.boletas.entities.LugarEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ja.amortegui10
 */
@Stateless
public class LugarPersistence {
    private static final Logger LOGGER = Logger.getLogger(LugarPersistence.class.getName());
    
    @PersistenceContext(unitName = "DnsPU")
    protected EntityManager em;
    
    public LugarEntity create(LugarEntity lugar)
    {
        LOGGER.log(Level.INFO, "Creando un nuevo lugar.");
        em.persist(lugar);
        LOGGER.log(Level.INFO, "Se ha creado un nuevo lugar.");
        return lugar;
    }
    
    public LugarEntity find(Long id)
    {
        return em.find(LugarEntity.class, id);
    }
    
    public List<LugarEntity> findAll()
    {
        TypedQuery<LugarEntity> query = em.createQuery("", LugarEntity.class);
        return query.getResultList();
    }
    
    public LugarEntity findByName(String name)
    {
        TypedQuery query = em.createQuery("Select e from LugarEntity e where e.nombre =:nombre", LugarEntity.class);
        query = query.setParameter("nombre", name);
        List<LugarEntity> lugares = query.getResultList();
        LugarEntity lugar = null;
        if(!lugares.isEmpty())
            lugar = lugares.get(0);
        
        return lugar;
    }
    
    public LugarEntity update(LugarEntity lugar)
    {
        return em.merge(lugar);
    }
    
    public void delete(Long id)
    {
        LugarEntity lugar = find(id);
        em.remove(lugar);
    }
}

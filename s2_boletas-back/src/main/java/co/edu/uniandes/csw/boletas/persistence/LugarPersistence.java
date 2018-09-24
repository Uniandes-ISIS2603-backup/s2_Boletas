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
    
    /**
     * Método que retorna una entidad Lugar según su id dado.
     * @param id
     * @return 
     */
    public LugarEntity find(Long id)
    {
        return em.find(LugarEntity.class, id);
    }
    
    /**
     * Método que retorna una lista con todas las entidades Lugar existentes en la base de datos.
     * @return 
     */
    public List<LugarEntity> findAll()
    {
        TypedQuery<LugarEntity> query = em.createQuery("select e from LugarEntity e", LugarEntity.class);
        return query.getResultList();
    }
    
    /**
     * Método que retorna un lugar según su nombre.
     * @param name
     * @return 
     */
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
    
    /**
     * Método que altera una entidad Lugar en la base de datos.
     * @param lugar
     * @return 
     */
    public LugarEntity update(LugarEntity lugar)
    {
        return em.merge(lugar);
    }
    
    /**
     * Método que elimina una entidad lugar de la base de datos.
     * @param id 
     */
    public void delete(Long id)
    {
        LugarEntity lugar = find(id);
        em.remove(lugar);
    }
}

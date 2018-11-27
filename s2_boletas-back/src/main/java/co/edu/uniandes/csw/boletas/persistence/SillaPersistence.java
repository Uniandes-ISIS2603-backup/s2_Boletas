/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.persistence;

import co.edu.uniandes.csw.boletas.entities.SillaEntity;
import java.util.ArrayList;
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
public class SillaPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(SillaPersistence.class.getName());
    
    @PersistenceContext(unitName = "DnsPU")
    EntityManager em;
    
    /**
     * Método que crea una entidad Silla en la base de datos.
     * @param silla
     * @return 
     */
    public SillaEntity create(SillaEntity silla)
    {
        LOGGER.log(Level.INFO, "Creando una nueva silla.");
        em.persist(silla);
        LOGGER.log(Level.INFO, "Una nueva silla creada.");
        return silla;
    }
    
    /**
     * Método que retorna una entidad Silla según su id.
     * @param id
     * @return 
     */
    public SillaEntity find(Long id)
    {
        return em.find(SillaEntity.class, id);
    }
    
    public List<SillaEntity> findByLugar(Long lugarId)
    {
        
         List<SillaEntity> sillas = new ArrayList<>();
        List<SillaEntity> sillasTodas = findAll();
        for(SillaEntity actual : sillasTodas)
            if(actual.getLugar().getId().equals( lugarId ))
                sillas.add(actual);
       
        return sillas;
        //Falta mejorar el algoritmo o implementar la búsqueda con ambos parámetros en el query.
    }
    
    /**
     * Método que retorna una entidad Silla según su número de silla y el id del lugar al que pertenece.
     * @param numero
     * @param lugarId
     * @return 
     */
    public SillaEntity findByNumeroAndLugar(String numero, Long lugarId)
    {
        TypedQuery<SillaEntity> query = em.createQuery("Select e from SillaEntity e where e.numero =:numero", SillaEntity.class);
        query = query.setParameter("numero", numero);
        List<SillaEntity> sillas = query.getResultList();
        //Falta mejorar el algoritmo o implementar la búsqueda con ambos parámetros en el query.
        if(!sillas.isEmpty())
            for(SillaEntity sillaActual: sillas)
                if(sillaActual.getLugar().getId().equals(lugarId))
                    return sillaActual;
        return null;
    }
    
    /**
     * Método que retorna una lista con todas las entidades sillas existentes en la base de datos.
     * @return 
     */
    public List<SillaEntity> findAll()
    {
        TypedQuery<SillaEntity> query = em.createQuery("select e from SillaEntity e", SillaEntity.class);
        return query.getResultList();
    }
    
    /**
     * Método que altera una entidad silla en la base de datos.
     * @param silla
     * @return 
     */
    public SillaEntity update(SillaEntity silla)
    {
        return em.merge(silla);
    }
    
    /**
     * Método que elimina una entidad silla de la base de datos. 
     * @param id 
     */
    public void delete(Long id)
    {
        SillaEntity silla = find(id);
        em.remove(silla);
    }
}

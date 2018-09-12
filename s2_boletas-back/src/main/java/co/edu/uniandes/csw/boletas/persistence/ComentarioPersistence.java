/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.persistence;

import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import co.edu.uniandes.csw.boletas.entities.ComentarioEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author estudiante
 */
@Stateless
public class ComentarioPersistence {
    private static final Logger LOGGER = Logger.getLogger(ComentarioPersistence.class.getName());
    
    @PersistenceContext(unitName="DnsPU")
    protected EntityManager em;
    
    public ComentarioEntity create(ComentarioEntity comentario)
    {
         LOGGER.log(Level.INFO, "Creando un nuevo comentario");
        
        em.persist(comentario);
        
        LOGGER.log(Level.INFO, "Saliendo de crear un nuevo comentario");
        
        return comentario;
    }
    
    public ComentarioEntity find(Long id)
    {
        return em.find(ComentarioEntity.class, id);
    }
    
    public void delete(Long id)
    {
        LOGGER.log(Level.INFO, "Borrando comentario con id = {0}", id);
        ComentarioEntity entity = em.find(ComentarioEntity.class,id);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar comentario con id = {0}", id);
    }
    
}
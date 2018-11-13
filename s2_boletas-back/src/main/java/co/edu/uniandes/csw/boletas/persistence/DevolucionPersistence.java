/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.persistence;

import co.edu.uniandes.csw.boletas.entities.DevolucionEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


/**
 *
 * @author Gabriel Hamilton
 */
@Stateless
public class DevolucionPersistence {
    
private static final Logger LOGGER = Logger.getLogger(DevolucionPersistence.class.getName());
   
//DnsPU --- persistence.xml (01/09/2018)
@PersistenceContext(unitName = "DnsPU")
protected EntityManager em;
    

/**
* Devuelve todas las devoluciones de la base de datos.
*
* @return una lista con todas las devoluciones que encuentre en la base de
* datos.
*/
public List<DevolucionEntity> findAll() {
    LOGGER.log(Level.INFO, "Consultando todas las devoluciones");
    TypedQuery query = em.createQuery("select u from DevolucionEntity u", DevolucionEntity.class);
    return query.getResultList();
    }

//-----------------------------------------------------------------------------------------
// Metodos CRUD individuales

/**
 * Metodo para crear en la base de datos (CREATE)
 * @param devolucionEntity, la devolucion a crear
 * @return la devolucion creada
 */
public DevolucionEntity create(DevolucionEntity devolucionEntity)
{
    LOGGER.log(Level.INFO, "Creando una devolucion nueva");       
    em.persist(devolucionEntity);
    LOGGER.log(Level.INFO, "Devolucion nueva creada");
    return devolucionEntity;
}


/**
 * Metodo para buscar una devolucion (READ)
 * @param devolucionId, id de la devolucion a buscar
 * @return la devolucion con el id ingresado por parametro
 */
public DevolucionEntity find(Long devolucionId) {
    LOGGER.log(Level.INFO, "Buscando devolucion con el id={0}", devolucionId);
    return em.find(DevolucionEntity.class, devolucionId);
}

/**
 * Metodo para actualizar una devolucion (UPDATE)
 * @param devolucionEntity, la devolucion con la informacion a actualizar
 * @return la devolucion con la informacion actualizada
 */
public DevolucionEntity update(DevolucionEntity devolucionEntity) {
    LOGGER.log(Level.INFO, "Actualizando devolucion con el id = {0}", devolucionEntity.getId());
    return em.merge(devolucionEntity);
}

/**
 * Metodo para borrar una devolucion (DELETE)
 * @param devolucionId, la devolucion a borrar
 */
public void delete(Long devolucionId) {
    LOGGER.log(Level.INFO, "Borrando devolucion con el id = {0}", devolucionId);
    DevolucionEntity devolucionD = em.find(DevolucionEntity.class, devolucionId);
    em.remove(devolucionD);
    LOGGER.log(Level.INFO, "Devolucion borrada, id = {0}", devolucionId);
}

}

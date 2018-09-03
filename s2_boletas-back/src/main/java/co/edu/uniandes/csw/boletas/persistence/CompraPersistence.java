/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.persistence;

import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author Gabriel Hamilton
 */
@Stateless
public class CompraPersistence {
    
private static final Logger LOGGER = Logger.getLogger(CompraPersistence.class.getName());
   
//DnsPU --- persistence.xml (01/09/2018)
//@PersistenceContext(unitName = "DnsPU")
//protected EntityManager em;
    
       
// Metodos CRUD
/**
 * Metodo para crear en la base de datos (CREATE)
 * @param compraEntity, la compra a crear
 * @return la compra creada
 */
public CompraEntity create(CompraEntity compraEntity)
{
    LOGGER.log(Level.INFO, "Creando una compra nueva");       
    //em.persist(compraEntity);
    LOGGER.log(Level.INFO, "Compra nueva creada");
    return compraEntity;
}


/**
 * Metodo para buscar una compra (READ)
 * @param compraId, id de la compra a buscar
 * @return la compra con el id ingresado por parametro
 */
//public CompraEntity find(Long compraId) {
  //  LOGGER.log(Level.INFO, "Buscando compra con el id={0}", compraId);
   // return em.find(CompraEntity.class, compraId);
//}

/**
 * Metodo para actualizar una compra (UPDATE)
 * @param compraEntity, la compra con la informacion a actualizar
 * @return la compra con la informacion actualizada
 */
//public CompraEntity update(CompraEntity compraEntity) {
  //  LOGGER.log(Level.INFO, "Actualizando compra con el id = {0}", compraEntity.getId());
   // return em.merge(compraEntity);
//}

/**
 * Metodo para borrar una compra (DELETE)
 * @param compraId, la compra a borrar
 */
public void delete(Long compraId) {
    LOGGER.log(Level.INFO, "Borrando compra con el id = {0}", compraId);
   // CompraEntity compraD = em.find(CompraEntity.class, compraId);
    //em.remove(compraD);
    LOGGER.log(Level.INFO, "Compra borrada, id = {0}", compraId);
}

}

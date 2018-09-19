/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.persistence;

import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import co.edu.uniandes.csw.boletas.persistence.CompraPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Gabriel Hamilton
 */
@RunWith(Arquillian.class)
public class CompraPersistenceTest {
    
    /**
     * inyeccion de dependencia para la clase CompraPersistence
     */
    @Inject
    private CompraPersistence compraPersistence;
    
    /**
     * Entity manager 
     */
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Lista datos
     */
    private List<CompraEntity> data = new ArrayList<CompraEntity>();
    
     /**
     * Variable para marcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    UserTransaction utx;
    
    //-------------------------------------------------------------------------------
  @Deployment
  public static JavaArchive createDeployement()
  {
      return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CompraEntity.class.getPackage())
                .addPackage(CompraPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
  }
    
  /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
  
    
     /**
     * Limpia las tablas que están implicadas en la prueba.
     *
     *
     */
    private void clearData() {
        em.createQuery("delete from CompraEntity").executeUpdate();
    }
  
  /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     *
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            CompraEntity entity = factory.manufacturePojo(CompraEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
  
  //-----------------------------------------------------------------------------------
  
     /**
     * Prueba para consultar la lista de Compras.
     */
    @Test
    public void getAllComprasTest() {
        List<CompraEntity> list = compraPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (CompraEntity ent : list) {
            boolean found = false;
            for (CompraEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    
  /**
   *  Prueba crear una compra (POST).
   */
    @Test 
    public void createCompraTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        CompraEntity newEntity = factory.manufacturePojo(CompraEntity.class);
        CompraEntity result = compraPersistence.create(newEntity);
        
        Assert.assertNotNull(result);
        
        CompraEntity entity = em.find(CompraEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    /**
     * Prueba borrar compra (DELETE)
     * Borrar en compra es cambiar el estado de TRUE a FALSE.
     */
    @Test
    public void deleteCompraTest() {
        CompraEntity entity = data.get(0);
        compraPersistence.delete(entity.getId());
        CompraEntity deleted = em.find(CompraEntity.class, entity.getId());
        Assert.assertFalse(deleted.getEstado());
    }
    /**
     * Prueba de conseguir una compra (GET).
     */        
    @Test
    public void getCompraTest() {
        CompraEntity entity = data.get(0);
        CompraEntity newEntity = compraPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }

    /**
     * Prueba actualizar compra (UPDATE).
     */
    @Test
    public void updateCompraTest()
    {
        CompraEntity entity = data.get(0);
        CompraEntity newEntity = compraPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        
        PodamFactory factory = new PodamFactoryImpl();
        CompraEntity newEntity2 = factory.manufacturePojo(CompraEntity.class);
        CompraEntity result = compraPersistence.create(newEntity2);
        
        Assert.assertNotNull(result);
    }
}

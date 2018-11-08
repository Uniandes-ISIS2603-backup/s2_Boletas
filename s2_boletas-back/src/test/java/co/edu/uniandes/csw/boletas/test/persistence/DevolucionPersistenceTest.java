/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.persistence;

import co.edu.uniandes.csw.boletas.entities.DevolucionEntity;
import co.edu.uniandes.csw.boletas.persistence.DevolucionPersistence;
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
public class DevolucionPersistenceTest {
    
    /**
     * inyeccion de dependencia para la clase DevolucionPersistence
     */
    @Inject
    private DevolucionPersistence devolucionPersistence;
    
    /**
     * Entity manager 
     */
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Lista datos
     */
    private List<DevolucionEntity> data = new ArrayList<DevolucionEntity>();
    
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
                .addPackage(DevolucionEntity.class.getPackage())
                .addPackage(DevolucionPersistence.class.getPackage())
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
        em.createQuery("delete from DevolucionEntity").executeUpdate();
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

            DevolucionEntity entity = factory.manufacturePojo(DevolucionEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
  
  //-----------------------------------------------------------------------------------
  
     /**
     * Prueba para consultar la lista de Devoluciones.
     */
    @Test
    public void getAllDevolucionesTest() {
        List<DevolucionEntity> list = devolucionPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (DevolucionEntity ent : list) {
            boolean found = false;
            for (DevolucionEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    
  /**
   *  Prueba crear una devolucion (POST).
   */
    @Test 
    public void createDevolucionTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        DevolucionEntity newEntity = factory.manufacturePojo(DevolucionEntity.class);
        DevolucionEntity result = devolucionPersistence.create(newEntity);
        
        Assert.assertNotNull(result);
        
        DevolucionEntity entity = em.find(DevolucionEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getCompra(), entity.getCompra());
        Assert.assertTrue(newEntity.getBoletas().equals(entity.getBoletas()));
            
    }
    
    /**
     * Prueba borrar devolucion (DELETE)
     * Borrar en devolucion es cambiar el estado de TRUE a FALSE.
     */
    @Test
    public void deleteDevolucionTest() {
        DevolucionEntity entity = data.get(0);
        devolucionPersistence.delete(entity.getId());
        DevolucionEntity deleted = em.find(DevolucionEntity.class, entity.getId());        
        Assert.assertNull(deleted);
    }
    /**
     * Prueba de conseguir una devolucion (GET).
     */        
    @Test
    public void getDevolucionTest() {
        DevolucionEntity entity = data.get(0);
        DevolucionEntity newEntity = devolucionPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(newEntity.getCompra(), entity.getCompra());
        Assert.assertTrue(newEntity.getBoletas().equals(entity.getBoletas()));
    }

    /**
     * Prueba actualizar devolucion (UPDATE).
     */
    @Test
    public void updateDevolucionTest()
    {
        DevolucionEntity entity = data.get(0);
        DevolucionEntity newEntity = devolucionPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        
        PodamFactory factory = new PodamFactoryImpl();
        DevolucionEntity newEntity2 = factory.manufacturePojo(DevolucionEntity.class);
        DevolucionEntity result = devolucionPersistence.create(newEntity2);
        
        Assert.assertNotNull(result);
    }
}
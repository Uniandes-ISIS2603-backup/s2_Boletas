/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.logic;

import co.edu.uniandes.csw.boletas.ejb.DevolucionLogic;
import co.edu.uniandes.csw.boletas.entities.DevolucionEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
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
public class DevolucionLogicTest {
 
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private DevolucionLogic devolucionLogic;

    @PersistenceContext
    private EntityManager em;

    
    /**
     * Variable para marcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    private UserTransaction utx;
    
    private List<DevolucionEntity> data = new ArrayList<DevolucionEntity>();
    
       //-------------------------------------------------------------------------------
  @Deployment
  public static JavaArchive createDeployement()
  {
      return ShrinkWrap.create(JavaArchive.class)
                .addPackage(DevolucionEntity.class.getPackage())
                .addPackage(DevolucionLogic.class.getPackage())
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
    public void getDevolucionesTest() {
        List<DevolucionEntity> list = devolucionLogic.getDevoluciones();
        Assert.assertEquals(data.size(), list.size());
        for (DevolucionEntity entity : list) {
            boolean found = false;
            for (DevolucionEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    
  //-----------------------------------------------------------------------------------
  // CRUD individuales
   /**
    *  Prueba crear una devolucion (POST).
    */
    @Test
    public void createDevolucionTest() throws BusinessLogicException {
        DevolucionEntity newEntity = factory.manufacturePojo(DevolucionEntity.class);
        DevolucionEntity result = devolucionLogic.createDevolucion(newEntity);
        
        Assert.assertNotNull(result);
        
        DevolucionEntity entity = em.find(DevolucionEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getCompra(), entity.getCompra());
        Assert.assertTrue(newEntity.getBoletas().equals(entity.getBoletas()));
    }
    
    /**
     * Prueba borrar devolucion (DELETE)
     */
    @Test
    public void deleteDevolucionTest() throws BusinessLogicException {
        DevolucionEntity entity = data.get(0);
        devolucionLogic.deleteDevolucion(entity.getId());
        DevolucionEntity deleted = em.find(DevolucionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba actualizar devolucion (UPDATE).
     */
    @Test
    public void updateDevolucionTest()
    {
        DevolucionEntity entity = data.get(0);
        DevolucionEntity pojoEntity = factory.manufacturePojo(DevolucionEntity.class);
        pojoEntity.setId(entity.getId());
        devolucionLogic.updateDevolucion(pojoEntity.getId(), pojoEntity);
        DevolucionEntity resp = em.find(DevolucionEntity.class, entity.getId());
       
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getCompra(), resp.getCompra());
        Assert.assertTrue(pojoEntity.getBoletas().equals(resp.getBoletas()));
    }
    
    
     /**
     * Prueba de conseguir una devolucion (GET).
     */        
    @Test
    public void getDevolucionTest() {
        DevolucionEntity entity = data.get(0);
        DevolucionEntity newEntity = devolucionLogic.getDevolucion(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getCompra(), newEntity.getCompra());
        Assert.assertTrue(entity.getBoletas().equals(newEntity.getBoletas()));
    }
    
  
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.logic;

import co.edu.uniandes.csw.boletas.ejb.CompraLogic;
import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
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
public class CompraLogicTest {
 
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private CompraLogic compraLogic;

    @PersistenceContext
    private EntityManager em;

    
    /**
     * Variable para marcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    private UserTransaction utx;
    
    private List<CompraEntity> data = new ArrayList<CompraEntity>();
    
       //-------------------------------------------------------------------------------
  @Deployment
  public static JavaArchive createDeployement()
  {
      return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CompraEntity.class.getPackage())
                .addPackage(CompraLogic.class.getPackage())
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
    public void getComprasTest() {
        List<CompraEntity> list = compraLogic.getCompras();
        Assert.assertEquals(data.size(), list.size());
        for (CompraEntity entity : list) {
            boolean found = false;
            for (CompraEntity storedEntity : data) {
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
    *  Prueba crear una compra (POST).
    */
    @Test
    public void createCompraTest() throws BusinessLogicException {
        CompraEntity newEntity = factory.manufacturePojo(CompraEntity.class);
        CompraEntity result = compraLogic.createCompra(newEntity);
        
        Assert.assertNotNull(result);
        
        CompraEntity entity = em.find(CompraEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getCliente(), entity.getCliente());
        Assert.assertEquals(newEntity.getCostoTotal(), entity.getCostoTotal());
        Assert.assertEquals(newEntity.getDireccion(), entity.getDireccion());
        Assert.assertEquals(newEntity.getEnvio(), entity.getEnvio());
        Assert.assertEquals(newEntity.getEstado(), entity.getEstado());
        Assert.assertEquals(newEntity.getFecha(), entity.getFecha());
        Assert.assertEquals(newEntity.getDevolucion(), entity.getDevolucion());
        Assert.assertTrue(newEntity.getBoletas().equals(entity.getBoletas()));
    }
    
    /**
     * Prueba borrar compra (DELETE)
     * Borrar en compra es cambiar el estado de TRUE a FALSE.
     */
    @Test
    public void deleteCompraTest() throws BusinessLogicException {
        CompraEntity entity = data.get(0);
        compraLogic.deleteCompra(entity.getId());
        CompraEntity deleted = em.find(CompraEntity.class, entity.getId());
        Assert.assertFalse(deleted.getEstado());
    }
    
    /**
     * Prueba actualizar compra (UPDATE).
     */
    @Test
    public void updateCompraTest()
    {
        CompraEntity entity = data.get(0);
        CompraEntity pojoEntity = factory.manufacturePojo(CompraEntity.class);
        pojoEntity.setId(entity.getId());
        compraLogic.updateCompra(pojoEntity.getId(), pojoEntity);
        CompraEntity resp = em.find(CompraEntity.class, entity.getId());
       
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getCliente(), resp.getCliente());
        Assert.assertEquals(pojoEntity.getCostoTotal(), resp.getCostoTotal());
        Assert.assertEquals(pojoEntity.getDireccion(), resp.getDireccion());
        Assert.assertEquals(pojoEntity.getEnvio(), resp.getEnvio());
        Assert.assertEquals(pojoEntity.getEstado(), resp.getEstado());
        Assert.assertEquals(pojoEntity.getFecha(), resp.getFecha());
        Assert.assertEquals(pojoEntity.getDevolucion(), resp.getDevolucion());
        Assert.assertTrue(pojoEntity.getBoletas().equals(resp.getBoletas()));
    }
    
    
     /**
     * Prueba de conseguir una compra (GET).
     */        
    @Test
    public void getCompraTest() {
        CompraEntity entity = data.get(0);
        CompraEntity newEntity = compraLogic.getCompra(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getCliente(), newEntity.getCliente());
        Assert.assertEquals(entity.getCostoTotal(), newEntity.getCostoTotal());
        Assert.assertEquals(entity.getDireccion(), newEntity.getDireccion());
        Assert.assertEquals(entity.getEnvio(), newEntity.getEnvio());
        Assert.assertEquals(entity.getEstado(), newEntity.getEstado());
        Assert.assertEquals(entity.getFecha(), newEntity.getFecha());
        Assert.assertEquals(entity.getDevolucion(), newEntity.getDevolucion());
        Assert.assertTrue(entity.getBoletas().equals(newEntity.getBoletas()));
    }
    
  
    
}

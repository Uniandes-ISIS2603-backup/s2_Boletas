/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.persistence;

import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import co.edu.uniandes.csw.boletas.persistence.CompraPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
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
    
  @Deployment
  public static JavaArchive createDeployement()
  {
      return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CompraEntity.class.getPackage())
                .addPackage(CompraPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
  }
    
    public void createCompraTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        CompraEntity newEntity = factory.manufacturePojo(CompraEntity.class);
        CompraEntity result = compraPersistence.create(newEntity);
        
        Assert.assertNotNull(result);
        
        CompraEntity entity = em.find(CompraEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
            
    
}

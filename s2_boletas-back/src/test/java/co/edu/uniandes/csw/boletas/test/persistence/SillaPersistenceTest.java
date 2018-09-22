/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.persistence;

import co.edu.uniandes.csw.boletas.entities.LugarEntity;
import co.edu.uniandes.csw.boletas.entities.SillaEntity;
import co.edu.uniandes.csw.boletas.persistence.SillaPersistence;
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
 * @author ja.amortegui10
 */
@RunWith(Arquillian.class)
public class SillaPersistenceTest {
    @Inject
    private SillaPersistence sillaPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<SillaEntity> data = new ArrayList<SillaEntity>();
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SillaEntity.class.getPackage())
                .addPackage(SillaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    
    @Before
    public void configTest()
    {
        try
        {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        }catch(Exception e1)
        {
            e1.printStackTrace();
            try
            {
                utx.rollback();
            }catch(Exception e2)
            {
                e2.printStackTrace();
            }
        }
    }
    
    private void clearData()
    {
        em.createQuery("delete from SillaEntity").executeUpdate();
    }
    
    private void insertData()
    {
        PodamFactory factory = new PodamFactoryImpl();
        for(int i = 0; i < 4; i++)
        {
            SillaEntity entity = factory.manufacturePojo(SillaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createSillaTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        
        SillaEntity newEntity = factory.manufacturePojo(SillaEntity.class);
        SillaEntity result = sillaPersistence.create(newEntity);
        
        Assert.assertNotNull(result);
    }
    
    @Test
    public void getSillasTest()
    {
        List<SillaEntity> sillas = sillaPersistence.findAll();
       // Assert.assertEquals(sillas.size(), data.size());
        for(SillaEntity silla1: sillas)
        {
            boolean found = false;
            for(SillaEntity silla2: data)
                if(silla2.equals(silla1))
                {
                    found = true;
                    break;
                }
            Assert.assertTrue(found);
        }
    }
    
     @Test
     public void updateSillaTest()
     {
         PodamFactory factory = new PodamFactoryImpl();
         SillaEntity silla1 = data.get(0);
         SillaEntity updated = factory.manufacturePojo(SillaEntity.class);
         updated.setId(silla1.getId());
         sillaPersistence.update(updated);
         
         SillaEntity sourced = em.find(SillaEntity.class, updated.getId());
         Assert.assertEquals(updated, sourced);
     }
     
     @Test
     public void deleteSillaTest()
     {
         SillaEntity silla1 = data.get(0);
         sillaPersistence.delete(silla1.getId());
         SillaEntity deleted = em.find(SillaEntity.class, silla1.getId());
         Assert.assertNull(deleted);
     }
}

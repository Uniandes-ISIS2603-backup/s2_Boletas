/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.persistence;

import co.edu.uniandes.csw.boletas.entities.LugarEntity;
import co.edu.uniandes.csw.boletas.persistence.LugarPersistence;
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
public class LugarPersistenceTest {
    @Inject
    private LugarPersistence lugarPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<LugarEntity> data = new ArrayList<LugarEntity>();
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return   ShrinkWrap.create(JavaArchive.class)
                .addPackage(LugarEntity.class.getPackage())
                .addPackage(LugarPersistence.class.getPackage())
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
        em.createQuery("delete from LugarEntity").executeUpdate();
    }
    private void insertData()
    {
        PodamFactory factory = new PodamFactoryImpl();
        for(int i = 0; i < 4; i++)
        {
            LugarEntity entity = factory.manufacturePojo(LugarEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    
 
    @Test
    public void createLugarTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        
        LugarEntity newEntity = factory.manufacturePojo(LugarEntity.class);
        LugarEntity result = lugarPersistence.create(newEntity);
        
        Assert.assertNotNull(result);
    }
    

    
    @Test
    public void updateLugarPersistence()
    {
        PodamFactory factory = new PodamFactoryImpl();
        LugarEntity entity = data.get(0);
        LugarEntity updater = factory.manufacturePojo(LugarEntity.class);
        updater.setId(entity.getId());
        lugarPersistence.update(updater);
        LugarEntity updated = em.find(LugarEntity.class, entity.getId());
        Assert.assertEquals(updated, updater);
    }
    
    @Test
    public void deleteLugarPersistence()
    {
        LugarEntity entity = data.get(0);
        lugarPersistence.delete(entity.getId());
        LugarEntity deleted = em.find(LugarEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}

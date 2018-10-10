/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.logic;

import co.edu.uniandes.csw.boletas.ejb.LugarLogic;
import co.edu.uniandes.csw.boletas.entities.LugarEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.LugarPersistence;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class LugarLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    @Inject
    private LugarLogic logic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<LugarEntity> data = new ArrayList<LugarEntity>();
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class )
                .addPackage(LugarEntity.class.getPackage())
                .addPackage(LugarLogic.class.getPackage())
                .addPackage(LugarPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Before
    public void congifTest()
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
            try{
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
            LugarEntity lugar = factory.manufacturePojo(LugarEntity.class);
            em.persist(lugar);
            data.add(lugar);
        }
    }
    
    @Test
    public void createLugarTest()throws BusinessLogicException
    {
        LugarEntity entity = factory.manufacturePojo(LugarEntity.class);
        LugarEntity result = logic.createLugar(entity);
        Assert.assertNotNull(result);
        
        LugarEntity sourced = em.find(LugarEntity.class, result.getId());
        Assert.assertEquals(sourced, result);
    }
    
     @Test
    public void deleteLugarTest()throws BusinessLogicException
    {
        LugarEntity entity = data.get(0);
        logic.deleteLugar(entity.getId());
        LugarEntity deleted = em.find(LugarEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    @Test
    public void updateLugarTest()throws BusinessLogicException
    {
        LugarEntity entity = data.get(0);
        LugarEntity changed = factory.manufacturePojo(LugarEntity.class);
        changed.setId(entity.getId());
        logic.updateLugar(changed.getId(), changed);
        LugarEntity sourced = em.find(LugarEntity.class, changed.getId());
        Assert.assertEquals(sourced, changed);
    }
    
   
    
    @Test 
    public void getLugarTest()
    {
        LugarEntity entity = data.get(0);
        LugarEntity sourced = logic.getLugarById(entity.getId());
        Assert.assertNotNull(sourced);
        Assert.assertEquals(sourced, entity);
    }
    
    @Test
    public void estaDisponibleTest()
    {
        //Está disponible dado que no tiene espectáculos. El lugar con el id 1.
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        boolean disponible = false;
        try
        {
             disponible = logic.estaDisponible(date, data.get(0).getId());
        }catch(BusinessLogicException bLE)
        {
            Assert.fail("El lugar debería existir.");
        }
       
        Assert.assertTrue(disponible);
    }
}

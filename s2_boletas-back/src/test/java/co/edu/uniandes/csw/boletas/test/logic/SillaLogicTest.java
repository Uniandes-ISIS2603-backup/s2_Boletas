/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.logic;

import co.edu.uniandes.csw.boletas.ejb.SillaLogic;
import co.edu.uniandes.csw.boletas.entities.SillaEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class SillaLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private SillaLogic logic;
    
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Variable para marcar las transacciones del em anterior.
     */ 
    @Inject
    private UserTransaction utx;
    
    private List<SillaEntity> data = new ArrayList<SillaEntity>();
    
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class )
                .addPackage(SillaEntity.class.getPackage())
                .addPackage(SillaLogic.class.getPackage())
                .addPackage(SillaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Configuración inicial de prueba.
     */
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
            SillaEntity silla = factory.manufacturePojo(SillaEntity.class);
            em.persist(silla);
            data.add(silla);
        }
    }
    
    @Test
    public void createSillaTest()throws BusinessLogicException
    {
        SillaEntity entity = factory.manufacturePojo(SillaEntity.class);
        SillaEntity result = logic.createSilla(entity);
        Assert.assertNotNull(result);
        
        SillaEntity sourced = em.find(SillaEntity.class, result.getId());
        Assert.assertEquals(sourced, result);
    }
    
    @Test
    public void deleteSillaTest()throws BusinessLogicException
    {
        SillaEntity entity = data.get(0);
        logic.deleteSilla(entity.getId());
        SillaEntity deleted = em.find(SillaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    @Test
    public void updateSillaTest()throws BusinessLogicException
    {
        SillaEntity entity = data.get(0);
        SillaEntity changed = factory.manufacturePojo(SillaEntity.class);
        changed.setId(entity.getId());
        logic.updateSilla(changed.getId(), changed);
        SillaEntity result = em.find(SillaEntity.class, changed.getId());
        Assert.assertEquals(changed, result);
    }
    
    @Test
    public void getSillaTest()throws BusinessLogicException
    {
        SillaEntity entity = data.get(0);
        SillaEntity sourced = logic.getSillaById(entity.getId());
        Assert.assertNotNull(sourced);
        Assert.assertEquals(entity, sourced);
        
    }

}

/*


 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.persistence;

import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import co.edu.uniandes.csw.boletas.persistence.BoletaPersistence;
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
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class BoletaPersistenceTest {
    
    @Inject
    private BoletaPersistence boletaPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
     @Inject
    UserTransaction utx;
    
    
    private List<BoletaEntity> data = new ArrayList<BoletaEntity>();
    
   
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(BoletaEntity.class.getPackage())
                .addPackage(BoletaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Test
    public void createBoletaTest(){
        PodamFactory factory = new PodamFactoryImpl();
        
        BoletaEntity newEntity = factory.manufacturePojo(BoletaEntity.class);
        BoletaEntity result= boletaPersistence.create(newEntity);
        
        Assert.assertNotNull(result);
        BoletaEntity entity = em.find(BoletaEntity.class, result.getId());
        
    }
    
    @Before
    public void configTest()
    {
        try{
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try{
                utx.rollback();
            }
            catch(Exception e1)
            {
                e1.printStackTrace();
            }
        }
    }
    private void clearData()
    {
        em.createQuery("Delete from BoletaEntity").executeUpdate();
    }
    
    private void insertData()
    {
        PodamFactory factory = new PodamFactoryImpl();
        for(int i = 0; i<3; i++)
        {
            BoletaEntity entity =factory.manufacturePojo(BoletaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void deleteBoletaTest()
    {
        BoletaEntity entity = data.get(0);
        boletaPersistence.delete(entity.getId());
        BoletaEntity deleted =em.find(BoletaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.persistence;

import co.edu.uniandes.csw.boletas.entities.ClienteEntity;
import co.edu.uniandes.csw.boletas.persistence.ClientePersistence;
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
 * @author Vilma Tirado Gomez
 */
@RunWith(Arquillian.class)
public class ClientePersistenceTest {
    
    @Inject
    private ClientePersistence clientePersistence;
    
    @Inject
    UserTransaction utx;
    
    @PersistenceContext
    private EntityManager em;
    
        private List<ClienteEntity> data = new ArrayList();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClientePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
   }
    
        private void clearData() {
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }
            private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    
        @Before
    public void configTest() {
        try {
            utx.begin();
            System.out.println("UTX");
            em.joinTransaction();
            System.out.println("YA LLEGUE");
            clearData();
            
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("MUERO ANTES");
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println("MUERO");
            }
        }
    }
    
    // Prueba para CREATE 
    
    @Test
    public void createClienteTest()
    {
        PodamFactory factory= new PodamFactoryImpl();
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity result= clientePersistence.create(newEntity);
        
        Assert.assertNotNull(result);
        
        ClienteEntity entity= em.find(ClienteEntity.class, result.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
    
    //Prueba para GET 
        @Test
    public void FindClientelByNameTest() {
         System.out.println("el tam de data es "+ data.size());
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = clientePersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    } 
    
        /**
     * Prueba para eliminar una Cliente.
     *
     *
     */
    @Test
    public void deleteClienteTest() {
        ClienteEntity entity = data.get(0);
        clientePersistence.delete(entity.getId());
        ClienteEntity deleted = em.find(ClienteEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}

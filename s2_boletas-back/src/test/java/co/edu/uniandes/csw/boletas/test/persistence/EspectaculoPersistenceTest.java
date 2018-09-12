/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.persistence;

import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import co.edu.uniandes.csw.boletas.persistence.EspectaculoPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Sebastian Rodriguez 
 */

@RunWith(Arquillian.class)
public class EspectaculoPersistenceTest 
{
   
    @Inject 
    private EspectaculoPersistence espectaculoPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<EspectaculoEntity> data = new ArrayList<EspectaculoEntity>();
    
    
    @Deployment
    public static JavaArchive createDeployement()
    {
      return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EspectaculoEntity.class.getPackage())
                .addPackage(EspectaculoPersistence.class.getPackage())
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
        em.createQuery("delete from EspectaculoEntity").executeUpdate();
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

            EspectaculoEntity entity = factory.manufacturePojo(EspectaculoEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
  
    
    @Test
    public void createEspectaculoTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        
        EspectaculoEntity  newEntity = factory.manufacturePojo(EspectaculoEntity.class);
        
        EspectaculoEntity resultado = espectaculoPersistence.create(newEntity);
        
        Assert.assertNotNull(resultado);
        
        EspectaculoEntity entity = em.find(EspectaculoEntity.class, resultado.getId());
        
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }
    
    /**
     * Prueba para consultar la lista de Espectaculos.
     */
    @Test
    public void getEspectaculosTest() {
        List<EspectaculoEntity> list = espectaculoPersistence.findAll();
        org.junit.Assert.assertEquals(data.size(), list.size());
        for (EspectaculoEntity ent : list) {
            boolean found = false;
            for (EspectaculoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            org.junit.Assert.assertTrue(found);
        }
    }
    
    @Test
    public void getEspectaculoTest() {
        EspectaculoEntity entity = data.get(0);
        EspectaculoEntity newEntity = espectaculoPersistence.find(entity.getId());
        org.junit.Assert.assertNotNull(newEntity);
        org.junit.Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }
    
      @Test
    public void deleteCompraTest() {
        EspectaculoEntity entity = data.get(0);
        espectaculoPersistence.delete(entity.getId());
        EspectaculoEntity deleted = em.find(EspectaculoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Prueba para actualizar una Editorial.
     */
    @Test
    public void updateEspectaculoTest() {
        EspectaculoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        EspectaculoEntity newEntity = factory.manufacturePojo(EspectaculoEntity.class);

        newEntity.setId(entity.getId());

        espectaculoPersistence.update(newEntity);

        EspectaculoEntity resp = em.find(EspectaculoEntity.class, entity.getId());

        org.junit.Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }

    /**
     * Prueba para consultar una Editorial por nombre.
     */
    @Test
    public void finsEspectaculoByNameTest() {
        EspectaculoEntity entity = data.get(0);
        EspectaculoEntity newEntity = espectaculoPersistence.findByName(entity.getNombre());
        org.junit.Assert.assertNotNull(newEntity);
        org.junit.Assert.assertEquals(entity.getNombre(), newEntity.getNombre());

        newEntity = espectaculoPersistence.findByName(null);
        org.junit.Assert.assertNull(newEntity);
    }
    
}

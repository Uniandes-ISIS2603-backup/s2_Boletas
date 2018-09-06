/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.persistence;

import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import co.edu.uniandes.csw.boletas.persistence.EspectaculoPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
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
    
    @Deployment
    public static JavaArchive createDeployement()
    {
      return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EspectaculoEntity.class.getPackage())
                .addPackage(EspectaculoEntity.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Test
    public void createEspectaculoTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        
        EspectaculoEntity  newEntity = factory.manufacturePojo(EspectaculoEntity.class);
        
        EspectaculoEntity resultado = espectaculoPersistence.create(newEntity);
        
        Assert.assertNotNull(resultado);
        
        EspectaculoEntity entity = em.find(EspectaculoEntity.class, resultado.getId());
        
        Assert.assertEquals(entity.darNombre(), newEntity.darNombre());
    }
    
}

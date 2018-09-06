/*


 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.persistence;

import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import co.edu.uniandes.csw.boletas.persistence.BoletaPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.junit.Arquillian;
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
public class BoletaPersistenceTest {
    
    @Inject
    private BoletaPersistence boletaPersistence;
    
    @PersistenceContext
    private EntityManager em;
    @Test
    public void createBoletaTest(){
        PodamFactory factory = new PodamFactoryImpl();
        
        BoletaEntity newEntity = factory.manufacturePojo(BoletaEntity.class);
        BoletaEntity result= boletaPersistence.create(newEntity);
        
        Assert.assertNotNull(result);
        
    }
}

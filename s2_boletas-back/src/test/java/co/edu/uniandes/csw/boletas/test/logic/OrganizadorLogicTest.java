/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.logic;

import co.edu.uniandes.csw.boletas.ejb.OrganizadorLogic;
import co.edu.uniandes.csw.boletas.ejb.OrganizadorLogic;
import co.edu.uniandes.csw.boletas.entities.ComentarioEntity;
import co.edu.uniandes.csw.boletas.entities.OrganizadorEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.OrganizadorPersistence;
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
public class OrganizadorLogicTest {
        private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private OrganizadorLogic organizadorLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
            
    private List<OrganizadorEntity> data = new ArrayList<OrganizadorEntity>();
   
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(OrganizadorEntity.class.getPackage())
                .addPackage(OrganizadorLogic.class.getPackage())
                .addPackage(OrganizadorPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    
    @Before
    public void configTest() {
        try {
            utx.begin();
            System.out.println("EMPIEZO");
            clearData();
            System.out.println("CLEAR");
            insertData();
            System.out.println("INSERT");
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
    
    private void clearData() {
        em.createQuery("delete from OrganizadorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            OrganizadorEntity organizador = factory.manufacturePojo(OrganizadorEntity.class);
            em.persist(organizador);
            data.add(organizador);
        }

  
    }
    
    @Test
    public void createOrganizadorTest() throws BusinessLogicException
    {
        OrganizadorEntity newEntity = factory.manufacturePojo(OrganizadorEntity.class);
        OrganizadorEntity result = organizadorLogic.createOrganizador(newEntity);
        Assert.assertNotNull(result);
        OrganizadorEntity entity = em.find(OrganizadorEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
    @Test
    public void createOrganizadorNombreTest() throws BusinessLogicException {
        OrganizadorEntity newEntity = factory.manufacturePojo(OrganizadorEntity.class);
        newEntity.setNombre(data.get(0).getNombre());
        organizadorLogic.createOrganizador(newEntity);
    }
   
    
    @Test
    public void getOrganizadorTest() {
        System.out.println(""+ data.size());
        OrganizadorEntity entity = data.get(0);
        OrganizadorEntity resultEntity = organizadorLogic.getOrganizador(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
    }
    
     @Test
    public void updateOrganizadorTest() {
        OrganizadorEntity entity = data.get(0);
        OrganizadorEntity pojoEntity = factory.manufacturePojo(OrganizadorEntity.class);
        pojoEntity.setId(entity.getId());
        organizadorLogic.update( pojoEntity);
        OrganizadorEntity resp = em.find(OrganizadorEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
    }
    
    @Test
    public void deleteOrganizadorTest() throws BusinessLogicException {
        OrganizadorEntity entity = data.get(1);
        organizadorLogic.delete(entity.getId());
        OrganizadorEntity deleted = em.find(OrganizadorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.logic;

import co.edu.uniandes.csw.boletas.ejb.BoletaLogic;
import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
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
 * Pruebas de logica de boleta
 * 
 * @author Diego Camacho
 */
@RunWith(Arquillian.class)
public class BoletaLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private BoletaLogic boletaLogic;
    
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<BoletaEntity> data = new ArrayList<BoletaEntity>();
    

    
     /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(BoletaEntity.class.getPackage())
                .addPackage(BoletaLogic.class.getPackage())
                .addPackage(BoletaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     *  Configuracion inicial de la prueba
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
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
     */
    private void clearData() {
        em.createQuery("delete from BoletaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            BoletaEntity entity = factory.manufacturePojo(BoletaEntity.class);

            em.persist(entity);
            data.add(entity);

        }
    }
    
    /**
     * Prueba para crear una boleta
     * @throws BusinessLogicException 
     */
    @Test
    public void createBoletaTest() throws BusinessLogicException {
        BoletaEntity newEntity = factory.manufacturePojo(BoletaEntity.class);
        BoletaEntity result = boletaLogic.createBoleta(newEntity);
        Assert.assertNotNull(result);
        BoletaEntity entity = em.find(BoletaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getPrecio(), entity.getPrecio());
        //Assert.assertEquals(newEntity.getFecha(), entity.getFecha());
        Assert.assertEquals(newEntity.getVendida(), entity.getVendida());
        
    }
    
    /**
     * Prueba para crear una boleta con mal espectaculo
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createBoletaConEspectaculoInvalidoTest() throws BusinessLogicException
    {
        BoletaEntity newEntity = factory.manufacturePojo(BoletaEntity.class);
        newEntity.setEspectaculo(null);
        boletaLogic.createBoleta(newEntity);
    }
    
    /**
     * Prueba para crear una boleta con un espectaculo que no existe
     * @throws BusinessLogicException
     */
    public void createBoletaConEspectaculoInvalido2Test() throws BusinessLogicException
    {
        BoletaEntity entity = factory.manufacturePojo(BoletaEntity.class);
        EspectaculoEntity espectaculo = new EspectaculoEntity();
        espectaculo.setId(Long.MIN_VALUE);
        entity.setEspectaculo(espectaculo);
        boletaLogic.createBoleta(entity);
        
    }
    /**
     * Prueba para borrar una boleta
     * @throws BusinessLogicException 
     */
    @Test
    public void deleteBoletaTest() throws BusinessLogicException {
        BoletaEntity entity = data.get(1);
        boletaLogic.deleteBoleta(entity.getId());
        BoletaEntity deleted = em.find(BoletaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.logic;

import co.edu.uniandes.csw.boletas.ejb.EspectaculoBoletasLogic;
import co.edu.uniandes.csw.boletas.ejb.EspectaculoLogic;
import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.EspectaculoPersistence;
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
 * @author Juan Diego Camacho
 */
@RunWith(Arquillian.class)
public class EspectaculoBoletasLogicTest {
    
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private EspectaculoLogic espectaculoLogic;
    @Inject
    private EspectaculoBoletasLogic espectaculoBoletasLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<EspectaculoEntity> data = new ArrayList<EspectaculoEntity>();

    private List<BoletaEntity> boletasData = new ArrayList();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EspectaculoEntity.class.getPackage())
                .addPackage(EspectaculoLogic.class.getPackage())
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
        em.createQuery("delete from EspectaculoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            BoletaEntity boletas = factory.manufacturePojo(BoletaEntity.class);
            em.persist(boletas);
            boletasData.add(boletas);
        }
        for (int i = 0; i < 3; i++) {
            EspectaculoEntity entity = factory.manufacturePojo(EspectaculoEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                boletasData.get(i).setEspectaculo(entity);
            }
        }
    }
    
    @Test
    public void addBoletaTest()
    {
        EspectaculoEntity entity = data.get(0);
        BoletaEntity boletaEntity = boletasData.get(1);
        BoletaEntity response = espectaculoBoletasLogic.addBoleta(boletaEntity.getId(), entity.getId());
        Assert.assertNotNull(response);
        Assert.assertEquals(boletaEntity.getId(),response.getId());
    }
    
    @Test
    public void getBoletasTest() throws BusinessLogicException
    {
        EspectaculoEntity entity = data.get(0);
        BoletaEntity boletaEntity = boletasData.get(0);
        BoletaEntity response = espectaculoBoletasLogic.getBoleta(entity.getId(), boletaEntity.getId());
        
        Assert.assertEquals(boletaEntity.getId(), response.getId());
        Assert.assertEquals(boletaEntity.getPrecio(), response.getPrecio());
        Assert.assertEquals(boletaEntity.getFecha(), response.getFecha());
        Assert.assertEquals(boletaEntity.getVendida(), response.getVendida());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void getoletaNoAsociadaTest() throws BusinessLogicException {
        EspectaculoEntity entity = data.get(0);
        BoletaEntity boletaEntity = boletasData.get(1);
        espectaculoBoletasLogic.getBoleta(entity.getId(), boletaEntity.getId());
    }
    
    @Test
    public void replaceBoletasTest() {
        EspectaculoEntity entity = data.get(0);
        List<BoletaEntity> list = boletasData.subList(1, 3);
        espectaculoBoletasLogic.replaceBoletas(entity.getId(), list);

        entity = espectaculoLogic.getEspectaculo(entity.getId());
        Assert.assertFalse(entity.getBoletas().contains(boletasData.get(0)));
        Assert.assertTrue(entity.getBoletas().contains(boletasData.get(1)));
        Assert.assertTrue(entity.getBoletas().contains(boletasData.get(2)));
    }
}

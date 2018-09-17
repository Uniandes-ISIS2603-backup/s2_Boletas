/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.logic;

import co.edu.uniandes.csw.boletas.ejb.CompraBoletasLogic;
import co.edu.uniandes.csw.boletas.ejb.CompraLogic;
import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.CompraPersistence;
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
 * @author Gabriel Hamilton
 */
@RunWith(Arquillian.class)
public class CompraBoletasLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private CompraLogic compraLogic;
    @Inject
    private CompraBoletasLogic compraBoletasLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<CompraEntity> data = new ArrayList<CompraEntity>();

    private List<BoletaEntity> boletasData = new ArrayList();

    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CompraEntity.class.getPackage())
                .addPackage(CompraLogic.class.getPackage())
                .addPackage(CompraPersistence.class.getPackage())
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
        em.createQuery("delete from CompraEntity").executeUpdate();
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
            CompraEntity entity = factory.manufacturePojo(CompraEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                boletasData.get(i).setCompra(entity);
            }
        }
    }

    /**
     * Prueba para asociar una boleta existente a un compra.
     */
    @Test
    public void addBoletaTest() {
        CompraEntity entity = data.get(0);
        BoletaEntity boletaEntity = boletasData.get(1);
        BoletaEntity response = compraBoletasLogic.createBoleta(boletaEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(boletaEntity.getId(), response.getId());
    }

    /**
     * Prueba para obtener una colección de instancias de Boletas asociadas a una
     * instancia Compra.
     */
    @Test
    public void getBoletasTest() {
        List<BoletaEntity> list = compraBoletasLogic.getBoletas(data.get(0).getId());

        Assert.assertEquals(1, list.size());
    }

    /**
     * Prueba para obtener una instancia de Boleta asociada a una instancia
     * Compra.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void getBoletaTest() throws BusinessLogicException {
        CompraEntity entity = data.get(0);
        BoletaEntity boletaEntity = boletasData.get(0);
        BoletaEntity response = compraBoletasLogic.getBoleta(entity.getId(), boletaEntity.getId());

        Assert.assertEquals(boletaEntity.getId(), response.getId());
        Assert.assertEquals(boletaEntity.getFecha(), response.getFecha());
        Assert.assertEquals(boletaEntity.getPrecio(), response.getPrecio());
        Assert.assertEquals(boletaEntity.getSilla(), response.getSilla());
        Assert.assertEquals(boletaEntity.getVendida(), response.getVendida());
    }


    /**
     * Prueba para remplazar las instancias de Boleta asociadas a una instancia
     * de Compra.
     */
    @Test
    public void putBoletasTest() {
        CompraEntity entity = data.get(0);
        List<BoletaEntity> list = boletasData.subList(1, 3);
        compraBoletasLogic.putBoletas(entity.getId(), list);

        entity = compraLogic.getCompra(entity.getId());
        Assert.assertFalse(entity.getBoletas().contains(boletasData.get(0)));
        Assert.assertTrue(entity.getBoletas().contains(boletasData.get(1)));
        Assert.assertTrue(entity.getBoletas().contains(boletasData.get(2)));
    }
    
}

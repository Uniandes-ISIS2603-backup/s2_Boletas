/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.logic;

import co.edu.uniandes.csw.boletas.ejb.BoletaCompraLogic;
import co.edu.uniandes.csw.boletas.ejb.BoletaLogic;
import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.CompraPersistence;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
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
public class BoletaCompraLogicTest {
    
      private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private BoletaLogic boletasLogic;
    @Inject
    private BoletaCompraLogic boletaCompraLogic;

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
                .addPackage(BoletaLogic.class.getPackage())
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
            BoletaEntity books = factory.manufacturePojo(BoletaEntity.class);
            em.persist(books);
            boletasData.add(books);
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
     * Prueba para remplazar las instancias de Boletas asociadas a una instancia
     * de Compra.
     */
    @Test
    public void putCompraTest() {
        BoletaEntity entity = boletasData.get(0);
        boletaCompraLogic.replaceCompra(entity.getId(), data.get(1).getId());
        entity = boletasLogic.getBoleta(entity.getId());
        Assert.assertEquals(entity.getCompra(), data.get(1));
    }

    /**
     * Prueba para desasociar una boleta existente de una compra existente
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void removeBoletaTest() throws BusinessLogicException {
        boletaCompraLogic.removeCompra(boletasData.get(0).getId());
        BoletaEntity response = boletasLogic.getBoleta(boletasData.get(0).getId());
        Assert.assertNull(response.getCompra());
}
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.logic;

import co.edu.uniandes.csw.boletas.ejb.CompraClienteLogic;
import co.edu.uniandes.csw.boletas.ejb.CompraLogic;
import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import co.edu.uniandes.csw.boletas.entities.ClienteEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
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
 * @author Gabriel Hamilton y Vilma Tirado
 */
@RunWith(Arquillian.class)
public class CompraClienteLogicTest {
    
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private CompraLogic comprasLogic;
    @Inject
    private CompraClienteLogic compraClienteLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ClienteEntity> data = new ArrayList<ClienteEntity>();

    private List<CompraEntity> comprasData = new ArrayList();


    //-------------------------------------------------------------------------------
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(CompraLogic.class.getPackage())
                .addPackage(ClientePersistence.class.getPackage())
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
        em.createQuery("delete from CompraEntity").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            CompraEntity books = factory.manufacturePojo(CompraEntity.class);
            em.persist(books);
            comprasData.add(books);
        }
        for (int i = 0; i < 3; i++) {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                comprasData.get(i).setCliente(entity);
            }
        }
    }
    //-------------------------------------------------------------------------------
    /**
     * Prueba para remplazar las instancias de Compras asociadas a una instancia
     * de Cliente.
     */
    @Test
    public void putClienteTest() {
        CompraEntity entity = comprasData.get(0);
        compraClienteLogic.replaceCliente(entity.getId(), data.get(1).getId());
        entity = comprasLogic.getCompra(entity.getId());
        Assert.assertEquals(entity.getCliente(), data.get(1));
    }

    /**
     * Prueba para desasociar una compra existente de un cliente existente
     *
     * @throws co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException
     */
    @Test
    public void removeClienteTest() throws BusinessLogicException {
        compraClienteLogic.removeCliente(comprasData.get(0).getId());
        CompraEntity response = comprasLogic.getCompra(comprasData.get(0).getId());
        Assert.assertNull(response.getCliente());
}

    
}
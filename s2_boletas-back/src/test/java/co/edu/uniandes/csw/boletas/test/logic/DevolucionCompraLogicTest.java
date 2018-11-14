/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.logic;

import co.edu.uniandes.csw.boletas.ejb.CompraDevolucionLogic;
import co.edu.uniandes.csw.boletas.ejb.CompraLogic;
import co.edu.uniandes.csw.boletas.ejb.DevolucionCompraLogic;
import co.edu.uniandes.csw.boletas.ejb.DevolucionLogic;
import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import co.edu.uniandes.csw.boletas.entities.DevolucionEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.CompraPersistence;
import co.edu.uniandes.csw.boletas.persistence.DevolucionPersistence;
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
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class DevolucionCompraLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private DevolucionLogic devolucionLogic;

    @Inject
    private DevolucionCompraLogic devolucionCompraLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
    
    private List<CompraEntity> compras = new ArrayList<CompraEntity>();
    
    private List<DevolucionEntity> devoluciones = new ArrayList<DevolucionEntity>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(DevolucionEntity.class.getPackage())
                .addPackage(DevolucionLogic.class.getPackage())
                .addPackage(DevolucionPersistence.class.getPackage())
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
        em.createQuery("delete from DevolucionEntity").executeUpdate();
        
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            DevolucionEntity dev = factory.manufacturePojo(DevolucionEntity.class);
            em.persist(dev);
            devoluciones.add(dev);
        }
        for (int i = 0; i < 3; i++) {
            CompraEntity entity = factory.manufacturePojo(CompraEntity.class);
            em.persist(entity);
            compras.add(entity);
            if (i == 0) {
                devoluciones.get(i).setCompra(entity);
            }
        }
    }
    
    
   
    @Test
    public void replaceCompraTest() {
        DevolucionEntity entity = devoluciones.get(1);
        devolucionCompraLogic.replaceCompra(entity.getId(), compras.get(1).getId());
        
        DevolucionEntity dev = devolucionLogic.getDevolucion(entity.getId());
        
        Assert.assertEquals(dev.getCompra().getId(), compras.get(1).getId());
        Assert.assertEquals(dev.getCompra().getEstado(), compras.get(1).getEstado());
    }
    
    @Test
    public void removeCompra() throws BusinessLogicException {
        devolucionCompraLogic.removeCompra(devoluciones.get(0).getId());
        DevolucionEntity response = devolucionLogic.getDevolucion(devoluciones.get(0).getId());
        Assert.assertNull(response.getCompra());
    }
    
}

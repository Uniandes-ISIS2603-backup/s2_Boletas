/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.logic;

import co.edu.uniandes.csw.boletas.ejb.CompraDevolucionLogic;
import co.edu.uniandes.csw.boletas.ejb.CompraLogic;
import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import co.edu.uniandes.csw.boletas.entities.DevolucionEntity;
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
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class CompraDevolucionLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private CompraLogic compraLogic;

    @Inject
    private CompraDevolucionLogic compraDevolucionLogic;

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
        
        em.createQuery("delete from DevolucionEntity").executeUpdate();
        em.createQuery("delete from CompraEntity").executeUpdate();
        
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
    public void addDevolucion()
    {
        CompraEntity compra = compras.get(1);
        
        DevolucionEntity dev = devoluciones.get(1);
        
        DevolucionEntity res = compraDevolucionLogic.addDevolucion(dev.getId(), compra.getId());
        
        Assert.assertNotNull(res);
        Assert.assertEquals(dev.getId(), res.getId());
    }
    
    @Test 
    public void getDevolucion() throws BusinessLogicException
    {
        DevolucionEntity dev = compraDevolucionLogic.getDevolucion(compras.get(0).getId());
        
        Assert.assertEquals(dev.getId(), devoluciones.get(0).getId());
        
    }
    
    @Test
    public void updateDevolucion()
    {
        DevolucionEntity dev = devoluciones.get(1);
        
        CompraEntity com = compras.get(0);
        
        DevolucionEntity dev2 = compraDevolucionLogic.updateDevolucion(com.getId(), dev);
        
        Assert.assertEquals(dev2.getId(), dev.getId());
    }
    
    @Test
    public void removeDevolucion()
    {
         DevolucionEntity dev = devoluciones.get(0);
        
        CompraEntity com = compras.get(0);
        
        compraDevolucionLogic.removeDevolucion(com.getId(), dev.getId());
        
        Assert.assertNull(com.getDevolucion());
    }
    
}

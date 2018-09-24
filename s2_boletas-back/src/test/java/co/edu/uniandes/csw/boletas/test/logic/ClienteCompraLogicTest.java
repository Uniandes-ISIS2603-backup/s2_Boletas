/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.logic;

import co.edu.uniandes.csw.boletas.ejb.ClienteCompraLogic;
import co.edu.uniandes.csw.boletas.ejb.ClienteLogic;
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
 * @author Gabriel Hamilton
 */
@RunWith(Arquillian.class)
public class ClienteCompraLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ClienteLogic clienteLogic;
    @Inject
    private ClienteCompraLogic clienteCompraLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ClienteEntity> data = new ArrayList<ClienteEntity>();

    private List<CompraEntity> comprasData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClienteLogic.class.getPackage())
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
            CompraEntity compras = factory.manufacturePojo(CompraEntity.class);
            em.persist(compras);
            comprasData.add(compras);
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

    /**
     * Prueba para asociar una Compra existente a un Cliente.
     */
    @Test
    public void addComprasTest() {
        ClienteEntity entity = data.get(0);
        CompraEntity compraEntity = comprasData.get(1);
        CompraEntity response = clienteCompraLogic.addCompra(compraEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(compraEntity.getId(), response.getId());
    }

    /**
     * Prueba para obtener una colección de instancias de Compra asociadas a una
     * instancia Cliente.
     */
    @Test
    public void getComprasTest() {
        List<CompraEntity> list = clienteCompraLogic.getCompras(data.get(0).getId());

        Assert.assertEquals(1, list.size());
    }

    /**
     * Prueba para obtener una instancia de Compras asociada a una instancia
     * Cliente.
     *
     * @throws co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException
     */
    @Test
    public void getCompraTest() throws BusinessLogicException {
        ClienteEntity entity = data.get(0);
        CompraEntity compraEntity = comprasData.get(0);
        CompraEntity response = clienteCompraLogic.getCompra(entity.getId(), compraEntity.getId());

        Assert.assertEquals(compraEntity.getId(), response.getId());
        Assert.assertEquals(compraEntity.getCliente(), response.getCliente());
        Assert.assertEquals(compraEntity.getCostoTotal(), response.getCostoTotal());
        Assert.assertEquals(compraEntity.getDireccion(), response.getDireccion());
        Assert.assertEquals(compraEntity.getEnvio(), response.getEnvio());
        Assert.assertEquals(compraEntity.getEstado(), response.getEstado());
        Assert.assertEquals(compraEntity.getFecha(), response.getFecha());
        Assert.assertTrue(compraEntity.getBoletas().equals(response.getBoletas()));
    }

    /**
     * Prueba para obtener una instancia de Compra asociada a una instancia
     * Cliente que no le pertenece.
     *
     * @throws co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getCompraNoAsociadoTest() throws BusinessLogicException {
        ClienteEntity entity = data.get(0);
        CompraEntity compraEntity = comprasData.get(1);
        clienteCompraLogic.getCompra(entity.getId(), compraEntity.getId());
    }

    /**
     * Prueba para remplazar las instancias de Compras asociadas a una instancia
     * de Cliente.
     */
    @Test
    public void replaceComprasTest() {
        ClienteEntity entity = data.get(0);
        List<CompraEntity> list = comprasData.subList(1, 3);
        clienteCompraLogic.updateCompras(entity.getId(), list);

        entity = clienteLogic.getCliente(entity.getId());
        Assert.assertFalse(entity.getCompras().contains(comprasData.get(0)));
        Assert.assertTrue(entity.getCompras().contains(comprasData.get(1)));
        Assert.assertTrue(entity.getCompras().contains(comprasData.get(2)));
}
}

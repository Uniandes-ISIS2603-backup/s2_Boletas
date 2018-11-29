/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.logic;

import co.edu.uniandes.csw.boletas.ejb.ClienteComentarioLogic;
import co.edu.uniandes.csw.boletas.ejb.ClienteLogic;
import co.edu.uniandes.csw.boletas.entities.ClienteEntity;
import co.edu.uniandes.csw.boletas.entities.ComentarioEntity;
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
 * @author Juan Diego Camacho
 */
@RunWith(Arquillian.class)
public class ClienteComentarioLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ClienteLogic clienteLogic;
    
    @Inject
    private ClienteComentarioLogic clienteComentarioLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ClienteEntity> data = new ArrayList<ClienteEntity>();

    private List<ComentarioEntity> comentariosData = new ArrayList<ComentarioEntity>();
    
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
        em.createQuery("delete from ComentarioEntity").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ComentarioEntity comentarios = factory.manufacturePojo(ComentarioEntity.class);
            em.persist(comentarios);
            comentariosData.add(comentarios);
        }
        for (int i = 0; i < 3; i++) {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                comentariosData.get(i).setCliente(entity);
            }
        }
    }
    
    @Test
    public void addComentarioTest()
    {
        ClienteEntity entity = data.get(0);
        ComentarioEntity comentarioEntity = comentariosData.get(1);
        ComentarioEntity response = clienteComentarioLogic.addComentario(comentarioEntity.getId(), entity.getId());
        Assert.assertNotNull(response);
        Assert.assertEquals(comentarioEntity.getId(),response.getId());
    }
    
    @Test
    public void getComentariosTest() throws BusinessLogicException
    {
        ClienteEntity entity = data.get(0);
        ComentarioEntity comentarioEntity = comentariosData.get(0);
        ComentarioEntity response = clienteComentarioLogic.getComentario(entity.getId(), comentarioEntity.getId());
        
        Assert.assertEquals(comentarioEntity.getId(), response.getId());
        Assert.assertEquals(comentarioEntity.getMensaje(), response.getMensaje());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void getComentarioNoAsociadoTest() throws BusinessLogicException {
        ClienteEntity entity = data.get(0);
        ComentarioEntity comentarioEntity = comentariosData.get(1);
        clienteComentarioLogic.getComentario(entity.getId(), comentarioEntity.getId());
    }
    
    @Test
    public void replaceComentarioTest() {
        ClienteEntity entity = data.get(0);
        List<ComentarioEntity> list = comentariosData.subList(1, 3);
        clienteComentarioLogic.updateComentarios(entity.getId(), list);

        entity = clienteLogic.getCliente(entity.getId());
        Assert.assertFalse(entity.getComentarios().contains(comentariosData.get(0)));
        Assert.assertTrue(entity.getComentarios().contains(comentariosData.get(1)));
        Assert.assertTrue(entity.getComentarios().contains(comentariosData.get(2)));
    } 
}

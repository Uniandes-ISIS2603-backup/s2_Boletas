/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.logic;


import co.edu.uniandes.csw.boletas.ejb.ComentarioLogic;
import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import co.edu.uniandes.csw.boletas.entities.ClienteEntity;
import co.edu.uniandes.csw.boletas.entities.ComentarioEntity;
import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.ComentarioPersistence;
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
 *  Pruebas de logica de comentarios
 * 
 * @author Diego Camacho
 */
@RunWith(Arquillian.class)
public class ComentarioLogicTest {
     private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private ComentarioLogic comentarioLogic;
    
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<ComentarioEntity> data = new ArrayList<ComentarioEntity>();
    private List<EspectaculoEntity> espectaculoData = new ArrayList<EspectaculoEntity>();
    private List<CompraEntity> compraData = new ArrayList<CompraEntity>();
    private List<ClienteEntity> clienteData = new ArrayList<ClienteEntity>();
    private List<BoletaEntity> boletaData = new ArrayList<BoletaEntity>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ComentarioEntity.class.getPackage())
                .addPackage(ComentarioLogic.class.getPackage())
                .addPackage(ComentarioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
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
        em.createQuery("delete from BoletaEntity").executeUpdate();
        em.createQuery("delete from CompraEntity").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();
        em.createQuery("delete from EspectaculoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            EspectaculoEntity espEntity = factory.manufacturePojo(EspectaculoEntity.class);
            em.persist(espEntity);
            espectaculoData.add(espEntity);
        }
        for (int i = 0; i < 3; i++) {
            ClienteEntity cliEntity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(cliEntity);
            clienteData.add(cliEntity);
        }
        for (int i = 0; i < 3; i++) {
            BoletaEntity bolEntity = factory.manufacturePojo(BoletaEntity.class);
            bolEntity.setEspectaculo(espectaculoData.get(0));
            em.persist(bolEntity);
            boletaData.add(bolEntity);
        }
        
        
        for (int i = 0; i < 3; i++) {
            CompraEntity compEntity = factory.manufacturePojo(CompraEntity.class);
            compEntity.setBoletas(boletaData);
            compEntity.setCliente(clienteData.get(0));
            em.persist(compEntity);
            compraData.add(compEntity);
        }
        for (int i = 0; i < 3; i++) {
            ComentarioEntity entity = factory.manufacturePojo(ComentarioEntity.class);
            clienteData.get(0).setCompras(compraData);
            entity.setCliente(clienteData.get(0));
            entity.setEspectaculo(espectaculoData.get(0));
            em.persist(entity);
            data.add(entity);

        }
        espectaculoData.get(0).setComentarios(data);
        clienteData.get(0).setComentarios(data);
    }
    
    /**
     * Prueba para crear un comentario
     * @throws BusinessLogicException 
     */
    @Test
    public void createComentarioTest() throws BusinessLogicException {
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        newEntity.setCliente(clienteData.get(0));
        newEntity.setEspectaculo(espectaculoData.get(0));
        ComentarioEntity result = comentarioLogic.createComentario(newEntity);
        Assert.assertNotNull(result);
        ComentarioEntity entity = em.find(ComentarioEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getMensaje(), entity.getMensaje());
    }
    
    /**
     * Prueba para crear un comentario con cliente invalido
     * @throws BusinessLogicException 
     */
   /* @Test(expected = BusinessLogicException.class)
    public void createComentarioConClienteInvalido() throws BusinessLogicException
    {
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        newEntity.setEspectaculo(espectaculoData.get(0));
        newEntity.setCliente(null);
        comentarioLogic.createComentario(newEntity);
    }*/
    
    /**
     * Prueba para crear un comentario con espectaculo invalido
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createComentarioConEspectaculoInvalido() throws BusinessLogicException
    {
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        newEntity.setEspectaculo(null);
        newEntity.setCliente(clienteData.get(0));
        comentarioLogic.createComentario(newEntity);
    }
    
    @Test
    public void deleteComentarioTest() throws BusinessLogicException {
        ComentarioEntity entity = data.get(1);
        comentarioLogic.deleteComentario(entity.getId());
        ComentarioEntity deleted = em.find(ComentarioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}


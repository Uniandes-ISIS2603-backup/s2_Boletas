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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
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
            espectaculoData.add(espEntity);
        }
        for (int i = 0; i < 3; i++) {
            ClienteEntity cliEntity = factory.manufacturePojo(ClienteEntity.class);
            
            clienteData.add(cliEntity);
        }
        for (int i = 0; i < 3; i++) {
            BoletaEntity bolEntity = factory.manufacturePojo(BoletaEntity.class);
            bolEntity.setEspectaculo(espectaculoData.get(0));
            
            boletaData.add(bolEntity);
        }
        
        
        for (int i = 0; i < 3; i++) {
            CompraEntity compEntity = factory.manufacturePojo(CompraEntity.class);
            compEntity.setBoletas(boletaData);
            compEntity.setCliente(clienteData.get(0));
           
            compraData.add(compEntity);
            
        }
        clienteData.get(0).setCompras(compraData);
        
        for (int i = 0; i < 3; i++) {
            ComentarioEntity entity = factory.manufacturePojo(ComentarioEntity.class);
            entity.setCliente(clienteData.get(0));
            entity.setEspectaculo(espectaculoData.get(0));
            em.persist(entity);
            data.add(entity);

        }
        //Calendar cal = new GregorianCalendar(2018, 03, 03);
        //Date fecha = cal.getTime();
        //espectaculoData.get(0).setFecha(fecha);
        espectaculoData.get(0).setComentarios(data);
        espectaculoData.get(0).setBoletas(boletaData);
        Date d;
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        d = c.getTime();
        espectaculoData.get(0).setFecha(d);
        em.persist(espectaculoData.get(0));       
        boletaData.get(0).setCompra(compraData.get(0));
        boletaData.get(0).setEspectaculo(espectaculoData.get(0));
        em.persist(boletaData.get(0));
        em.persist(compraData.get(0));
        em.persist(clienteData.get(0));
        
        Calendar e = Calendar.getInstance();
        e.add(Calendar.DAY_OF_MONTH, 1);
        boletaData.get(1).setEspectaculo(espectaculoData.get(1));
        boletaData.get(1).setCompra(compraData.get(1));
        espectaculoData.get(1).setFecha(e.getTime());
        compraData.get(1).setCliente(clienteData.get(1));
        em.persist(boletaData.get(1));
        em.persist(espectaculoData.get(1));
        em.persist(compraData.get(1));
        em.persist(clienteData.get(1));
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
    @Test(expected = BusinessLogicException.class)
    public void createComentarioConClienteInvalido() throws BusinessLogicException
    {
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        newEntity.setEspectaculo(espectaculoData.get(0));
        newEntity.setCliente(null);
        comentarioLogic.createComentario(newEntity);
    }
    
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
    
    /**
     * Prueba para crear un comentario con espectaculo que aun no ha ocurrido
     * @throws BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void CreateComentarioConEspectaculoConFechaPosterior() throws BusinessLogicException
    {
        ComentarioEntity entity = factory.manufacturePojo(ComentarioEntity.class);
        entity.setEspectaculo(espectaculoData.get(1));
        entity.setCliente(clienteData.get(1));
        comentarioLogic.createComentario(entity);     
    }
    
    /**
     * Prueba para crear un comentario con un cliente que no tiene boletas/compras del espectaculo
     * @throws BusinessLogicException
     */
    @Test (expected = BusinessLogicException.class)
    public void createComentarioClienteNoEspectaculo() throws BusinessLogicException
    {
        ComentarioEntity entity = factory.manufacturePojo(ComentarioEntity.class);
        entity.setEspectaculo(espectaculoData.get(0));
        entity.setCliente(clienteData.get(1));
        comentarioLogic.createComentario(entity);
        
    }
    /**
     * Prueba para borrar un comentario
     * @throws BusinessLogicException 
     */
    @Test
    public void deleteComentarioTest() throws BusinessLogicException {
        ComentarioEntity entity = data.get(1);
        comentarioLogic.deleteComentario(entity.getId());
        ComentarioEntity deleted = em.find(ComentarioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    
}


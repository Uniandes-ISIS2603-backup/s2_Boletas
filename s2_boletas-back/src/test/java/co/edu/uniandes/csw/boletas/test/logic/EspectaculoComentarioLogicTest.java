/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.logic;

import co.edu.uniandes.csw.boletas.ejb.EspectaculoComentarioLogic;
import co.edu.uniandes.csw.boletas.ejb.EspectaculoLogic;
import co.edu.uniandes.csw.boletas.entities.ComentarioEntity;
import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.EspectaculoPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Sebastian Rodriguez y Diego Camacho
 */
@RunWith(Arquillian.class)
public class EspectaculoComentarioLogicTest
{
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    
    
    @Inject
    private EspectaculoComentarioLogic espectaculoComentarioLogic;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
    
    
    private List<EspectaculoEntity> espectaculos = new ArrayList<EspectaculoEntity>();
    
    private List<ComentarioEntity> comentarios = new ArrayList<ComentarioEntity>();
    
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
        
        em.createQuery("delete from ComentarioEntity").executeUpdate();
        
        em.createQuery("delete from EspectaculoEntity").executeUpdate();
    }
    
    /**
     * Metodo que llena los arreglos para hacer las pruebas, los objetos son hechos por 
     * el factory que va a hacer POJO's
     */
    private void insertData()
    {
        
        for (int i = 0; i < 3; i++) {
            ComentarioEntity comen = factory.manufacturePojo(ComentarioEntity.class);
            em.persist(comen);
            comentarios.add(comen);
        }
        
        for (int i = 0; i < 3; i++) {
            EspectaculoEntity espec = factory.manufacturePojo(EspectaculoEntity.class);
            em.persist(espec);
            espectaculos.add(espec);
            if(i == 0)
            {
                comentarios.get(i).setEspectaculo(espec);
            }
        }
    }
    
    
    /**
     * Prueba para añadir un comentario a un espectaculo
     * Se obtiene el primer elemento de la lista de espectaculos
     * Se obtiene el segundo elemento de la lista de comentarios
     * Se llama a la logica del recurso de EspectaculoComentario, que se encarga de hacer la union,
     * esta debe devolver un comentarioEntity
     */
    @Test
    public void addComentarioTest()
    {
        EspectaculoEntity espec = espectaculos.get(0);
        
        ComentarioEntity comen = comentarios.get(1);
        
        ComentarioEntity newComen = espectaculoComentarioLogic.addComentario(comen.getId(), espec.getId());
        
        Assert.assertNotNull(newComen);
        Assert.assertEquals(newComen.getId(), comen.getId());
    }
    
    /**
     * Va a hacer la prueba sobre el espectaculo en la posicion 0,
     * que en el insert data se le agrego un comentario
     * El tamaño de la lista deberia ser 0
     */
    @Test 
    public void getComentariosTest()
    {
        
        List<ComentarioEntity> coment = espectaculoComentarioLogic.darComentarios((espectaculos.get(0)).getId());
        System.out.println(coment.size());
        Assert.assertEquals(1, coment.size());
    }
    
    /**
     * 
     * @throws BusinessLogicException 
     */
    @Test 
    public void getComentarioTest() throws BusinessLogicException
    {
        EspectaculoEntity espectaculo = espectaculos.get(0);
        
        ComentarioEntity comentario = comentarios.get(0);
        
        ComentarioEntity newComen = espectaculoComentarioLogic.getComentario(espectaculo.getId(), comentario.getId());
        
        Assert.assertEquals(comentario.getId(), newComen.getId());
        Assert.assertEquals(comentario.getMensaje(), newComen.getMensaje());
    }
    
    /**
     * Prueba para obtener un comentario que no esta asociado a un espectaculo
     * se eligen el espectaculo 0 y comentario 1 para hacer la prueba
     * No deberia estar asociado, deberia lanzar excepcion
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void obtenerUnComentarioNoAsociadoTest() throws BusinessLogicException
    {
        EspectaculoEntity espec = espectaculos.get(0);
        
        ComentarioEntity comen = comentarios.get(1);
        
        espectaculoComentarioLogic.getComentario(espec.getId(), comen.getId());
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.logic;

import co.edu.uniandes.csw.boletas.ejb.EspectaculoLogic;
import co.edu.uniandes.csw.boletas.ejb.OrganizadorEspectaculoLogic;
import co.edu.uniandes.csw.boletas.ejb.OrganizadorLogic;
import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import co.edu.uniandes.csw.boletas.entities.OrganizadorEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.EspectaculoPersistence;
import co.edu.uniandes.csw.boletas.persistence.OrganizadorPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import junit.framework.Assert;
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
 * @author Sebastian Rodriguez Beltran
 */
@RunWith(Arquillian.class)
public class OrganizadorEspectaculoLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private OrganizadorLogic organizadorLogic;
    
    @Inject 
    private OrganizadorEspectaculoLogic organizadorEspectaculoLogic;
    
    @PersistenceContext 
    private EntityManager em;
    
    @Inject 
    private UserTransaction utx;
    
    private List<OrganizadorEntity> organizadores = new ArrayList<OrganizadorEntity>();
    
    private List<EspectaculoEntity> espectaculos = new ArrayList<EspectaculoEntity>();
    
    
     @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(OrganizadorEntity.class.getPackage())
                .addPackage(OrganizadorLogic.class.getPackage())
                .addPackage(OrganizadorPersistence.class.getPackage())
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
    
    
    private void clearData() {
        em.createQuery("delete from OrganizadorEntity").executeUpdate();
        em.createQuery("delete from EspectaculoEntity").executeUpdate();
    }
    
    
    /**
     * Metodo que añade valores a los arreglos, con POJO's
     */
    private void insertData()
    {
        for (int i = 0; i < 3; i++) {
            EspectaculoEntity espec = factory.manufacturePojo(EspectaculoEntity.class);
            em.persist(espec);
            espectaculos.add(espec);
        }
        for (int i = 0; i < 3; i++) {
            OrganizadorEntity organizador = factory.manufacturePojo(OrganizadorEntity.class);
            em.persist(organizador);
            organizadores.add(organizador);
            if(i == 0)
            {
                espectaculos.get(i).setOrganizador(organizador);
            }
        }
    }

    @Test
    public void getEspectaculoTest() throws BusinessLogicException
    {
        EspectaculoEntity espec = espectaculos.get(0);
        
        OrganizadorEntity orga = organizadores.get(0);
        
        EspectaculoEntity newEspec  = organizadorEspectaculoLogic.getEspectaculo(orga.getId(), espec.getId());
        
        Assert.assertEquals(espec.getId(), newEspec.getId());
        Assert.assertEquals(espec.getNombre(), newEspec.getNombre());
        Assert.assertEquals(espec.getFecha(), newEspec.getFecha());
        Assert.assertEquals(espec.getTipo(), newEspec.getTipo());
    }

    
    /**
     * Metodo encargado de probar que se agrege correctamente un espectaculo a un organizador
     */
    @Test
    public void addEspectaculoTest()
    {
        EspectaculoEntity espec = espectaculos.get(1);
        
        OrganizadorEntity orga = organizadores.get(0);
        
        EspectaculoEntity newEspec = organizadorEspectaculoLogic.addEspectaculo(espec.getId(), orga.getId());
        
        Assert.assertNotNull(newEspec);
        Assert.assertEquals(espec.getId(), newEspec.getId());
    }
    
    
    
    @Test
    public void getEspectaculosTest()
    {
        List<EspectaculoEntity> espectacul = organizadores.get(0).getEspectaculos();
        
        Assert.assertEquals(1,espectacul.size());
    }
    
    /**
     * Metodo que prueba que obtener un espectaculo no asociado a un organizador
     * Debe lanzar la excepcion, pues no lo deberia encontrar en la logica
     * @throws BusinessLogicException 
     */
    @Test
    public void obtenerUnEspectaculoNoAsociadoTest() throws BusinessLogicException
    {
        EspectaculoEntity espec = espectaculos.get(1);
        
        OrganizadorEntity org = organizadores.get(0);
        
        organizadorEspectaculoLogic.getEspectaculo(espec.getId(), org.getId());
    }
    
    
            
}

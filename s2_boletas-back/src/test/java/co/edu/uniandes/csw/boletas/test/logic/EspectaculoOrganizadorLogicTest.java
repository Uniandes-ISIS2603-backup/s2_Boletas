/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.logic;


import co.edu.uniandes.csw.boletas.ejb.EspectaculoOrganizadorLogic;
import co.edu.uniandes.csw.boletas.ejb.EspectaculoLogic;
import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import co.edu.uniandes.csw.boletas.entities.OrganizadorEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.OrganizadorPersistence;
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
 * Pruebas de logica de la relacion Espectaculo - Organizador
 *
 * @author ISIS2603
 */
@RunWith(Arquillian.class)
public class EspectaculoOrganizadorLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private EspectaculoLogic espectaculoLogic;
    @Inject
    private EspectaculoOrganizadorLogic espectaculoOrganizadorLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<OrganizadorEntity> data = new ArrayList<OrganizadorEntity>();

    private List<EspectaculoEntity> espectaculosData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(OrganizadorEntity.class.getPackage())
                .addPackage(EspectaculoLogic.class.getPackage())
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

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from EspectaculoEntity").executeUpdate();
        em.createQuery("delete from OrganizadorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            EspectaculoEntity espectaculos = factory.manufacturePojo(EspectaculoEntity.class);
            em.persist(espectaculos);
            espectaculosData.add(espectaculos);
        }
        for (int i = 0; i < 3; i++) {
            OrganizadorEntity entity = factory.manufacturePojo(OrganizadorEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                espectaculosData.get(i).setOrganizador(entity);
            }
        }
    }

    /**
     * Prueba para remplazar las instancias de Espectaculos asociadas a una instancia
     * de Organizador.
     */
    @Test
    public void replaceOrganizadorTest() {
        EspectaculoEntity entity = espectaculosData.get(0);
        espectaculoOrganizadorLogic.replaceOrganizador(entity.getId(), data.get(1).getId());
        entity = espectaculoLogic.getEspectaculo(entity.getId());
        Assert.assertEquals(entity.getOrganizador(), data.get(1));
    }

    /**
     * Prueba para desasociar un Espectaculo existente de un Organizador existente
     *
     * @throws co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException
     */
    @Test
    public void removeEspectaculosTest() throws BusinessLogicException {
        espectaculoOrganizadorLogic.removeOrganizador(espectaculosData.get(0).getId());
        EspectaculoEntity response = espectaculoLogic.getEspectaculo(espectaculosData.get(0).getId());
        Assert.assertNull(response.getOrganizador());
    }
}

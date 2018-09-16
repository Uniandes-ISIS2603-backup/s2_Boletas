
package co.edu.uniandes.csw.boletas.test.persistence;
import co.edu.uniandes.csw.boletas.entities.OrganizadorEntity;
import co.edu.uniandes.csw.boletas.persistence.OrganizadorPersistence;
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
 * Pruebas de persistencia de Organizador
 *
 * @author Vilma Tirado Gomez
 */
@RunWith(Arquillian.class)
public class OrganizadorPersistenceTest {

    /**
     * Inyección de la dependencia a la clase organizadorPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private OrganizadorPersistence organizadorPersistence;

    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    
    @PersistenceContext
    private EntityManager em;

    /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    UserTransaction utx;

    /**
     * Lista que tiene los datos de prueba.
     */
    private List<OrganizadorEntity> data = new ArrayList<OrganizadorEntity>();

    /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Editorial, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(OrganizadorEntity.class.getPackage())
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
            em.joinTransaction();
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
     *
     *
     */
    private void clearData() {
        em.createQuery("delete from OrganizadorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     *
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            OrganizadorEntity entity = factory.manufacturePojo(OrganizadorEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Organizador.
     *
     *
     */
    @Test
    public void createOrganizadorTest() {
        PodamFactory factory = new PodamFactoryImpl();
        OrganizadorEntity newEntity = factory.manufacturePojo(OrganizadorEntity.class);
        OrganizadorEntity result = organizadorPersistence.create(newEntity);

        Assert.assertNotNull(result);

        OrganizadorEntity entity = em.find(OrganizadorEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }

    /**
     * Prueba para eliminar una Organizador.
     *
     *
     */
    @Test
    public void deleteOrganizadorTest() {
        OrganizadorEntity entity = data.get(0);
        organizadorPersistence.delete(entity.getId());
        OrganizadorEntity deleted = em.find(OrganizadorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para consultar un Organizador por nombre.
     *
     *
     */
    @Test
    public void FindOrganizadorByNameTest() {
        OrganizadorEntity entity = data.get(0);
        OrganizadorEntity newEntity = organizadorPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }
}
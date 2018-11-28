/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.test.logic;

import co.edu.uniandes.csw.boletas.ejb.LugarEspectaculoLogic;
import co.edu.uniandes.csw.boletas.ejb.LugarLogic;
import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import co.edu.uniandes.csw.boletas.entities.LugarEntity;
import co.edu.uniandes.csw.boletas.persistence.LugarPersistence;
import java.util.Date;
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
 * @author ja.amortegui10
 */
@RunWith(Arquillian.class)
public class LugarEspectaculoLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private LugarLogic lLogic;
    
    @Inject
    private LugarEspectaculoLogic lELogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    
    private List<LugarEntity> lugaresData;
    private List<EspectaculoEntity> espectaculosData;
    
    @Deployment
     public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class )
                .addPackage(LugarEntity.class.getPackage())
                .addPackage(LugarLogic.class.getPackage())
                .addPackage(LugarPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
     
     @Before
     public void congifTest()
    {
        try
        {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        }catch(Exception e1)
        {
            e1.printStackTrace();
            try{
                utx.rollback();
            }catch(Exception e2)
            {
                e2.printStackTrace();
            }
        }
    }
     
     private void clearData()
     {
         em.createQuery("delete from LugarEntity");
         em.createQuery("delete from EspectaculoEntity");
     }
     
     private void insertData()
     {
         for(int i = 0; i <  4; i++)
         {
             LugarEntity lugar = factory.manufacturePojo(LugarEntity.class);
             em.persist(lugar);
             lugaresData.add(lugar);
         }
         for(int i = 0; i < 4; i++)
         {
             EspectaculoEntity espectaculo = factory.manufacturePojo(EspectaculoEntity.class);
             em.persist(espectaculo);
             espectaculosData.add(espectaculo);
             
         }
         try
         {
            lELogic.addEspectaculo(lugaresData.get(0).getId(), espectaculosData.get(0).getId());
            lELogic.addEspectaculo(lugaresData.get(1).getId(), espectaculosData.get(1).getId());
            lELogic.addEspectaculo(lugaresData.get(1).getId(), espectaculosData.get(2).getId());
            lELogic.addEspectaculo(lugaresData.get(2).getId(), espectaculosData.get(3).getId());
         }catch(Exception e)
         {
             Assert.fail();
         }
     }
     
     @Test
     public void getLugaresDisponibles()
     {
         Date fecha = espectaculosData.get(2).getFecha();
         try
         {
             List<LugarEntity> lugaresDisponibles = lELogic.getLugaresDisponiblles(fecha);
             if(lugaresDisponibles == null || lugaresDisponibles.size() < 3)
                 Assert.fail();
             for(LugarEntity lugar: lugaresDisponibles)
             {
                 List<EspectaculoEntity> espectaculos = lugar.getEspectaculos();
                 if(espectaculos != null)
                     for(EspectaculoEntity espectaculo : espectaculos)
                     {
                         Assert.assertFalse(espectaculo.getFecha().equals(fecha));
                     }
                     
             }
         }catch(Exception e)
         {
             Assert.fail();
         }
     }
     
}

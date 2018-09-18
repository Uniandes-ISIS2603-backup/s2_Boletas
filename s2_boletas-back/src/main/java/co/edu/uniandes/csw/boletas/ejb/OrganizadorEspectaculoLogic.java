/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import co.edu.uniandes.csw.boletas.entities.OrganizadorEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.EspectaculoPersistence;
import co.edu.uniandes.csw.boletas.persistence.OrganizadorPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Sebastian Beltran y Vilma Tirado
 */
@Stateless
public class OrganizadorEspectaculoLogic {
    
    
      private static final Logger LOGGER = Logger.getLogger(OrganizadorEspectaculoLogic.class.getName());
      
      
      //Injeccion para la clase organizador persistence 
      @Inject 
      private OrganizadorPersistence organizadorPersistence;
      
      //Injeccion para la clase espectaculo persistence 
      @Inject 
      private EspectaculoPersistence espectaculoPersistence;
      
      
      
          /**
     * Agregar un espectaculo  al la lista del organizador 
     *
     * @param espectaculoId El id del espectaculo 
     * @param organizadorId El id del organizador que ofrece el espectaculo
     * 
     * @return El espectaculo con el organizador añadido.
     */
      
      public EspectaculoEntity addEspectaculo (Long espectaculoId, Long organizadorId)
      {
          LOGGER.log(Level.INFO, "Inicia proceso de agregarle un espectaculo al organizador con id = {0}", organizadorId);
          //Busca el organizador en la base de datos 
          OrganizadorEntity organizadorEntity= organizadorPersistence.find(organizadorId);
          
          //Busca el espectaculo en la base de datos 
          EspectaculoEntity espectaculoEntity = espectaculoPersistence.find(espectaculoId);
          
          //Le asigna al espectaculo su organizador
          espectaculoEntity.setOrganizador(organizadorEntity);
          
           LOGGER.log(Level.INFO, "Inicia proceso de agregarle un espectaculo al organizador con id = {0}", organizadorId);
           
           return espectaculoEntity;
      }
      
          /**
     * Retorna todos los espectaculos  asociados a un organizador
     *
     * @param organizadorId El ID del organizador buscado
     * @return La lista de espectaculos de un organizador.
     */
      
      public List<EspectaculoEntity> getEspectaculos (Long organizadorId)
      {
           LOGGER.log(Level.INFO, "Inicia proceso de consultar los espectaculos  asociados al organizador con id = {0}", organizadorId);
           
           
           return organizadorPersistence.find(organizadorId).getEspectaculos();
      }
      
          /**
     * Retorna un espectaculo asociado a un organizador
     *
     * @param organizadorId El id del espectaculo que se va a buscar
     * @param espectaculoId El id del espectaculo a buscar
     * @return El espectaculo encontrado dentro del organizador.
     * @throws BusinessLogicException Si el espectaculo no es 
     * de ese organizador 
     */
      
      public EspectaculoEntity getEspectaculo (Long organizadorId, Long espectaculoId) throws BusinessLogicException
      {
          //Se busca la entidad de espectaculo en la persistencia
          EspectaculoEntity espectaculoEntity= espectaculoPersistence.find(espectaculoId);
          //Se busca la lista de espectaculos del organizador 
          List<EspectaculoEntity> espectaculos  = organizadorPersistence.find(organizadorId).getEspectaculos();
         // Se busca el indice del espectaculo dentro de la lista del organizador
        int index= espectaculos.indexOf(espectaculoEntity);
        //Se comprueba que si exista ese espectaculo en la lista, sino manda excepcion 
        
        
        if(index<0)
        {
        throw new BusinessLogicException("El espectaculo no pertenece a este organizador");
        }
        
          return  espectaculos.get(index);

        
      }
      
          /**
     * Rempla        throw new BusinessLogicException("El espectaculo no pertenece a este organizador");
zar los espectaculos de un organizador
     *
     * @param espectaculos  Lista de espectaculos  que serán los del organizador.
     * @param organizadorId  El id del organizador que se quiere actualizar.
     * @return Lalista de espectaculos actualizados.
     */
      
      public List<EspectaculoEntity> updateEspectaculos (List<EspectaculoEntity> espectaculos,Long organizadorId)
      {
          OrganizadorEntity organizadorEntity= organizadorPersistence.find(organizadorId);
          List<EspectaculoEntity> listaEspectaculos= espectaculoPersistence.findAll();
          for (EspectaculoEntity espec: listaEspectaculos)
          {
              if (espectaculos.contains(espec))
              {
                  espec.setOrganizador(organizadorEntity);
              }
              else if (espec.getOrganizador()!=null && espec.getOrganizador().equals(organizadorEntity)  )
              {
                  espec.setOrganizador(null);
              }
          }
          return espectaculos;
      }
      
      
      
    
}

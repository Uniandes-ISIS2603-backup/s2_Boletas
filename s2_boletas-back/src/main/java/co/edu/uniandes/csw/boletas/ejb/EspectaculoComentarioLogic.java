/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.ComentarioEntity;
import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.ComentarioPersistence;
import co.edu.uniandes.csw.boletas.persistence.EspectaculoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Diego Camacho y Sebastian Rodriguez
 */
@Stateless
public class EspectaculoComentarioLogic 
{
    
    private static final Logger LOGGER = Logger.getLogger(EspectaculoComentarioLogic.class.getName());

    @Inject
    private EspectaculoPersistence espectaculoPersistence;

    @Inject
    private ComentarioPersistence comentarioPersistence;
    
    
    /**
     * Metodo para agregar un comentario a un espectaculo
     * @param comentarioId Id del comentario a agregar
     * @param espectaculoId Id de espectaculo al que se le va a agregar el comentario
     * @return Un comentario que llega
     */
    public ComentarioEntity addComentario(Long comentarioId, Long espectaculoId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un comentario a una editorial con id = {0}", espectaculoId);
        EspectaculoEntity espectaculoEntity = espectaculoPersistence.find(espectaculoId);
        ComentarioEntity comentarioEntity = comentarioPersistence.find(comentarioId);
        comentarioEntity.setEspectaculo(espectaculoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un comentario a una editorial con id = {0}", espectaculoEntity);
        return comentarioEntity;
    } 
    
    
    /**
     * Devuelve una lista de los comentarios asociados a un espectaculo, dado su ID
     * @param espectaculoId
     * @return 
     */
    public List<ComentarioEntity> darComentarios(Long espectaculoId)
    {
        EspectaculoEntity espectaculo = espectaculoPersistence.find(espectaculoId);
        
        return espectaculo.getComentarios();
    }
    
    
    /**
     * Metodo que obtiene un comentario, dado su id, y el id del espectaculo asociado
     * @param espectaculoId Espectaculo que debe contener el comentario
     * @param comentarioId El id del comentario a buscar 
     * @return Una instancia de ComentarioEntity 
     * @throws BusinessLogicException En caso de que no se encuentre asociado a ese espectaculo
     */
    public ComentarioEntity getComentario(Long espectaculoId, Long comentarioId) throws BusinessLogicException
    {
        
        LOGGER.log(Level.INFO, "Inicia proceso de obtener el comentario de un espectaculo asociado");
        
        List<ComentarioEntity> comentarios = espectaculoPersistence.find(espectaculoId).getComentarios();
        
        ComentarioEntity comentario = comentarioPersistence.find(comentarioId);
        
        int index = comentarios.indexOf(comentario);
        
        if(index >= 0)
        {
            return comentarios.get(index);
        }
        
        throw new BusinessLogicException("El comentario no estaba asociado a el espectaculo");
    }
    
    
    /**
     * Metodo que remplaza los comentarios de un espectaculo, con una lista que le entra como parametro
     * @param espectaculosId, el Espectaculo a cambiar comentarios
     * @param comentarios Una lista de ComentariosEntity para ser agregada a  un 
     * espectaculo
     * @return La lista de comentarios que fue agregada al espectaculo
     */
    public List<ComentarioEntity> remplazarComentarios(Long espectaculosId, List<ComentarioEntity> comentarios) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el espectaculo con id = {0}", espectaculosId);
        EspectaculoEntity espectaculoEntity = espectaculoPersistence.find(espectaculosId);
        List<ComentarioEntity> listaComentarios = comentarioPersistence.findAll();
        for (ComentarioEntity comentario : listaComentarios) {
            if (listaComentarios.contains(comentario)) {
                comentario.setEspectaculo(espectaculoEntity);
            } else if (comentario.getEspectaculo() != null && comentario.getEspectaculo().equals(espectaculoEntity)) {
                comentario.setEspectaculo(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la editorial con id = {0}", espectaculosId);
        return listaComentarios;
    }
    
}

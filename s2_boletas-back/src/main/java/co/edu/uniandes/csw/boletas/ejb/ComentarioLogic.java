/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.ComentarioEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.ComentarioPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class ComentarioLogic {
    private static final Logger LOGGER = Logger.getLogger(ComentarioLogic.class.getName());
   
    @Inject
    private ComentarioPersistence persistence;
    
    public ComentarioEntity createComentario(ComentarioEntity comentario) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia el proceso de la creación del comentario");
      //  if(comentario.getCliente()==null)
       // {
       //     throw new BusinessLogicException("El comentario debe tener un cliente que lo realizó");
       // }
      //  if(comentario.getEspectaculo() == null )
       // {
      //      throw new BusinessLogicException("El comentario debe estar asociado a un espectáculo");
       // }
        persistence.create(comentario);
        LOGGER.log(Level.INFO, "Termina proceso de la creación del comentario");
        return comentario;
    }
    
    public void deleteComentario(Long comentarioId)
    {
        LOGGER.log(Level.INFO, "Inicia el proceso de la eliminación de un comentario con id = {0}", comentarioId);
        persistence.delete(comentarioId);
        LOGGER.log(Level.INFO, "Termina proceso de eliminar un comentario con id = {0}", comentarioId);
    }
    
}
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
    
    public ComentarioEntity addComentario(Long comentarioId, Long espectaculoId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un libro a la editorial con id = {0}", espectaculoId);
        EspectaculoEntity espectaculoEntity = espectaculoPersistence.find(espectaculoId);
        ComentarioEntity comentarioEntity = comentarioPersistence.find(comentarioId);
        comentarioEntity.setEspectaculo(espectaculoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un libro a la editorial con id = {0}", espectaculoEntity);
        return comentarioEntity;
    } 
    
    public List<ComentarioEntity> darComentarios(Long espectaculoId)
    {
        EspectaculoEntity espectaculo = espectaculoPersistence.find(espectaculoId);
        
        return espectaculo.getComentarios();
    }
    
    public ComentarioEntity getComentario(Long espectaculoId, Long comentarioId) throws BusinessLogicException
    {
        List<ComentarioEntity> comentarios = espectaculoPersistence.find(espectaculoId).getComentarios();
        
        ComentarioEntity comentario = comentarioPersistence.find(comentarioId);
        
        int index = comentarios.indexOf(comentario);
        
        if(index >= 0)
        {
            return comentarios.get(index);
        }
        
        throw new BusinessLogicException("El comentario no estaba asociado a el espectaculo");
    }
}

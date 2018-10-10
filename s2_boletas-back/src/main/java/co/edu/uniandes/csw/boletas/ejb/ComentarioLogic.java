/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import co.edu.uniandes.csw.boletas.entities.ClienteEntity;
import co.edu.uniandes.csw.boletas.entities.ComentarioEntity;
import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.BoletaPersistence;
import co.edu.uniandes.csw.boletas.persistence.ClientePersistence;
import co.edu.uniandes.csw.boletas.persistence.ComentarioPersistence;
import co.edu.uniandes.csw.boletas.persistence.CompraPersistence;
import co.edu.uniandes.csw.boletas.persistence.EspectaculoPersistence;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *  
 * 
 * @author Diego Camacho
 */
@Stateless
public class ComentarioLogic {
    private static final Logger LOGGER = Logger.getLogger(ComentarioLogic.class.getName());
   
    @Inject
    private ComentarioPersistence persistence;
    
    @Inject
    private ClientePersistence clientePersistence;
    
    @Inject
    private EspectaculoPersistence espectaculoPersistence;
    
    @Inject
    private BoletaPersistence boletaPersistence;
    
    @Inject 
    private CompraPersistence compraPersistence;
    
    public ComentarioEntity createComentario(ComentarioEntity comentario) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia el proceso de la creación del comentario");
        if(comentario.getCliente()==null || clientePersistence.find(comentario.getCliente().getId())== null)
        {
            throw new BusinessLogicException("El comentario debe tener un cliente que lo realizó");
        }
        if(comentario.getEspectaculo() == null || espectaculoPersistence.find(comentario.getEspectaculo().getId())==null )
        {
            throw new BusinessLogicException("El comentario debe estar asociado a un espectáculo");
        }
        if(comentario.getMensaje()==null || comentario.getMensaje().equals(""))
        {
            throw new BusinessLogicException("El comentario debe tener un contenido");
        }
        ClienteEntity cliente = clientePersistence.find(comentario.getCliente().getId());
        if(cliente.getCompras()==null)
        {
            throw new BusinessLogicException("El cliente no tiene compras");
        }
        boolean corresponde = false;
        for(CompraEntity compra: cliente.getCompras())
        {
            if(compra.getBoletas()!=null && compraPersistence.find(compra.getId())!=null&&compraPersistence.find(compra.getId()).getBoletas()!=null )
            {
                for(BoletaEntity boleta: compraPersistence.find(compra.getId()).getBoletas())
                {
                    if(boleta.getEspectaculo()!=null && boletaPersistence.find(boleta.getId())!=null && boletaPersistence.find(boleta.getId()).getEspectaculo()!=null )
                    {
                        if(boletaPersistence.find(boleta.getId()).getEspectaculo()==comentario.getEspectaculo())
                        {
                            corresponde = true;
                        }
                    }
                }
            }
        }
        if(!corresponde)
        {
            throw new BusinessLogicException("El cliente que quiere hacer el comentario no tiene boletas de este espectaculo");
        }
        Date ya = new Date();
        if(comentario.getEspectaculo().getFecha()!=null && comentario.getEspectaculo().getFecha().after(ya))
        {
            throw new BusinessLogicException("No se puede comentar ya que el espectaculo aun no se ha dado");
        }
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
    
    public List<ComentarioEntity> getComentarios() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas los comentarios");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<ComentarioEntity> comentarios = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los comentarios");
        return comentarios;
    }
    public ComentarioEntity getComentario(Long comentarioId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el comentario con id = {0}", comentarioId);
        ComentarioEntity comentarioEntity = persistence.find(comentarioId);
        if (comentarioEntity == null) {
            LOGGER.log(Level.SEVERE, "el comentario con el id = {0} no existe", comentarioId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la boleta con id = {0}", comentarioId);
        return comentarioEntity;
    }
    /**
     * Actualizar un comentario por un id
     * 
     * @param comentariosId El id del comentario a actualizar
     * @param comentarioEntity La entidad del comentario con los cambios deseados
     * @return La entidad del comentario luego de actualizarla
     */
    public ComentarioEntity updateComentario(Long comentariosId, ComentarioEntity comentarioEntity)
    {
      LOGGER.log(Level.INFO, "Inicia proceso de actualizar el comentario con id = {0}", comentariosId);
        ComentarioEntity newEntity = persistence.update(comentarioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el comentario con id = {0}", comentarioEntity.getId());
        return newEntity;  
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;

import co.edu.uniandes.csw.bibilioteca.entities.ComentarioEntity;

/**
 *
 * @author estudiante
 */
public class ComentarioDTO {
    public Long comentarioID;
    
    public String mensaje;
    
    public ComentarioDTO(){
    }
    
    public ComentarioDTO(ComentarioEntity comentario)
    {
        comentarioID = comentario.getId();
        mensaje = comentario.getMensaje();
    }
    
    public ComentarioEntity toEntity()
    {
       ComentarioEntity comentario = new ComentarioEntity();
       comentario.setId(comentarioID);
       comentario.setMensaje(mensaje);
       return comentario;
    }

    public Long getComentarioID() {
        return comentarioID;
    }

    public void setComentarioID(Long comentarioID) {
        this.comentarioID = comentarioID;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
}

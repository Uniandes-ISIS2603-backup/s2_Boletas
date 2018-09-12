/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;

import co.edu.uniandes.csw.boletas.entities.ComentarioEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
        if(comentario!= null)
        {   
            comentarioID = comentario.getId();
            mensaje = comentario.getMensaje();
        }
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
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}

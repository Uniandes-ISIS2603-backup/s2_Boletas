/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;

import co.edu.uniandes.csw.boletas.entities.ComentarioEntity;
import co.edu.uniandes.csw.boletas.entities.ClienteEntity;
import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Vilma Tirado Gomez
 */
public class ClienteDetailDTO extends ClienteDTO implements Serializable{
    
    public List <ComentarioDTO> comentarios;
    
    public List<CompraDTO> compras;
    
    
       public ClienteDetailDTO() {
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param clienteEntity La entidad de la cliente para transformar a DTO.
     */
    public ClienteDetailDTO(ClienteEntity clienteEntity) {
        super(clienteEntity);
        if (clienteEntity != null) {
            if (clienteEntity.getComentarios() != null) {
                comentarios = new ArrayList<>();
                for (ComentarioEntity entityComentario : clienteEntity.getComentarios()) {
                    comentarios.add(new ComentarioDTO(entityComentario));
                }
            }
        }
                if (clienteEntity != null) {
            if (clienteEntity.getComentarios() != null) {
                compras = new ArrayList<>();
                for (CompraEntity entityCompra : clienteEntity.getCompras()) {
                    compras.add(new CompraDTO(entityCompra));
                }
            }
        }
    }

    /**
     * Transformar un DTO a un Entity
     *
     * @return El DTO de la cliente para transformar a Entity
     */
    @Override
    public ClienteEntity toEntity() {
        ClienteEntity clienteEntity = super.toEntity();
        if (comentarios != null) {
            List<ComentarioEntity> comentariosEntity = new ArrayList<>();
            for (ComentarioDTO dtoComentario : comentarios) {
                comentariosEntity.add(dtoComentario.toEntity());
            }
            clienteEntity.setComentarios(comentariosEntity);
        }
        return clienteEntity;
    }

    /**
     * Devuelve la lista de libros de la cliente.
     *
     * @return the comentarios
     */
    public List<ComentarioDTO> getComentario() {
        return comentarios;
    }

    /**
     * Modifica la lista de libros de la cliente.
     *
     * @param comentarios the comentarios to set
     */
    public void setComentarios(List<ComentarioDTO> comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
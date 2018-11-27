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
    
    /**
     * Lista de comentarios hechos por el cliente
     * */
    private List <ComentarioDTO> comentarios;
    
        /**
     * Lista de compras hechos por el cliente
     * */
    private List<CompraDTO> compras;
    
    
       public ClienteDetailDTO() {
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param clienteEntity La entidad de la cliente para transformar a DTO.
     */
    public ClienteDetailDTO(ClienteEntity clienteEntity) {
        super(clienteEntity);
        if (clienteEntity != null && clienteEntity.getComentarios() != null)
        {
            comentarios = new ArrayList<>();
            for (ComentarioEntity entityComentario : clienteEntity.getComentarios()) {
                comentarios.add(new ComentarioDTO(entityComentario));
            }
            
            compras = new ArrayList<>();
            for (CompraEntity entityCompra : clienteEntity.getCompras()) {
                compras.add(new CompraDTO(entityCompra));
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
        if (compras!=null)
        {
            List<CompraEntity> compraEntity = new ArrayList<>();
            for (CompraDTO dtoCompra: compras)
            {
                compraEntity.add(dtoCompra.toEntity());
            }
            clienteEntity.setCompras(compraEntity);
        }
        return clienteEntity;
    }

    /**
     * Devuelve la lista de comentarios del cliente.
     *
     * @return the comentarios
     */
    public List<ComentarioDTO> getComentarios() {
        return comentarios;
    }

    /**
     * Modifica la lista de comentarios del cliente
     * @param comentarios the comentarios to set
     */
    public void setComentarios(List<ComentarioDTO> comentarios) {
        this.comentarios = comentarios;
    }
    
        /**
     * Devuelve la lista de compras del cliente.
     *
     * @return the compra
     */
        public List<CompraDTO> getCompras() {
        return compras;
    }

         /* * Modifica la lista de compras del cliente
     * @param lista de compras para arreglar
     */
    public void setCompras(List<CompraDTO> compras) {
        this.compras = compras;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}

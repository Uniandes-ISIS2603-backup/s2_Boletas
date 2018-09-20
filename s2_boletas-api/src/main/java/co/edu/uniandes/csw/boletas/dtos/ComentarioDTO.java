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
 *Clase que representa un comentario
 * @author Diego Camacho
 */
public class ComentarioDTO {
    
    /**
     * Id del comentario
     */
    private Long id;
    
    /**
     * Mensaje que contiene el comentario
     */
    private String mensaje;
    
    /**
     * Espectaculo al que pertenece el comentario
     */
    private EspectaculoDTO espectaculo;
    
    /**
     * Cliente al que pertenece el comentario
     */
    private ClienteDTO cliente;
            
    
    /**
     * Constructor vacío de la clase
     */
    public ComentarioDTO(){
    }
    
    /**
     * Constructor que recibe un comentarioEntity y lo convierte a un DTO
     * @param comentario Entity que se va a convertir a DTO
     */
    public ComentarioDTO(ComentarioEntity comentario)
    {
        if(comentario!= null)
        {   
            id = comentario.getId();
            mensaje = comentario.getMensaje();
            if(comentario.getCliente()!=null)
            {
                cliente = new ClienteDTO(comentario.getCliente());
            }
            if(comentario.getEspectaculo()!=null)
            {
                espectaculo = new EspectaculoDTO(comentario.getEspectaculo());
            }
            
        }
    }
    
    /**
     * Metodo que transforma un comentario DTO a un tipo Entity
     * @return regresa un comentario en tipo entity
     */
    public ComentarioEntity toEntity()
    {
       ComentarioEntity comentario = new ComentarioEntity();
       comentario.setId(id);
       comentario.setMensaje(mensaje);
       if(espectaculo!=null)
       {
           comentario.setEspectaculo(espectaculo.toEntity());
       }
       if(cliente!=null)
       {
           comentario.setCliente(cliente.toEntity());
       }
       return comentario;
    }

    /**
     * Retorna el id de un comentario
     * @return id de comentario
     */
    public Long getComentarioID() {
        return id;
    }

    /**
     * Asigna un identificador a un comentario
     * @param comentarioID id a asignar
     */
    public void setComentarioID(Long comentarioID) {
        this.id = comentarioID;
    }

    /**
     * Retorna el mensaje del que va a estar compuesto el comentario
     * @return mensaje del comentario
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Asigna un mensaje al comentario
     * @param mensaje contenido del comentario
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public EspectaculoDTO getEspectaculo() {
        return espectaculo;
    }

    public void setEspectaculo(EspectaculoDTO espectaculo) {
        this.espectaculo = espectaculo;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }
    
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}

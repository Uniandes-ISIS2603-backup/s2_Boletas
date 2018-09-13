/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;


import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * Clase que representa una boleta
 * @author Diego Camacho
 */
public class BoletaDTO implements Serializable {
    /**
     * Identificador de la boleta
     */
    private Long id;
    
    /**
     * Precio de la boleta
     */
    private Integer precio;
    
    /**
     * Fecha a la que corresponde la realización del evento en la boleta
     */
    private Date fecha;
    
    /**
     * Constructor vacío de una boleta.
     */
    public BoletaDTO()
    {
        
    }
    

    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param boleta: Es la entidad que se va a convertir a DTO
     */
    public BoletaDTO(BoletaEntity boleta)
    {
        if(boleta!=null)
        {
            id = boleta.getId();
            precio = boleta.getPrecio();
            fecha = boleta.getFecha();
        }
    }
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public BoletaEntity toEntity()
    {
        BoletaEntity boleta = new BoletaEntity();
        boleta.setId(id);
        boleta.setPrecio(precio);
        boleta.setFecha(fecha);
        return boleta;
    }

    /**
     * retorna el identificador de la boleta
     * @return id de Boleta
     */
    public Long getID() {
        return id;
    }
    
    /**
     * Asigna un identificador a la boleta
     * @param boletaID id a asignar a la boleta
     */
    public void setidID(Long boletaID) {
        this.id = boletaID;
    }

    /**
     * Da el precio de la boleta 
     * @return precio de la boleta
     */
    public Integer getPrecio() {
        return precio;
    }
    
    /**
     * Asigna un precio a una boleta
     * @param precio el precio que se le va a asignar a la boleta 
     */
    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    /**
     * Da la fecha del evento al que corresponde la boleta.
     * @return fecha del evento de la boleta
     */
    public Date getFecha() {
        return fecha;
    }
    
    /**
     * Asigna una fecha a una boleta 
     * @param fecha fecha a asignar 
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    
}

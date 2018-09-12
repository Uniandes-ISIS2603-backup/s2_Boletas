/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;

/**
 *
 * @author Diego Camacho
 */
import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BoletaDTO implements Serializable {
    
    private Long id;
    
    private Integer precio;
    
    private Date fecha;
    
    public BoletaDTO()
    {
        
    }
    
    public BoletaDTO(BoletaEntity boleta)
    {
        if(boleta!=null)
        {
            id = boleta.getId();
            precio = boleta.getPrecio();
            fecha = boleta.getFecha();
        }
    }
    
    public BoletaEntity toEntity()
    {
        BoletaEntity boleta = new BoletaEntity();
        boleta.setId(id);
        boleta.setPrecio(precio);
        boleta.setFecha(fecha);
        return boleta;
    }

    public Long getID() {
        return id;
    }

    public void setidID(Long boletaID) {
        this.id = boletaID;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    
}

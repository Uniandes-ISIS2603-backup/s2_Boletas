/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author estudiante
 */
@Entity
public class BoletaEntity extends BaseEntity implements Serializable       
{
    
    private Integer precio;
    
    private Date fecha;
    
    /*
    @PodamExclude
    @ManyToOne
    private EspectaculoEntity espectaculo;

    public EspectaculoEntity getEspectaculo() {
        return espectaculo;
    }

    public void setEspectaculo(EspectaculoEntity espectaculo) {
        this.espectaculo = espectaculo;
    }
    */
    
    
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
    
    
    
}

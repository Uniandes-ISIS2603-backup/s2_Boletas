/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.entities;

import co.edu.uniandes.csw.boletas.podam.DateStrategy;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Diego Camacho
 */
@Entity
public class BoletaEntity extends BaseEntity implements Serializable       
{
    
    private Integer precio;
    
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fecha;

    private Boolean vendida;

   
    @PodamExclude
    @ManyToOne
    private EspectaculoEntity espectaculo;

    @PodamExclude
    @ManyToOne
    private CompraEntity compra;
   
    @PodamExclude
    @OneToOne()
    private SillaEntity silla;

    public SillaEntity getSilla() {
        return silla;
    }

    public void setSilla(SillaEntity silla) {
        this.silla = silla;
    }
    
    public CompraEntity getCompra() {
        return compra;
    }

    public void setCompra(CompraEntity compra) {
        this.compra = compra;
    }
    
    
    
    public EspectaculoEntity getEspectaculo() {
        return espectaculo;
    }

    public void setEspectaculo(EspectaculoEntity espectaculo) {
        this.espectaculo = espectaculo;
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
    
    
    public Boolean getVendida() {
        return vendida;
    }

    public void setVendida(Boolean vendida) {
        this.vendida = vendida;
    }
    
    @Override
    public boolean equals (Object obj)
    {
        return super.equals(obj);
    }
    
    @Override
    public int hashCode() {
        if (this.getId() != null) {
            return this.getId().hashCode();
        }
        return super.hashCode();
    }
    
}

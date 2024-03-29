/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;



/**
 *
 * @author ja.amortegui10
 */
@Entity
public class SillaEntity extends BaseEntity implements Serializable{
 
    private String numero;
    private String tipo;
    private Boolean disponible;
    
    @PodamExclude
    @ManyToOne()
    private LugarEntity lugar;
    
    @PodamExclude
    @OneToOne(mappedBy = "silla", fetch=FetchType.LAZY)
    private BoletaEntity boleta;

    public String getNumero() {
        return numero;
    }
     public String getTipo() {
        return tipo;
    }

    public Boolean getDisponible() {
        return disponible;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public LugarEntity getLugar() {
        return lugar;
    }

    public void setLugar(LugarEntity lugar) {
        this.lugar = lugar;
    }

    public BoletaEntity getBoleta() {
        return boleta;
    }

    public void setBoleta(BoletaEntity boleta) {
        this.boleta = boleta;
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

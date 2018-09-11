/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;



/**
 *
 * @author ja.amortegui10
 */
@Entity
public class SillaEntity extends BaseEntity implements Serializable{
 
    private String numero;
    private String tipo;
    
    @PodamExclude
    @ManyToOne()
    private LugarEntity lugar;
    
    
    public void setLugar(LugarEntity lugar) {
        this.lugar = lugar;
    }

    public LugarEntity getLugar() {
        return lugar;
    }


    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
    
}

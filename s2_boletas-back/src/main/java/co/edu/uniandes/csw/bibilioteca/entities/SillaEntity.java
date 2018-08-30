/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bibilioteca.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;



/**
 *
 * @author ja.amortegui10
 */
@Entity
public class SillaEntity extends BaseEntity implements Serializable{
    
    public enum TipoSilla{
        PREFERENCIAL, 
        NORMAL
    }
    
    @Id
    private Long id;
    private String numero;
    private TipoSilla tipo;

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public TipoSilla getTipo() {
        return tipo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setTipo(TipoSilla tipoSilla) {
        this.tipo = tipoSilla;
    }
    
    
}

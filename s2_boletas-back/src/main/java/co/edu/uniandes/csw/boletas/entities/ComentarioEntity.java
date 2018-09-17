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
 * @author estudiante
 */
@Entity
public class ComentarioEntity extends BaseEntity implements Serializable
{
   
    @PodamExclude
    @ManyToOne()
    private EspectaculoEntity espectaculo;
    
    @PodamExclude
    @ManyToOne()
    private ClienteEntity cliente;

    public EspectaculoEntity getEspectaculo() {
        return espectaculo;
    }

    public void setEspectaculo(EspectaculoEntity espectaculo) {
        this.espectaculo = espectaculo;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }
    
    private String mensaje;
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
}

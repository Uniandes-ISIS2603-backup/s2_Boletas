/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author estudiante
 */
@Entity
public class ClienteEntity extends BaseEntity implements Serializable {
    
    private String usuario;
    private String nombre;
    private String cedula;
    private String pago; 
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }
    
    public String getUsuario()
    {
        return usuario;
    }
    public void setUsuario(String usuario)
    {
        this.usuario=usuario;
    }
    
    public void setCedula( String cedula)
    {
        this.cedula=cedula;
    }
    public String getCedula()
    {
        return cedula;
    }
}
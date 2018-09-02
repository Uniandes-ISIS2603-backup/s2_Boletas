/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author estudiante
 */
@Entity
public class ClienteEntity extends BaseEntity implements Serializable {
    
    private String usuario;
    private String nombre;
    private String password;
    private int cedula;
    private String pago; 
    
    @Id
    public Long clienteId;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
    
    public String getUsuario()
    {
        return usuario;
    }
    
    public void setUsuario(String usuario)
    {
        this.usuario=usuario;
    }
    
    public int getCedula()
    {
        return cedula;
    }
    
    public void setCedula(int cedula)
    {
        this.cedula=cedula;
    }
   
    

    
}
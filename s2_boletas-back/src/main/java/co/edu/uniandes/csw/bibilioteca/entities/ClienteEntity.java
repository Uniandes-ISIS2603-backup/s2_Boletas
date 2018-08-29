/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bibilioteca.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author estudiante
 */
@Entity
public class ClienteEntity extends BaseEntity implements Serializable {
    
    private String nombre;
    private String password;
    private String pago; 
    
    public String darNombre()
    {
        return nombre;
    }
    
    public String darPassword()
    {
        return password;
    }
    
    public String darPago()
    {
        return pago;
    }
    
    public void cambiarPago (String pago)
    {
        this.pago= pago;
    }
    
}
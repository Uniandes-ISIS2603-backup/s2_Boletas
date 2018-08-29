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
public class OrganizadorEntity extends BaseEntity implements Serializable{
    
    private String nombre;
    private String password;
    private int presupuesto;
    private int ganancia;
    
    public String darNombre()
    {
        return nombre;
    }
    
    public String darPassword()
    {
        return password;
    }
    public int darPresupuesto()
    {
        return presupuesto;
    }
    
    public int darGanancia()
    {
        return ganancia;
    }
    
    public void cambiarGanancia(int gana)
    {
        this.ganancia= gana;
    }
    public void cambiarPresupuesto (int presup)
    {
        this.presupuesto=presup;
    }
    
}
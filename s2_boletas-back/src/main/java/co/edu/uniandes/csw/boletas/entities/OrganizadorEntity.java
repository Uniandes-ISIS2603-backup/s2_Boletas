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
public class OrganizadorEntity extends BaseEntity implements Serializable{
    
    private String nombre;
    private String password;
    private int presupuesto;
    private int ganancia;
    @Id 
    public Long organizadorId;
    
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
    
    public void setNombre(String nom)
    {
        this.nombre=nom;
    }
    public void setPassword (String pass)
    {
        this.password=pass;
    }
    
    public void setGanancia(int gana)
    {
        this.ganancia= gana;
    }
    public void setPresupuesto (int presup)
    {
        this.presupuesto=presup;
    }
    
    public void setId(Long id)
    {
        organizadorId=id;
    }
    
    public Long darId()
    {
        return organizadorId;
    }
    
}
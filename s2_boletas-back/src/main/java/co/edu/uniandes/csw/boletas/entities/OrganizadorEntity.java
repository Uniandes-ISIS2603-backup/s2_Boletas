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
public class OrganizadorEntity extends BaseEntity implements Serializable{
    
    private String nombre;
    private Integer  presupuesto;
    private Integer ganancia;
    
    public String getNombre()
    {
        return nombre;
    }
    public Integer getPresupuesto()
    {
        return presupuesto;
    }
    
    public Integer  getGanancia()
    {
        return ganancia;
    }
    
    public void setNombre(String nom)
    {
        this.nombre=nom;
    }
    
    public void setGanancia(Integer ganancia)
    {
        this.ganancia= ganancia;
    }
    public void setPresupuesto (Integer presup)
    {
        this.presupuesto=presup;
    }
    
}
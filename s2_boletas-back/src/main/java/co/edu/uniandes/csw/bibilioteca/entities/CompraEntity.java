/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bibilioteca.entities;

import java.util.Date;

/**
 *
 * @author estudiante
 */
public class CompraEntity {
    
    private long id;
    private int costoTotal;
    private boolean envio;
    private  Date fecha;
    private String direccion;    
    
    public long getId()
    {
        return id;
    }
    
    public int getCostoTotal()
    {
        return costoTotal;
    }
    
    public boolean getEnvio()
    {
        return envio;
    }
    
    public Date getFecha()
    {
        return fecha;
    }
    
    public String getDireccion()
    {
        return direccion;
    }
    
    public void setId(long idE)
    {
        id=idE;
    }
    
    public void setCosto(int costoT)
    {
        costoTotal= costoT;
    }
    
    public void setEnvio(boolean envioP)
    {
        envio= envioP;
    }
     
    public void setDireccion(String direccionE)
    {
        direccion= direccionE;
    }
    
    public void setFecha(Date fechaE)
    {
        fecha= fechaE;
    }
    
}
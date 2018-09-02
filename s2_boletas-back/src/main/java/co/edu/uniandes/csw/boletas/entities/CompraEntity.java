/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.entities;

import java.util.Date;

/**
 *
 * @author estudiante
 */
public class CompraEntity {
    
    private Long id;
    private Integer costoTotal;
    private Boolean envio;
    private  Date fecha;
    private String direccion;    
    
    public Long getId()
    {
        return id;
    }
    
    public Integer getCostoTotal()
    {
        return costoTotal;
    }
    
    public Boolean getEnvio()
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
    
    public void setId(Long idE)
    {
        id=idE;
    }
    
    public void setCosto(Integer costoT)
    {
        costoTotal= costoT;
    }
    
    public void setEnvio(Boolean envioP)
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Gabriel Hamilton
 */
@javax.persistence.Entity
public class CompraEntity extends BaseEntity implements Serializable{
    
    private Long id;
    
    private Integer costoTotal;
    
    private Boolean envio;
    
    private Date fecha;
    
    private String direccion;    
    
    
    //@javax.persistence.OneToMany(
    //fetch = javax.persistence.FetchType.LAZY)
    //Collection<BoletaEntity> boletas;
    
    //@javax.persistence.ManyToOne()
    //ClienteEntity cliente;
    
    /**
     * retorna el Id de la compra
     * @return id
     */
    public Long getId()
    {
        return id;
    }
    
    /*
     * rtorna el costo total de la compra
     * @return costoTotal
     */
    public Integer getCostoTotal()
    {
        return costoTotal;
    }
    
    /**
     * retorna un boolean para saber si se pide o no un envio de la compra
     * @return envio
     */
    public Boolean getEnvio()
    {
        return envio;
    }
    
    
    /**
     * retorna la fecha de la compra
     * @return fecha
     */
    public Date getFecha()
    {
        return fecha;
    }
    
    
    /**
     * retorna la direccion ingresada en caso de que el envio sea true
     * @return direccion
     */
    public String getDireccion()
    {
        return direccion;
    }
    
    
    /**
     * modifica el id con el id ingresado por parametro
     * @param idN, nuevo id
     */
    public void setId(Long idN)
    {
        id=idN;
    }
    
    
    /**
     * modifica el costo de la compra
     * @param costoN, el nuevo costo de la compra
     */
    public void setCosto(Integer costoN)
    {
        costoTotal= costoN;
    }
    
    
    /**
     * modifica si se necesita un envio o no
     * @param envioP, la nueva decicion sobre el envio
     */
    public void setEnvio(Boolean envioP)
    {
        envio= envioP;
    }
     
    
    /**
     * modifica la direccion ingresada
     * @param direccionN , nueva direccion
     */
    public void setDireccion(String direccionN)
    {
        direccion= direccionN;
    }
    
   /**
    * modifica la fecha de la compra
    * @param fechaN , nueva fecha de la compra
    */
    public void setFecha(Date fechaN)
    {
        fecha= fechaN;
    }
    
}

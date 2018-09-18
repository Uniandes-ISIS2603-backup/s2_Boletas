/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import uk.co.jemos.podam.common.PodamExclude;


/**
 *
 * @author Gabriel Hamilton
 */
@javax.persistence.Entity
public class CompraEntity extends BaseEntity implements Serializable{
    


    private Integer costoTotal;
    
    private Boolean envio;
    
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    private String direccion;    
    
    private Boolean estado;
    
    @PodamExclude
    @javax.persistence.OneToMany(
    mappedBy = "compra",
    fetch = javax.persistence.FetchType.LAZY)
    private List<BoletaEntity> boletas;
    
    @PodamExclude
    @javax.persistence.ManyToOne()
    ClienteEntity cliente;
    

    
    /*
     * retorna el costo total de la compra
     * @return costoTotal
     */
    public Integer getCostoTotal()
    {
        return costoTotal;
    }
    
    
     /*
     * retorna un boolean sobre el estado de la compra, vigente = True, cancelada = False
     * @return estado
     */
    public Boolean getEstado()
    {
        return estado;
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
     * devuelve la lista de boletas
     * @return  lista de entidades de boletas
     */
    public List<BoletaEntity> getBoletas()
    {
        return boletas;
    }

    /**
     * devuelve el cliente de la compra
     * @return  lel cliente
     */
    public ClienteEntity getCliente()
    {
        return cliente;
    }
    
    
    /**
     * modifica el costo de la compra
     * @param costoN, el nuevo costo de la compra
     */
    public void setCosto(Integer costoN)
    {
        costoTotal= costoN;
    }
    
     /*
     * modifica el estado de la compra, vigente = True, cancelada = False
     * @return vigente
     */
    public void setEstado(Boolean estado)
    {
        this.estado = estado;
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
    
    /**
     * Modifica las boletas de la compra.
     *
     * @param boletaS Las nuevas boletas.
     */
    public void setBoletas(List<BoletaEntity> boletaS) {
        boletas = boletaS;
    }
    
    /**
     * Modifica el cliente de la compra
     *
     * @param cliente el nuevo cliente de la compra
     */
    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.entities;

import co.edu.uniandes.csw.boletas.podam.DateStrategy;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.FetchType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;


/**
 *
 * @author Gabriel Hamilton
 */
@javax.persistence.Entity
public class CompraEntity extends BaseEntity implements Serializable{
    


    private Integer costoTotal;
    
    private Boolean envio;
    
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fecha;
    
    private String direccion;    
    
    private Boolean estado;
    
    @PodamExclude
    @javax.persistence.OneToMany()
    private List<BoletaEntity> boletas;
    
    @PodamExclude
    @javax.persistence.ManyToOne()
    ClienteEntity cliente;
    
    @PodamExclude
    @javax.persistence.OneToOne(mappedBy = "compra",fetch = FetchType.LAZY)
    DevolucionEntity devolucion;

   
    /*
     * retorna la devolucion asociada a la compra
     * @return devolucion
     */
    public DevolucionEntity getDevolucion() {
        return devolucion;
    }

     /**
     * Modifica la devolucion asociada a la compra.
     * @param devolucion La nueva devolucion.
     */
    public void setDevolucion(DevolucionEntity devolucion) {
        this.devolucion = devolucion;
    }
    

    
    /*
     * retorna el costo total de la compra
     * @return costoTotal
     */
    public Integer getCostoTotal()
    {
        return costoTotal;
    }
    
        /**
     * modifica el costo de la compra
     * @param costoTotal, el nuevo costo de la compra
     */
    public void setCostoTotal(Integer costoTotal)
    {
        this.costoTotal= costoTotal;
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
     * @param envio, la nueva decicion sobre el envio
     */
    public void setEnvio(Boolean envio)
    {
        this.envio= envio;
    }
     
    
    /**
     * modifica la direccion ingresada
     * @param direccion , nueva direccion
     */
    public void setDireccion(String direccion)
    {
        this.direccion= direccion;
    }
    
   /**
    * modifica la fecha de la compra
    * @param fecha , nueva fecha de la compra
    */
    public void setFecha(Date fecha)
    {
        this.fecha= fecha;
    }
    
    /**
     * Modifica las boletas de la compra.
     *
     * @param boletas Las nuevas boletas.
     */
    public void setBoletas(List<BoletaEntity> boletas) {
        this.boletas = boletas;
    }
    
    /**
     * Modifica el cliente de la compra
     *
     * @param cliente el nuevo cliente de la compra
     */
    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }
    
    @Override
    public boolean equals (Object obj)
    {
        return super.equals(obj);
    }
    
    @Override
    public int hashCode() {
        if (this.getId() != null) {
            return this.getId().hashCode();
        }
        return super.hashCode();
    }
    
}

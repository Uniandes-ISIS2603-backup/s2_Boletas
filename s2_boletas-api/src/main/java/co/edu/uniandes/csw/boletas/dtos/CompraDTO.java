/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;
import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import java.io.Serializable;
import java.util.Date;
/**
 *
 * @author Gabriel Hamilton
 */
        

public class CompraDTO implements Serializable {
    
    private Long id;
    private Integer costoTotal;
    private Boolean envio;
    private  Date fecha;
    private String direccion;
    private Boolean estado;
    
    public CompraDTO ()
    {
        
    }
    
    
    public CompraDTO (CompraEntity compra)
    {
        id = compra.getId();
        costoTotal = compra.getCostoTotal();
        envio = compra.getEnvio();
        fecha = compra.getFecha();
        direccion = compra.getDireccion();
        
    }
    
    public CompraEntity toEntity()
    {
        CompraEntity compra = new CompraEntity();
        compra.setId(id);
        compra.setCosto(costoTotal);
        compra.setEnvio(envio);
        compra.setFecha(fecha);
        compra.setDireccion(direccion);
        return compra;
    }
    
    /**
     * retorna el id de la compra
     * @return id
     */
    public Long getId()
    {
        return id;
    }
    
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
     * modifica el id de la compra
     * @param idN , el nuevo id
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
   
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;
import co.edu.uniandes.csw.boletas.adapters.DateAdapter;
import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/**
 *
 * @author Gabriel Hamilton
 */
        

public class CompraDTO implements Serializable {
    
    private Long id;
    private Integer costoTotal;
    private Boolean envio;
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date fecha;
    private String direccion;
    private Boolean estado;
    private ClienteDTO cliente;
    private DevolucionDTO devolucion;
    
    public CompraDTO ()
    {
        
    }
    
    
    public CompraDTO (CompraEntity compra)
    {
        if(compra != null)
        {
        id = compra.getId();
        costoTotal = compra.getCostoTotal();
        envio = compra.getEnvio();
        fecha = compra.getFecha();
        direccion = compra.getDireccion();
        estado= compra.getEstado();
        if (compra.getDevolucion()!=null)
            {
                    devolucion=new DevolucionDTO(compra.getDevolucion());
            }
        if (compra.getCliente()!=null)
            {
                    cliente=new ClienteDTO(compra.getCliente());
            }
            
        }
    }

 
    
    public CompraEntity toEntity()
    {
        CompraEntity compra = new CompraEntity();
        compra.setId(id);
        compra.setCostoTotal(costoTotal);
        compra.setEnvio(envio);
        compra.setFecha(fecha);
        compra.setDireccion(direccion);
        compra.setEstado(estado);
        if(cliente!= null)
        {
            compra.setDevolucion(devolucion.toEntity());
        }
        if(cliente!= null)
        {
            compra.setCliente(cliente.toEntity());
        }
        
        return compra;
    }
    /**
     * retorna la devolucion asociada a la compra
     * @return devolucion
     */
    public DevolucionDTO getDevolucion() {
        return devolucion;
    }
    
    /**
     * modifica la devolucion de la compra
     * @param devolucion , la nueva devolucion
     */
    public void setDevolucion(DevolucionDTO devolucion) 
    {
        this.devolucion = devolucion;
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
     * retorna el cliente
     * @return cliente
     */
    public ClienteDTO getCliente()
    {
        return cliente;
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
     * @param id , el nuevo id
     */
    public void setId(Long id)
    {
        this.id=id;
    }
    
    /**
     * modifica el cliente
     * @param cliente el cliente nuevo
     */
    public void setCliente(ClienteDTO cliente)
    {
        this.cliente = cliente;
    }
    
    
    public void setCostoTotal(Integer costoTotal){
        this.costoTotal=costoTotal;
    }
    
    public Integer getCostoTotal (){
            return costoTotal;
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
   
    
}

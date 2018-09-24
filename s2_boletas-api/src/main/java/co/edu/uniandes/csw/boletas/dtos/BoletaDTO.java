/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;


import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * Clase que representa una boleta
 * @author Diego Camacho
 */
public class BoletaDTO implements Serializable {
    /**
     * Identificador de la boleta
     */
    private Long id;
    
    /**
     * Precio de la boleta
     */
    private Integer precio;
    
    /**
     * Fecha a la que corresponde la realización del evento en la boleta
     */
    private Date fecha;
    
    /**
     * Indica si la boleta está vendida o no
     */
    private Boolean vendida;
    
    /**
     * Espectaculo de la boleta
     */
    private EspectaculoDTO espectaculo;
    
    /**
     * Silla de la boleta
     */
    private SillaDTO silla;
    
    /**
     * Compra a la que pertenece la boleta
     */
    private CompraDTO compra;


    /**
     * Constructor vacío de una boleta.
     */
    public BoletaDTO()
    {
        
    }
    

    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param boleta: Es la entidad que se va a convertir a DTO
     */
    public BoletaDTO(BoletaEntity boleta)
    {
        if(boleta!=null)
        {
            id = boleta.getId();
            precio = boleta.getPrecio();
            fecha = boleta.getFecha();
            vendida = boleta.getVendida();
            if(boleta.getCompra()!=null)
            {
                this.compra= new CompraDTO(boleta.getCompra());
            }
            if(boleta.getEspectaculo()!=null)
            {
                this.espectaculo= new EspectaculoDTO(boleta.getEspectaculo());
            }
            if(boleta.getSilla()!=null)
            {
                this.silla=new SillaDTO(boleta.getSilla());
            }
        }
    }
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public BoletaEntity toEntity()
    {
        BoletaEntity boleta = new BoletaEntity();
        boleta.setId(id);
        boleta.setPrecio(precio);
        boleta.setFecha(fecha);
        boleta.setVendida(vendida);
        if(this.compra!=null){
        boleta.setCompra(compra.toEntity());}
        if(this.espectaculo!=null){
        boleta.setEspectaculo(espectaculo.toEntity());}
        if(this.silla!=null){
        boleta.setSilla(silla.toEntity());}
        return boleta;
    }


    /**
     * Da el precio de la boleta 
     * @return precio de la boleta
     */
    public Integer getPrecio() {
        return precio;
    }
    
    /**
     * Asigna un precio a una boleta
     * @param precio el precio que se le va a asignar a la boleta 
     */
    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    /**
     * Da la fecha del evento al que corresponde la boleta.
     * @return fecha del evento de la boleta
     */
    public Date getFecha() {
        return fecha;
    }
    
    /**
     * Asigna una fecha a una boleta 
     * @param fecha fecha a asignar 
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Retorna el identificador de la boleta
     * @return identificador de la boleta
     */
    public Long getId() {
        return id;
    }

    /**
     * Asigna el id a la boleta
     * @param id el id a asignar
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna si la boleta está vendida o no
     * @return true si la boleta está vendida, false en caso contrario
     */
    public Boolean getVendida() {
        return vendida;
    }

    /**
     * Le asigna a una boleta la caracteristica de estar vendida
     * @param vendida 
     */
    public void setVendida(Boolean vendida) {
        this.vendida = vendida;
    }
    
    /**
     * Da el espectaculo de la boleta
     * @return espectaculo de la boleta
     */
    public EspectaculoDTO getEspectaculo() {
        return espectaculo;
    }

    /**
     * Le asigna un espectaculo a una boleta
     * @param espectaculo el espectaculo a asignar
     */
    public void setEspectaculo(EspectaculoDTO espectaculo) {
        this.espectaculo = espectaculo;
    }
    
    /**
     * Da la silla que tiene asignada la boleta
     * @return la silla de la boleta
     */
    public SillaDTO getSilla() {
        return silla;
    }
    
    /**
     * Le asigna una silla a una boleta
     * @param silla la silla a asignar
     */
    public void setSilla(SillaDTO silla) {
        this.silla = silla;
    }
    
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    
}

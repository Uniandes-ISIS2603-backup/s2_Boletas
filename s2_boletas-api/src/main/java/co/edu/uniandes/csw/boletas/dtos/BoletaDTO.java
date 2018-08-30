/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;

/**
 *
 * @author Diego Camacho
 */
import co.edu.uniandes.csw.bibilioteca.entities.BoletaEntity;
import java.io.Serializable;
import java.util.Date;

public class BoletaDTO implements Serializable {
    
    public Long boletaID;
    
    public Integer precio;
    
    public Date fecha;
    
    public BoletaDTO()
    {
        
    }
    
    public BoletaDTO(BoletaEntity boleta)
    {
        boletaID = boleta.getId();
        precio = boleta.getPrecio();
        fecha = boleta.getFecha();
    }
    
    public BoletaEntity toEntity()
    {
        BoletaEntity boleta = new BoletaEntity();
        boleta.setId(boletaID);
        boleta.setPrecio(precio);
        boleta.setFecha(fecha);
        return boleta;
    }

    public Long getBoletaID() {
        return boletaID;
    }

    public void setBoletaID(Long boletaID) {
        this.boletaID = boletaID;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
    
}

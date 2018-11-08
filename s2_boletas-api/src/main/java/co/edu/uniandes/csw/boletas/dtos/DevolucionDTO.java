/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;

import co.edu.uniandes.csw.boletas.entities.DevolucionEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriel Hamilton
 */
public class DevolucionDTO implements Serializable
{
    private Long id;
    private CompraDTO compra;
    private List<Long> boletas;
    
    
    public DevolucionDTO ()
    {
        
    }
    
    
    public DevolucionDTO (DevolucionEntity devolucion)
    {
        if(devolucion != null)
        {
            id = devolucion.getId();
            if (devolucion.getCompra()!=null)
            {
                    compra=new CompraDTO(devolucion.getCompra());
            }
            if (devolucion.getBoletas() != null) {
                boletas = new ArrayList<>();
                for (long boleta : devolucion.getBoletas()) {
                    boletas.add(boleta);
                }
            }
        }
                
    }

    public List<Long> getBoletas() {
        return boletas;
    }

    public void setBoletas(List<Long> boletas) {
        this.boletas = boletas;
    }
    
    public DevolucionEntity toEntity()
    {
        DevolucionEntity devolucion = new DevolucionEntity();
        devolucion.setId(id);
       
        if(compra!= null)
        {
            devolucion.setCompra(compra.toEntity());
        }
        
        return devolucion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompraDTO getCompra() {
        return compra;
    }

    public void setCompra(CompraDTO compra) {
        this.compra = compra;
    }

}

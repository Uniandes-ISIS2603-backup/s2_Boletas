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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Gabriel Hamilton
 */
@javax.persistence.Entity
public class DevolucionEntity extends BaseEntity implements Serializable
{

    
  
    private List<Long> boletas;
    
    @PodamExclude
    @javax.persistence.OneToOne()
    CompraEntity compra;

    public List<Long> getBoletas() {
        return boletas;
    }

    public void setBoletas(List<Long> boletas) {
        this.boletas = boletas;
    }   

    public CompraEntity getCompra() {
        return compra;
    }

    public void setCompra(CompraEntity compra) {
        this.compra = compra;
    }
    
    
}

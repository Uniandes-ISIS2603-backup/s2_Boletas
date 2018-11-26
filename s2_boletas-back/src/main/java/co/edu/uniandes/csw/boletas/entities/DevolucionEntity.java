/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.entities;

import java.io.Serializable;
import java.util.List;
import uk.co.jemos.podam.common.PodamExclude;

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

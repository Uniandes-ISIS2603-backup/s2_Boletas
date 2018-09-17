/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;

import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import co.edu.uniandes.csw.boletas.entities.CompraEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriel Hamilton
 */
public class CompraDetailDTO extends CompraDTO implements Serializable {
    
   /**
    * Esta lista de tipo BoletaDTO contiene las boletas que estan asociadas a una compra
    */
    private List<BoletaDTO> boletas;

    
    
    private ClienteDTO cliente;
    
    /**
     * Constructor por defecto
     */
    public CompraDetailDTO() {
    }
    
    
    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param compraEntity La entidad de la compra para transformar a DTO.
     */
    public CompraDetailDTO(CompraEntity compraEntity) {
        super(compraEntity);
        if (compraEntity != null) {
            if (compraEntity.getBoletas() != null) {
                boletas = new ArrayList<>();
                for (BoletaEntity entityBoleta : compraEntity.getBoletas()) {
                    boletas.add(new BoletaDTO(entityBoleta));
                }
            }
            
            if (compraEntity.getCliente()!=null)
            {
                    cliente=new ClienteDTO(compraEntity.getCliente());
            }
        }
    }
    
    
    /**
     * Transformar un DTO a un Entity
     *
     * @return El DTO de la compra para transformar a Entity
     */
    @Override
    public CompraEntity toEntity() {
        CompraEntity compraEntity = super.toEntity();
        if (boletas != null) {
            List<BoletaEntity> boletasEntity = new ArrayList<>();
            for (BoletaDTO dtoBoleta : boletas) {
                boletasEntity.add(dtoBoleta.toEntity());
            }
            compraEntity.setBoletas(boletasEntity);
        }
        return compraEntity;
    }
    
     /**
     * Devuelve la lista de boletas de la compra.
     *
     * @return las boletas
     */
    public List<BoletaDTO> getBoletas() {
        return boletas;
    }

    /**
     * Modifica la lista de boletas de la compra.
     *
     * @param boletas las nuevas boletas
     */
    public void setBoletas(List<BoletaDTO> boletas) {
        this.boletas = boletas;
    }
}

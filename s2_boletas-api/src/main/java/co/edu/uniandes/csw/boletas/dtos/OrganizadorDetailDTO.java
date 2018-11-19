/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.dtos;

import co.edu.uniandes.csw.boletas.entities.EspectaculoEntity;
import co.edu.uniandes.csw.boletas.entities.OrganizadorEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Vilma Tirado Gomez
 */
public class OrganizadorDetailDTO extends OrganizadorDTO implements Serializable  {
    
    private List<EspectaculoDTO> espectaculos;
    
       public OrganizadorDetailDTO() {
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param organizadorEntity La entidad de la organizador para transformar a DTO.
     */
    public OrganizadorDetailDTO(OrganizadorEntity organizadorEntity) {
        super(organizadorEntity);
        if (organizadorEntity != null && organizadorEntity.getEspectaculos() != null) {
            
            espectaculos = new ArrayList<>();
            for (EspectaculoEntity entityEspectaculo : organizadorEntity.getEspectaculos()) {
                espectaculos.add(new EspectaculoDTO(entityEspectaculo));
            }
            
        }
    }

    /**
     * Transformar un DTO a un Entity
     *
     * @return El DTO de la organizador para transformar a Entity
     */
    @Override
    public OrganizadorEntity toEntity() {
        OrganizadorEntity organizadorEntity = super.toEntity();
        if (espectaculos != null) {
            List<EspectaculoEntity> espectaculosEntity = new ArrayList<>();
            for (EspectaculoDTO dtoEspectaculo : espectaculos) {
                espectaculosEntity.add(dtoEspectaculo.toEntity());
            }
            organizadorEntity.setEspectaculos(espectaculosEntity);
        }
        return organizadorEntity;
    }

    /**
     * Devuelve la lista de libros de la organizador.
     *
     * @return the espectaculos
     */
    public List<EspectaculoDTO> getEspectaculos() {
        return espectaculos;
    }

    /**
     * Modifica la lista de libros de la organizador.
     *
     * @param espectaculos the espectaculos to set
     */
    public void setEspectaculos(List<EspectaculoDTO> espectaculos) {
        this.espectaculos = espectaculos;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}

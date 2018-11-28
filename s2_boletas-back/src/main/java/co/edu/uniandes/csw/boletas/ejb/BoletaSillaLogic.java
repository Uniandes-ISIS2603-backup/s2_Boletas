/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.BoletaEntity;
import co.edu.uniandes.csw.boletas.entities.SillaEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.BoletaPersistence;
import co.edu.uniandes.csw.boletas.persistence.SillaPersistence;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author ja.amortegui10
 */
@Stateless
public class BoletaSillaLogic {
    private static final Logger LOGGER = Logger.getLogger(BoletaCompraLogic.class.getName());
    
    @Inject
    private BoletaPersistence boletaPersistence;
    
    @Inject
    private SillaPersistence sillaPersistence;
    
    /**
     * Método que asocia una boleta a una silla después de haberse agregado.
     * @param lugarId
     * @param boletaId
     * @return
     * @throws BusinessLogicException 
     */
    public BoletaEntity addBoleta(Long lugarId, Long boletaId)throws BusinessLogicException
    {
        
        SillaEntity silla = sillaPersistence.find(lugarId);
        if(silla == null)
            throw new BusinessLogicException("La silla con el id dado no existe.");
        BoletaEntity boleta = boletaPersistence.find(boletaId);
        if(boleta == null)
            throw new BusinessLogicException("El lugar con el id dado no existe.");
        
        silla.setBoleta(boleta);
        boleta.setSilla(silla);
        
        return boleta;
    }
}

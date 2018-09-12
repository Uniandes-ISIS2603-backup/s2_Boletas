/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.ejb;

import co.edu.uniandes.csw.boletas.entities.SillaEntity;
import co.edu.uniandes.csw.boletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.boletas.persistence.SillaPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author ja.amortegui10
 */
@Stateless
public class SillaLogic {
    @Inject
    private SillaPersistence persistence;
    
    public SillaEntity getSillaById(Long id)
    {
        return persistence.find(id);
    }
    
    public SillaEntity getSillaByNumero(String numero, Long lugarId)
    {
        return persistence.findByNumeroAndLugar(numero, lugarId);
    }
    
    public SillaEntity createSilla(SillaEntity entity) throws BusinessLogicException
    {
        //No sé cómo verificar que la silla no exista, dado que SIllaEntity no tiene nombre.
        SillaEntity alreadyExists = getSillaByNumero(entity.getNumero(), entity.getLugar().getId());
        if(alreadyExists != null)
            throw new BusinessLogicException("Ya existe una silla con ese número en el lugar especificado.");
        return persistence.create(entity);
       
    }
}

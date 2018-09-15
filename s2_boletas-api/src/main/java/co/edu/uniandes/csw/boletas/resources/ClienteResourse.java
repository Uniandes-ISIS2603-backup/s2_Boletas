/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.boletas.resources;

import co.edu.uniandes.csw.boletas.entities.ClienteEntity;
import co.edu.uniandes.csw.boletas.dtos.ClienteDTO;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;

/**
 *
 * @author Vilma Tirado Gomez
 */
@Path("clientes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ClienteResourse 
{
    
    private static final Logger LOGGER = Logger.getLogger(ClienteResourse.class.getName());
    
    @POST
    public ClienteDTO createCliente(ClienteDTO cliente)
    { 
        
        LOGGER.info("ClienteResourse createCliente: input: " + cliente.toString());
        
        ClienteEntity entity = cliente.toEntity();
        
        
        
        
        return cliente;   
    }
    
    @PUT
    @Path("{clienteId : \\d+}")
    public ClienteDTO updateCliente(@PathParam("clienteId") Long clienteId, ClienteDTO cliente)
    {
        return null;
    }
    
    @GET
    @Path("{clienteId : \\d+}")
    public ClienteDTO getCliente(@PathParam("clienteId") Long clienteId)
    {
        return null;
    }
    
    @GET 
    public List<ClienteDTO> getCliente()
    {
        return null;
    }
    
    @DELETE
    @Path("{clienteId: \\d+}")
    public void deleteCliente(@PathParam("clienteId") Long clienteId) 
    { 
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package BO;

import DTO.ClienteDTO;
import Entidades.Cliente;
import Interfaces.IClienteDAO;
import Mapper.ClienteConvertidor;
import NegocioException.NegocioException;
import Utilerias.Encriptador;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author SDavidLedesma
 */
public class ClienteBOTest {

    private ClienteBO clienteBO;
    private IClienteDAO clienteDAOMock;

    @BeforeEach
    public void setUp() {
        clienteDAOMock = mock(IClienteDAO.class);
        clienteBO = new ClienteBO(clienteDAOMock);
    }

    @Test
    public void testRegistrarCliente_valido() throws Exception {
        ClienteDTO dto = new ClienteDTO();
        dto.setNombreCompleto("Juan Pérez HAHA");
        dto.setContrasenia(Encriptador.encriptar("12345"));
        dto.setEdad(25);

        Cliente clienteEntidad = ClienteConvertidor.dtoAEntidad(dto);
        clienteEntidad.setId(6);

        doAnswer(invocation -> {
            Cliente arg = invocation.getArgument(0);
            arg.setId(26);
            return null;
        }).when(clienteDAOMock).insertar(any(Cliente.class));

        ClienteDTO resultado = clienteBO.registrarCliente(dto);

        assertNotNull(resultado);
        assertEquals(26, resultado.getId());
    }

    @Test
    public void testRegistrarCliente_invalido_menorEdad() {
        ClienteDTO dto = new ClienteDTO();
        dto.setNombreCompleto("Pedro");
        dto.setContrasenia("pass");
        dto.setEdad(16); // menor de edad

        NegocioException ex = assertThrows(NegocioException.class, () -> clienteBO.registrarCliente(dto));
        assertEquals("El cliente debe ser mayor de edad.", ex.getMessage());
    }

    @Test
    public void testBuscarClientePorId_ok() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(5);
        cliente.setNombreCompleto("Maria");
        cliente.setContrasenia(Encriptador.encriptar("encrypted"));

        when(clienteDAOMock.buscarPorId(5)).thenReturn(cliente);

        ClienteDTO dto = clienteBO.buscarClientePorId(5);

        assertNotNull(dto);
        assertEquals("Maria", dto.getNombreCompleto());
    }

    @Test
    public void testLoginCliente_correcto() throws Exception {
        String nombre = "Luis";
        String contrasena = "123";
        String encriptada = Encriptador.encriptar(contrasena);

        Cliente cliente = new Cliente();
        cliente.setId(10);
        cliente.setNombreCompleto(nombre);
        cliente.setContrasenia(encriptada);

        when(clienteDAOMock.buscarPorNombre(nombre)).thenReturn(cliente);

        ClienteDTO dto = clienteBO.loginCliente(nombre, contrasena);

        assertNotNull(dto);
        assertEquals(nombre, dto.getNombreCompleto());
    }

    @Test
    public void testLoginCliente_contrasenaIncorrecta() throws Exception {
        String nombre = "Carlos";
        Cliente cliente = new Cliente();
        cliente.setNombreCompleto(nombre);
        cliente.setContrasenia(Encriptador.encriptar("correcta"));

        when(clienteDAOMock.buscarPorNombre(nombre)).thenReturn(cliente);

        NegocioException ex = assertThrows(NegocioException.class, () -> {
            clienteBO.loginCliente(nombre, "incorrecta");
        });

        assertEquals("Contraseña incorrecta.", ex.getMessage());
    }

    @Test
    public void testObtenerTodosLosClientes() throws Exception {
        Cliente cliente1 = new Cliente();
        cliente1.setId(1);
        cliente1.setNombreCompleto("Cliente1");

        Cliente cliente2 = new Cliente();
        cliente2.setId(2);
        cliente2.setNombreCompleto("Cliente2");

        when(clienteDAOMock.buscarTodos()).thenReturn(List.of(cliente1, cliente2));

        List<ClienteDTO> resultado = clienteBO.obtenerTodosLosClientes();

        assertEquals(2, resultado.size());
        assertEquals("Cliente1", resultado.get(0).getNombreCompleto());
    }
}

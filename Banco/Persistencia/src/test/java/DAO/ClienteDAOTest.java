package DAO;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
import Entidades.Cliente;
import Interfaces.IClienteDAO;
import java.time.LocalDate;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClienteDAOTest {

    private IClienteDAO clienteDAO;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        clienteDAO = new ClienteDAO();
        cliente = new Cliente("Juan Pérez", "CDMX", LocalDate.of(1995, 5, 10), 29,"hashedpass123");
    }

    @Test
    void testInsertarYBuscarPorId() throws Exception {
        clienteDAO.insertar(cliente);
        assertTrue(cliente.getId() > 0);

        Cliente recuperado = clienteDAO.buscarPorId(cliente.getId());
        assertNotNull(recuperado);
        assertEquals("Juan Pérez", recuperado.getNombreCompleto());
    }

    @Test
    void testActualizarCliente() throws Exception {
        clienteDAO.insertar(cliente);
        cliente.setDomicilio("Guadalajara");
        clienteDAO.actualizar(cliente);

        Cliente actualizado = clienteDAO.buscarPorId(cliente.getId());
        assertEquals("Guadalajara", actualizado.getDomicilio());
    }

    @Test
    void testBuscarPorNombreYExiste() throws Exception {
        clienteDAO.insertar(cliente);
        assertTrue(clienteDAO.existeClienteConNombre("Juan Pérez"));
        Cliente buscado = clienteDAO.buscarPorNombre("Juan Pérez");
        assertNotNull(buscado);
    }

    @Test
    void testBuscarTodos() throws Exception {
        clienteDAO.insertar(cliente);
        List<Cliente> lista = clienteDAO.buscarTodos();
        assertFalse(lista.isEmpty());
    }
}

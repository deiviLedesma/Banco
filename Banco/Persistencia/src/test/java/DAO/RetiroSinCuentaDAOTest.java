/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package DAO;

import Entidades.RetiroSinCuenta;
import Enumss.Estado;
import Interfaces.IRetiroSinCuentaDAO;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author SDavidLedesma
 */
public class RetiroSinCuentaDAOTest {
    
   private IRetiroSinCuentaDAO retiroDAO;
    private RetiroSinCuenta retiro;

    @BeforeEach
    void setUp() {
        retiroDAO = new RetiroSinCuentaDAO();
        retiro = new RetiroSinCuenta();
        retiro.setContrasena("12345678");
        retiro.setFechaSolicitud(LocalDateTime.now());
        retiro.setMonto(800.0);
        retiro.setEstado(Estado.PENDIENTE);
        retiro.setIdCliente(1); // debe existir
        retiro.setIdOperacion(1); // debe existir
        
    }

    @Test
    void testRegistrarYBuscarRetiro() throws Exception {
        retiroDAO.registrarRetiro(retiro);
        assertTrue(retiro.getFolio() > 0);

        RetiroSinCuenta buscado = retiroDAO.buscarPorFolioYContrasena(retiro.getFolio(), "12345678");
        assertNotNull(buscado);
        assertEquals(800.0, buscado.getMonto());
    }

    @Test
    void testActualizarEstado() throws Exception {
        retiroDAO.registrarRetiro(retiro);
        retiroDAO.actualizarEstado(retiro.getFolio(), "Cobrado");

        RetiroSinCuenta actualizado = retiroDAO.buscarPorFolioYContrasena(retiro.getFolio(), "12345678");
        assertEquals(Estado.COBRADO, actualizado.getEstado());
    }
}

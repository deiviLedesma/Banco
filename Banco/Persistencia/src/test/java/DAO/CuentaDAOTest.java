/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package DAO;

import Entidades.Cuenta;
import Interfaces.ICuentaDAO;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author SDavidLedesma
 */
public class CuentaDAOTest {

    private ICuentaDAO cuentaDAO;
    private Cuenta cuenta;

    @BeforeEach
    void setUp() {
        cuentaDAO = new CuentaDAO();
        cuenta = new Cuenta("111111", LocalDate.now(), 5000.0, true, 1);

    }

    @Test
    void testInsertarYBuscar() throws Exception {
        cuentaDAO.insertar(cuenta);
        assertTrue(cuenta.getId() > 0);

        Cuenta buscada = cuentaDAO.buscarPorNumeroCuenta("111111");
        assertNotNull(buscada);
        assertEquals(5000.0, buscada.getSaldo());
    }

    @Test
    void testActualizarSaldo() throws Exception {
        cuentaDAO.insertar(cuenta);
        cuentaDAO.actualizarSaldo(cuenta.getId(), 10000.0);

        Cuenta actualizada = cuentaDAO.buscarPorNumeroCuenta("111111");
        assertEquals(10000.0, actualizada.getSaldo());
    }

    @Test
    void testCancelarCuenta() throws Exception {
        cuentaDAO.insertar(cuenta);
        cuentaDAO.cancelarCuenta(cuenta.getId());

        Cuenta cancelada = cuentaDAO.buscarPorNumeroCuenta("111111");
        assertFalse(!cancelada.isActiva());
    }

    @Test
    void testBuscarCuentasPorCliente() throws Exception {
        cuentaDAO.insertar(cuenta);
        List<Cuenta> cuentas = cuentaDAO.buscarCuentasPorCliente(1);
        assertFalse(cuentas.isEmpty());
    }
}

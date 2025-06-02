/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package BO;

import DTO.CuentaDTO;
import Entidades.Cuenta;
import Interfaces.ICuentaDAO;
import NegocioException.NegocioException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author SDavidLedesma
 */
public class CuentaBOTest {

    private ICuentaDAO cuentaDAOMock;
    private CuentaBO cuentaBO;

    @BeforeEach
    public void setUp() {
        cuentaDAOMock = mock(ICuentaDAO.class);
        cuentaBO = new CuentaBO(cuentaDAOMock);

    }

    @Test
    public void testRegistrarCuentaValida() throws Exception {
        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setIdCliente(1);
        cuentaDTO.setSaldo(1000.0);

        when(cuentaDAOMock.buscarPorNumeroCuenta(anyString())).thenReturn(null);
        doAnswer(invocation -> {
            Cuenta cuenta = invocation.getArgument(0);
            cuenta.setId(1);
            return null;
        }).when(cuentaDAOMock).insertar(any(Cuenta.class));

        CuentaDTO resultado = cuentaBO.registrarCuenta(cuentaDTO);

        assertNotNull(resultado.getNumeroCuenta());
        assertEquals(1, resultado.getId());
    }

    @Test
    public void testRegistrarCuentaSaldoNegativo() {
        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setIdCliente(1);
        cuentaDTO.setSaldo(-100.0);

        NegocioException exception = assertThrows(NegocioException.class, () -> {
            cuentaBO.registrarCuenta(cuentaDTO);
        });

        assertEquals("El saldo inicial no puede ser negativo.", exception.getMessage());
    }

    @Test
    public void testRegistrarCuentaIdClienteInvalido() {
        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setIdCliente(0);
        cuentaDTO.setSaldo(100.0);

        NegocioException exception = assertThrows(NegocioException.class, () -> {
            cuentaBO.registrarCuenta(cuentaDTO);
        });

        assertEquals("ID de cliente inválido.", exception.getMessage());
    }

    @Test
    public void testActualizarSaldoCorrectamente() throws Exception {
        assertDoesNotThrow(() -> cuentaBO.actualizarSaldo(1, 500.0));
        verify(cuentaDAOMock).actualizarSaldo(1, 500.0);
    }

    @Test
    public void testActualizarSaldoNegativo() {
        NegocioException exception = assertThrows(NegocioException.class, () -> {
            cuentaBO.actualizarSaldo(1, -500.0);
        });

        assertEquals("El saldo no puede ser negativo.", exception.getMessage());
    }

    @Test
    public void testCancelarCuenta() throws Exception {
        assertDoesNotThrow(() -> cuentaBO.cancelarCuenta(1));
        verify(cuentaDAOMock).cancelarCuenta(1);
    }

    @Test
    public void testObtenerCuentasPorCliente() throws Exception {
        Cuenta cuenta1 = new Cuenta(1, "1234567890", LocalDate.now(), 1000.0, true, 1);
        Cuenta cuenta2 = new Cuenta(2, "0987654321", LocalDate.now(), 2000.0, true, 1);
        when(cuentaDAOMock.buscarCuentasPorCliente(1)).thenReturn(Arrays.asList(cuenta1, cuenta2));

        List<CuentaDTO> cuentas = cuentaBO.obtenerCuentasPorCliente(1);

        assertEquals(2, cuentas.size());
        assertEquals("1234567890", cuentas.get(0).getNumeroCuenta());
    }
}

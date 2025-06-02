/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package BO;

import DTO.RetiroSinCuentaDTO;
import Entidades.RetiroSinCuenta;
import Enumss.Estado;
import Interfaces.IRetiroSinCuentaDAO;
import NegocioException.NegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author SDavidLedesma
 */
public class RetiroSinCuentaBOTest {

    private IRetiroSinCuentaDAO retiroDAOMock;
    private RetiroSinCuentaBO retiroBO;

    @BeforeEach
    public void setUp() {
        retiroDAOMock = mock(IRetiroSinCuentaDAO.class);
        retiroBO = new RetiroSinCuentaBO(retiroDAOMock);
    }

    @Test
    public void testRegistrarRetiro_valido() throws Exception {
        RetiroSinCuentaDTO dto = new RetiroSinCuentaDTO();
        dto.setMonto(1000.0);
        dto.setContrasena("segura123");

        doAnswer(invocation -> {
            RetiroSinCuenta entidad = invocation.getArgument(0);
            entidad.setFolio(123);
            return null;
        }).when(retiroDAOMock).registrarRetiro(any(RetiroSinCuenta.class));

        RetiroSinCuentaDTO resultado = retiroBO.registrarRetiro(dto);

        assertEquals(Estado.PENDIENTE, resultado.getEstado());
        assertEquals(123, resultado.getFolio());
        assertNotNull(resultado.getFechaSolicitud());
    }

    @Test
    public void testRegistrarRetiro_montoInvalido() {
        RetiroSinCuentaDTO dto = new RetiroSinCuentaDTO();
        dto.setMonto(0.0);
        dto.setContrasena("1234");

        NegocioException ex = assertThrows(NegocioException.class, () -> {
            retiroBO.registrarRetiro(dto);
        });

        assertEquals("El monto debe ser mayor a cero.", ex.getMessage());
    }

    @Test
    public void testRegistrarRetiro_sinContrasena() {
        RetiroSinCuentaDTO dto = new RetiroSinCuentaDTO();
        dto.setMonto(500.0);
        dto.setContrasena("");

        NegocioException ex = assertThrows(NegocioException.class, () -> {
            retiroBO.registrarRetiro(dto);
        });

        assertEquals("La contraseña es obligatoria.", ex.getMessage());
    }

    @Test
    public void testCobrarRetiro_valido() throws Exception {
        RetiroSinCuenta entidad = new RetiroSinCuenta();
        entidad.setEstado(Estado.PENDIENTE);

        when(retiroDAOMock.buscarPorFolioYContrasena(100, "clave"))
                .thenReturn(entidad);

        assertDoesNotThrow(() -> retiroBO.cobrarRetiro(100, "clave"));

        verify(retiroDAOMock).actualizarEstado(100, "COBRADO");
    }

    @Test
    public void testCobrarRetiro_incorrecto() throws Exception {
        when(retiroDAOMock.buscarPorFolioYContrasena(200, "mala"))
                .thenReturn(null);

        NegocioException ex = assertThrows(NegocioException.class, () -> {
            retiroBO.cobrarRetiro(200, "mala");
        });

        assertEquals("Folio o contraseña incorrectos.", ex.getMessage());
    }

    @Test
    public void testCobrarRetiro_noPendiente() throws Exception {
        RetiroSinCuenta retiro = new RetiroSinCuenta();
        retiro.setEstado(Estado.COBRADO); // Ya cobrado

        when(retiroDAOMock.buscarPorFolioYContrasena(101, "clave"))
                .thenReturn(retiro);

        NegocioException ex = assertThrows(NegocioException.class, () -> {
            retiroBO.cobrarRetiro(101, "clave");
        });

        assertEquals("Este retiro ya fue cobrado o no está disponible.", ex.getMessage());
    }
}

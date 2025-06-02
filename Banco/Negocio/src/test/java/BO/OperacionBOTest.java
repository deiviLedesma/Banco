/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package BO;

import DTO.OperacionDTO;
import Entidades.Operacion;
import Enumss.TipoOperacion;
import Interfaces.IOperacionDAO;
import NegocioException.NegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

/**
 *
 * @author SDavidLedesma
 */
public class OperacionBOTest {

    private IOperacionDAO operacionDAOMock;
    private OperacionBO operacionBO;

    @BeforeEach
    public void setUp() {
        operacionDAOMock = mock(IOperacionDAO.class);
        operacionBO = new OperacionBO(operacionDAOMock);
    }


    @Test
    void registrarTransferencia_valida() throws Exception {
        OperacionDTO dto = new OperacionDTO();
        dto.setIdCuentaOrigen(1);
        dto.setIdCuentaDestino(2);
        dto.setMonto(100);

        doAnswer(invocation -> {
            Operacion entidad = invocation.getArgument(0);
            entidad.setId(123); // simulando ID generado
            return null;
        }).when(operacionDAOMock).registrarOperacion(any(Operacion.class));

        OperacionDTO resultado = operacionBO.registrarTransferencia(dto);

        assertEquals(123, resultado.getId());
        assertEquals(TipoOperacion.TRANSFERENCIA, resultado.getTipoOperacion());
        assertNotNull(resultado.getFechaHora());
    }

    @Test
    void registrarTransferencia_invalida_mismoDestino() {
        OperacionDTO dto = new OperacionDTO();
        dto.setIdCuentaOrigen(1);
        dto.setIdCuentaDestino(1);
        dto.setMonto(100);

        NegocioException ex = assertThrows(NegocioException.class, () -> {
            operacionBO.registrarTransferencia(dto);
        });
        assertTrue(ex.getMessage().contains("no pueden ser iguales"));
    }
}

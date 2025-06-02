/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package DAO;

import Entidades.Operacion;
import Enumss.TipoOperacion;
import Interfaces.IOperacionDAO;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author SDavidLedesma
 */
public class OperacionDAOTest {

    private IOperacionDAO operacionDAO;
    private Operacion operacion;

    @BeforeEach
    void setUp() {
        operacionDAO = new OperacionDAO();
        operacion = new Operacion(TipoOperacion.TRANSFERENCIA, LocalDateTime.now(), 300.0, 1, 2); // asegúrate de tener cuentas 1 y 2
    }

    @Test
    void testRegistrarYBuscarOperacion() throws Exception {
        operacionDAO.registrarOperacion(operacion);
        assertTrue(operacion.getId() > 0);

        List<Operacion> lista = operacionDAO.buscarOperacionesPorCuenta(1);
        assertFalse(lista.isEmpty());
    }

    @Test
    void testBuscarPorTipoYFechas() throws Exception {
        operacionDAO.registrarOperacion(operacion);

        List<Operacion> lista = operacionDAO.buscarOperacionesPorTipoYFechas(
                1, TipoOperacion.TRANSFERENCIA,
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1));

        assertFalse(lista.isEmpty());
    }
}

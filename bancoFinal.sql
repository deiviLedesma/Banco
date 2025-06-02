-- Crear base de datos
DROP DATABASE IF EXISTS banco;
CREATE DATABASE banco;
USE banco;

-- Tabla clientes
CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    domicilio VARCHAR(150) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    edad INT NOT NULL,
    contrasena VARCHAR(255) NOT NULL
);

-- Tabla cuentas
CREATE TABLE cuentas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero_cuenta VARCHAR(20) NOT NULL UNIQUE,
    fecha_apertura DATE NOT NULL,
    saldo DOUBLE NOT NULL,
    activa BOOLEAN NOT NULL DEFAULT TRUE,
    id_cliente INT NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id)
);

-- Tabla operaciones
CREATE TABLE operaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo_operacion ENUM('TRANSFERENCIA', 'RETIRO_SIN_CUENTA') NOT NULL,
    fecha_hora DATETIME NOT NULL,
    monto DOUBLE NOT NULL,
    id_cuenta_origen INT NULL,
    id_cuenta_destino INT NULL,
    FOREIGN KEY (id_cuenta_origen) REFERENCES cuentas(id),
    FOREIGN KEY (id_cuenta_destino) REFERENCES cuentas(id)
);

-- Tabla retiros sin cuenta
CREATE TABLE retiros_sin_cuenta (
    folio INT AUTO_INCREMENT PRIMARY KEY,
    contrasena VARCHAR(20) NOT NULL,
    fecha_solicitud DATETIME NOT NULL,
    monto DOUBLE NOT NULL,
    estado ENUM('PENDIENTE', 'COBRADO', 'NO_COBRADO') NOT NULL,
    id_cliente INT NOT NULL,
    id_operacion INT NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id),
    FOREIGN KEY (id_operacion) REFERENCES operaciones(id)
);

--  Datos de prueba
INSERT INTO clientes (nombre_completo, domicilio, fecha_nacimiento, edad, contrasena)
VALUES
('Juan Pérez', 'Calle 123', '1990-05-01', 34, 'HASH1'),
('Ana López', 'Av. Central', '1985-10-20', 38, 'HASH2');

INSERT INTO cuentas (numero_cuenta, fecha_apertura, saldo, activa, id_cliente)
VALUES
('CUENTA001', CURDATE(), 10000.00, TRUE, 1),
('CUENTA002', CURDATE(), 5000.00, TRUE, 2);

--  Procedimiento para transferencias con transacción
DELIMITER //
CREATE PROCEDURE realizar_transferencia(
    IN cuenta_origen_id INT,
    IN cuenta_destino_id INT,
    IN monto DOUBLE
)
BEGIN
    DECLARE saldo_actual DOUBLE;

    START TRANSACTION;

    SELECT saldo INTO saldo_actual FROM cuentas WHERE id = cuenta_origen_id FOR UPDATE;

    IF saldo_actual >= monto THEN
        UPDATE cuentas SET saldo = saldo - monto WHERE id = cuenta_origen_id;
        UPDATE cuentas SET saldo = saldo + monto WHERE id = cuenta_destino_id;

        INSERT INTO operaciones (tipo_operacion, fecha_hora, monto, id_cuenta_origen, id_cuenta_destino)
        VALUES ('TRANSFERENCIA', NOW(), monto, cuenta_origen_id, cuenta_destino_id);
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Saldo insuficiente';
    END IF;

    COMMIT;
END;
//
DELIMITER ;

--  Trigger para retiros no cobrados en más de 10 minutos
DELIMITER //
CREATE EVENT verificar_retiros_no_cobrados
ON SCHEDULE EVERY 1 MINUTE
DO
BEGIN
    UPDATE retiros_sin_cuenta
    SET estado = 'NO_COBRADO'
    WHERE estado = 'PENDIENTE' AND TIMESTAMPDIFF(MINUTE, fecha_solicitud, NOW()) > 10;
END;
//
DELIMITER ;

-- Habilitar eventos en MySQL (una sola vez)
SET GLOBAL event_scheduler = ON;

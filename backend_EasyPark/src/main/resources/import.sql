INSERT INTO users (full_name, email, password, telefono) VALUES ("Oscar Guillermo Sierra", "ogsierra@gmail.com", "12345", "31256")
INSERT INTO users (full_name, email, password, telefono) VALUES ("Pepe Gutierrez", "pepe@gmail.com", "123456", "311454")
INSERT INTO admin (full_name, email, password, telefono) VALUES ("Admin", "admin@gmail.com", "e633f4fc79badea1dc5db970cf397c8248bac47cc3acf9915ba60b5d76b0e88f", "31111111")
INSERT INTO tipo_vehiculo (tipo_vehiculo) VALUES ("Carro")
INSERT INTO tarifa (nombre_tarifa, precio, fecha, tipo_vehiculo_id) VALUES ("Prueba tarifa 1", 1000, "2025-03-18", 1)
INSERT INTO vehiculo (placa, tipo_vehiculo, user_id) VALUES ("DUK-608", "Camioneta", "ogsierra@gmail.com")
INSERT INTO registro_vehiculo (placa, tipo_vehiculo, entrada, tarifa_id) VALUES ("DUK-608", "Camioneta", "2025-04-08 16:31:00", 1)
INSERT INTO pago (salida, tarifa_id, registro_vehiculo_id) VALUES ("2025-04-08 20:00:00", 1, 1)
INSERT INTO factura (numero_factura	, fecha_emision, pago_id, user_id) VALUES (135, '2025-03-18', 1, "ogsierra@gmail.com");
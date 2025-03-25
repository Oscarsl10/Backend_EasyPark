INSERT INTO users (name, email, password) VALUES ("Oscar", "ogsierra@gmail.com", "12345")
INSERT INTO users (name, email, password) VALUES ("Pepe", "pepe@gmail.com", "123456")
INSERT INTO tarifa (precio, fecha, tipo_vehiculo, lapso) VALUES (1000, "2025-03-18", "Camioneta", "1")
INSERT INTO vehiculo (placa, tipo_vehiculo, user_id) VALUES ("DUK-608", "Camioneta", "ogsierra@gmail.com")
INSERT INTO pago (entrada, salida, tarifa_id, vehiculo_id) VALUES ("2025-03-18 08:00:00", "2025-03-18 12:00:00", 1, 1)
INSERT INTO factura (numero_factura	, fecha_emision, pago_id, user_id) VALUES (135, '2025-03-18', 1, "ogsierra@gmail.com");
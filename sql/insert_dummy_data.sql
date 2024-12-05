USE bancar_db;

INSERT INTO Cities (CityName, ZipCode, ProvinceId)
VALUES 
    ('CABA', 'C1000', 1),
    ('Mar del Plata', 'B7600', 1),
    ('La Plata', 'B1900', 1),
    ('Carlos Paz', 'X5000', 5),
    ('Rosario', 'S2000', 20),
    ('Luján de Cuyo', 'M5500', 12);

INSERT INTO Addresses (StreetName, StreetNumber, Flat, Details, CityId)
VALUES
    ('Calle Falsa', '123', '1A', 'Departamento', 1),
    ('Avenida Siempre Viva', '742', NULL, NULL, 2),
    ('Calle 9 de Julio', '111', 'PB', 'Frente', 3),
    ('Boulevard San Juan', '500', NULL, 'Casa', 4),
    ('Calle Mitre', '200', NULL, NULL, 5),
    ('Av. Rivadavia', '1000', '2B', 'Vista al parque', 6),
    ('Calle Los Olivos', '250', NULL, 'Casa', 1),
    ('Avenida del Libertador', '456', '3C', 'Departamento', 2),
    ('Calle Belgrano', '789', 'PB', NULL, 3),
    ('Pasaje Mitre', '50', '1B', 'Frente', 4),
    ('Boulevard Oroño', '620', NULL, 'Casa esquina', 5),
    ('Calle San Martin', '800', '4D', 'Vista al río', 6),
    ('Calle Las Flores', '1234', NULL, NULL, 1),
    ('Avenida Centenario', '987', NULL, 'Chalet', 2),
    ('Calle de los Sauces', '45', NULL, NULL, 3),
    ('Calle Lavalle', '321', '5A', 'Departamento', 4),
    ('Calle Perito Moreno', '567', NULL, 'Casa familiar', 5),
    ('Calle San Juan', '111', '2C', 'Vista al lago', 6),
    ('Calle Rivadavia', '654', NULL, NULL, 1),
    ('Calle Corrientes', '789', '1A', 'Frente', 2);

INSERT INTO Users (Username, UserPassword, RoleId)
VALUES
    ('admin', 'admin', 1),
    ('client', 'client', 2),
    ('user2', 'Password111', 2),
    ('user3', 'Password222', 2),
    ('user4', 'Password333', 2),
    ('user5', 'Password444', 2),
    ('user6', 'Password555', 2),
    ('user7', 'Password666', 2),
    ('user8', 'Password777', 2),
    ('user9', 'Password888', 2),
    ('user10', 'Password999', 2),
    ('user11', 'Password000', 2),
    ('user12', 'Password112', 2),
    ('user13', 'Password113', 2),
    ('user14', 'Password114', 2),
    ('user15', 'Password115', 2),
    ('user16', 'Password116', 2),
    ('user17', 'Password117', 2),
    ('user18', 'Password118', 2),
    ('user19', 'Password119', 2),
    ('user20', 'Password110', 2),
    ('user21', 'Password121', 2),
    ('user22', 'Password131', 2),
    ('user23', 'Password141', 2),
    ('user24', 'Password151', 2),
    ('user25', 'Password161', 2);

INSERT INTO Clients (ActiveStatus, Dni, Cuil, FirstName, LastName, Sex, Email, Phone, BirthDate, NationalityId, AddressId, UserId)
VALUES
    (1, '12345678', '20123456781', 'Juan', 'Perez', 'Masculino', 'juan.perez@example.com', '1112345678', '1990-05-15', 1, 1, 2),
    (1, '87654321', '27876543210', 'Maria', 'Gomez', 'Femenino', 'maria.gomez@example.com', '1123456789', '1985-10-22', 1, 2, 3),
    (1, '23456789', '23234567892', 'Carlos', 'Lopez', 'Masculino', 'carlos.lopez@example.com', '1134567890', '1978-03-12', 1, 3, 4),
    (1, '34567890', '20345678903', 'Ana', 'Martinez', 'Femenino', 'ana.martinez@example.com', '1145678901', '1995-08-30', 1, 4, 5),
    (0, '45678901', '23456789014', 'Jorge', 'Fernandez', 'Masculino', 'jorge.fernandez@example.com', '1156789012', '1980-01-18', 1, 5, 6),
    (1, '56789012', '20567890125', 'Laura', 'Diaz', 'Femenino', 'laura.diaz@example.com', '1167890123', '1993-11-25', 1, 6, 7),
    (1, '11122333', '20111223331', 'Luis', 'Castro', 'Masculino', 'luis.castro@example.com', '1171234567', '1990-02-15', 1, 7, 8),
    (1, '22233444', '27222334442', 'Elena', 'Romero', 'Femenino', 'elena.romero@example.com', '1182345678', '1983-05-10', 1, 8, 9),
    (1, '33344555', '23333445553', 'Gabriel', 'Sosa', 'Masculino', 'gabriel.sosa@example.com', '1193456789', '1975-07-22', 1, 9, 10),
    (0, '44455666', '20444556664', 'Veronica', 'Arias', 'Femenino', 'veronica.arias@example.com', '1104567890', '1992-04-18', 1, 10, 11),
    (1, '55566777', '20555667775', 'Ricardo', 'Alvarez', 'Masculino', 'ricardo.alvarez@example.com', '1115678901', '1988-03-30', 1, 11, 12),
    (1, '66677888', '27666778886', 'Camila', 'Rios', 'Femenino', 'camila.rios@example.com', '1126789012', '1994-09-05', 1, 12, 13),
    (1, '77788999', '23777889997', 'Pablo', 'Diaz', 'Masculino', 'pablo.diaz@example.com', '1137890123', '1996-08-12', 1, 13, 14),
    (1, '88899000', '20888990008', 'Sofia', 'Martinez', 'Femenino', 'sofia.martinez@example.com', '1148901234', '1991-12-07', 1, 14, 15),
    (0, '99900111', '27999001119', 'Alberto', 'Gutierrez', 'Masculino', 'alberto.gutierrez@example.com', '1159012345', '1982-10-20', 1, 15, 16),
    (1, '10011002', '20100110021', 'Carla', 'Fernandez', 'Femenino', 'carla.fernandez@example.com', '1160123456', '1987-11-01', 1, 16, 17),
    (1, '10122013', '27101220132', 'Lucas', 'Perez', 'Masculino', 'lucas.perez@example.com', '1171234567', '1993-03-14', 1, 17, 18),
    (1, '11011223', '23110112233', 'Diana', 'Lopez', 'Femenino', 'diana.lopez@example.com', '1182345678', '1985-06-26', 1, 18, 19),
    (1, '11233444', '20112334444', 'Sergio', 'Mendez', 'Masculino', 'sergio.mendez@example.com', '1193456789', '1976-07-18', 1, 19, 20),
    (0, '12344555', '23123445555', 'Julia', 'Silva', 'Femenino', 'julia.silva@example.com', '1104567890', '1990-02-27', 1, 20, 21),
    (1, '13455666', '27134556666', 'Mario', 'Castillo', 'Masculino', 'mario.castillo@example.com', '1115678901', '1989-01-23', 1, 7, 22),
    (1, '14566777', '20145667777', 'Estela', 'Vega', 'Femenino', 'estela.vega@example.com', '1126789012', '1984-09-14', 1, 8, 23),
    (1, '15677888', '23156778888', 'Diego', 'Ruiz', 'Masculino', 'diego.ruiz@example.com', '1137890123', '1995-08-11', 1, 9, 24),
    (1, '16788999', '27167889999', 'Monica', 'Perez', 'Femenino', 'monica.perez@example.com', '1148901234', '1980-06-10', 1, 10, 25),
    (0, '17899000', '20178990000', 'Ramon', 'Santos', 'Masculino', 'ramon.santos@example.com', '1159012345', '1997-03-28', 1, 10, 26);

INSERT INTO Accounts (ActiveStatus, Cbu, Balance, AccountTypeId, ClientId)
VALUES
    (1, '1234567890123456789101', 10000.00, 1, 1),
    (1, '1234567890123456789102', 5000.00, 2, 1),
    (1, '1234567890123456789103', 7500.00, 1, 1),
    (0, '1234567890123456789104', 3000.00, 2, 1),
    (0, '1234567890123456789105', 1500.00, 1, 1),
    (1, '1234567890123456789106', 900.00, 1, 2),
    (1, '1234567890123456789107', 1200.50, 2, 2),
    (0, '1234567890123456789108', 450.75, 1, 2),
    (1, '1234567890123456789109', 670.20, 1, 3),
    (0, '1234567890123456789110', 250.75, 2, 3),
    (1, '1234567890123456789111', 340.15, 1, 4),
    (1, '1234567890123456789112', 800.00, 2, 4),
    (0, '1234567890123456789113', 300.00, 1, 4),
    (0, '1234567890123456789114', 275.30, 2, 4),
    (1, '1234567890123456789115', 500.60, 1, 5),
    (1, '1234567890123456789116', 650.80, 2, 5),
    (1, '1234567890123456789117', 2000.40, 1, 5),
    (0, '1234567890123456789118', 425.20, 2, 5),
    (0, '1234567890123456789119', 100.50, 1, 5),
    (1, '1234567890123456789120', 820.25, 1, 6),
    (0, '1234567890123456789121', 500.90, 1, 6),
    (0, '1234567890123456789122', 315.90, 2, 6),
    (1, '1234567890123456789123', 435.00, 1, 7),
    (1, '1234567890123456789124', 500.75, 1, 8),
    (1, '1234567890123456789125', 300.40, 2, 8),
    (1, '1234567890123456789126', 850.00, 2, 9),
    (1, '1234567890123456789127', 750.15, 1, 9),
    (0, '1234567890123456789128', 600.00, 2, 9),
    (0, '1234567890123456789129', 425.20, 1, 9),
    (1, '1234567890123456789130', 150.75, 2, 11),
    (1, '1234567890123456789131', 800.25, 1, 11),
    (0, '1234567890123456789132', 200.00, 1, 11),
    (1, '1234567890123456789133', 225.30, 1, 12),
    (1, '1234567890123456789134', 600.20, 2, 13),
    (0, '1234567890123456789135', 450.00, 1, 13),
    (1, '1234567890123456789136', 725.40, 1, 14),
    (1, '1234567890123456789137', 1300.90, 1, 16),
    (1, '1234567890123456789138', 800.60, 2, 16),
    (1, '1234567890123456789139', 1200.00, 1, 16),
    (0, '1234567890123456789140', 600.00, 2, 16),
    (0, '1234567890123456789141', 450.00, 1, 16);

INSERT INTO Loans (CreationDate, InstallmentsQuantity, RequestedAmount, InterestRate, LoanTypeId, LoanStatusId, ClientId, AccountId)
VALUES
    ('2023-01-10', 10, 5000.00, 5.5, 1, 1, 1, 1),
    ('2024-10-10', 12, 50000.00, 5.5, 1, 2, 1, 1), -- Vigente 2
    ('2023-01-11', 10, 100000.00, 5.5, 1, 3, 1, 1),
    ('2023-02-20', 24, 15000.00, 6.8, 2, 2, 2, 2), -- Vigente 4
    ('2023-03-15', 36, 20000.00, 7.2, 1, 3, 3, 3),
    ('2023-04-01', 18, 7500.00, 5.0, 3, 1, 4, 4),
    ('2023-05-22', 12, 300000.00, 4.5, 2, 2, 5, 5), -- Vigente 7
    ('2023-06-30', 24, 12000.00, 6.0, 3, 1, 6, 6),
    ('2023-07-05', 48, 25000.00, 8.5, 1, 3, 7, 7),
    ('2023-08-12', 36, 80000.00, 7.0, 2, 2, 8, 8), -- Vigente 10
    ('2023-09-18', 12, 10000.00, 5.8, 3, 1, 9, 9),
    ('2023-10-25', 24, 50000.00, 6.2, 1, 2, 10, 10), -- Vigente 12
    ('2023-11-15', 18, 8000.00, 5.3, 2, 1, 11, 11),
    ('2023-12-01', 36, 22000.00, 7.5, 3, 3, 12, 12),
    ('2024-01-10', 12, 150000.00, 4.8, 1, 2, 13, 13), -- Vigente 15
    ('2024-02-22', 24, 15500.00, 6.9, 2, 1, 14, 14),
    ('2024-03-05', 18, 6500.00, 5.4, 3, 1, 15, 15),
    ('2024-04-18', 48, 27000.00, 8.1, 1, 3, 16, 16),
    ('2024-05-30', 12, 200000.00, 5.1, 2, 2, 2, 2), -- Vigente 19
    ('2024-06-12', 24, 13000.00, 6.3, 3, 1, 3, 3),
    ('2024-07-20', 36, 160000.00, 6.7, 1, 2, 4, 4), -- Vigente 21
    ('2024-08-25', 18, 7000.00, 5.2, 2, 1, 5, 5),
    ('2024-09-10', 12, 95000.00, 4.9, 3, 2, 6, 6), -- Vigente 23
    ('2024-10-02', 24, 14000.00, 6.4, 1, 3, 7, 7),
    ('2024-11-14', 48, 21000.00, 7.8, 2, 1, 8, 8),
    ('2024-12-05', 36, 250000.00, 7.1, 3, 2, 9, 9); -- Vigente 26

-- Generación de cuotas para préstamos vigentes
SET @GeneratedInstallments = 0; -- Es requerida para poder llamar al SP

CALL generate_installments(@GeneratedInstallments, 2);
CALL generate_installments(@GeneratedInstallments, 4);
CALL generate_installments(@GeneratedInstallments, 7);
CALL generate_installments(@GeneratedInstallments, 10);
CALL generate_installments(@GeneratedInstallments, 12);
CALL generate_installments(@GeneratedInstallments, 15);
CALL generate_installments(@GeneratedInstallments, 19);
CALL generate_installments(@GeneratedInstallments, 21);
CALL generate_installments(@GeneratedInstallments, 23);
CALL generate_installments(@GeneratedInstallments, 26);

SET @NewMovementId = 0; -- Requerida para el SP de transfers

CALL generate_transfer(@NewMovementId, UUID(), '2024-04-09 00:00:00', 'Depto Carlos (Alquileres)', 100000, 4, 1, 8);
CALL generate_transfer(@NewMovementId, UUID(), '2024-06-19 00:00:00', 'Bici (Varios)', 75000, 4, 1, 9);
CALL generate_transfer(@NewMovementId, UUID(), '2024-09-25 00:00:00', 'Curso C# (Varios)', 65000, 4, 1, 13);
CALL generate_transfer(@NewMovementId, UUID(), '2024-11-11 00:00:00', 'Desarrollo Web (Haberes)', 250000, 4, 17, 1);
CALL generate_transfer(@NewMovementId, UUID(), '2024-03-05 00:00:00', 'Consultoria (Varios)', 100000, 4, 20, 1);

-- Movimientos 
-- Variable para compartir el UUID de 2 Transferencias
SET @SharedTransactionId1 = UUID();
SET @SharedTransactionId2 = UUID();

-- Inserciones
INSERT INTO Movements (TransactionId, Details, Amount, MovementTypeId, AccountId)
VALUES 
    (UUID(), 'Apertura de cuenta', 10000.00, 1, 1),
    (UUID(), 'Apertura de cuenta', 2000.00, 1, 3),
    (UUID(), 'Apertura de cuenta', 3000.00, 1, 4),
    (UUID(), 'Apertura de cuenta', 1500.00, 1, 6),
    (UUID(), 'Apertura de cuenta', 2000.00, 1, 8),
    (UUID(), 'Alta de cuenta', 3000.00, 1, 10),
    (UUID(), 'Apertura de cuenta', 1800.00, 1, 11),
    (UUID(), 'Alta de cuenta', 3500.00, 1, 13),
    (UUID(), 'Apertura de cuenta', 1500.00, 1, 14);

INSERT INTO Movements (TransactionId, MovementDateTime, Details, Amount, MovementTypeId, AccountId)
VALUES
    (UUID(), '2024-03-09 00:00:00', 'Préstamo Nro. 2', 50000.00, 2, 1),
    (UUID(), '2024-10-16 00:00:00', 'Préstamo Nro. 4', 15000.00, 2, 2),
    (UUID(), '2024-12-09 00:00:00', 'Préstamo Nro. 7', 300000.00, 2, 5),
    (UUID(), '2024-03-03 00:00:00', 'Préstamo Nro. 10', 80000.00, 2, 8),
    (UUID(), '2024-09-01 00:00:00', 'Préstamo Nro. 12', 50000.00, 2, 10),
    (UUID(), '2024-05-10 00:00:00', 'Préstamo Nro. 15', 150000.00, 2, 13),
    (UUID(), '2024-06-28 00:00:00', 'Préstamo Nro. 19', 200000.00, 2, 2),
    (UUID(), '2024-07-20 00:00:00', 'Préstamo Nro. 21', 160000.00, 2, 4),
    (UUID(), '2024-03-14 00:00:00', 'Préstamo Nro. 23', 95000.00, 2, 6),
    (UUID(), '2024-11-18 00:00:00', 'Préstamo Nro. 26', 250000.00, 2, 9);
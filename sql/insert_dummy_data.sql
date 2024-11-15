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
    ('user1', 'password123', 2),
    ('user2', 'password456', 2),
    ('user3', 'password789', 2),
    ('user4', 'passwordABC', 2),
    ('user5', 'passwordXYZ', 2),
    ('user6', 'passwordDEF', 2),
    ('user7', 'password777', 2),
    ('user8', 'password888', 2),
    ('user9', 'password999', 2),
    ('user10', 'passwordAAA', 2),
    ('user11', 'passwordBBB', 2),
    ('user12', 'passwordCCC', 2),
    ('user13', 'passwordDDD', 2),
    ('user14', 'passwordEEE', 2),
    ('user15', 'passwordFFF', 2),
    ('user16', 'passwordGGG', 2),
    ('user17', 'passwordHHH', 2),
    ('user18', 'passwordIII', 2),
    ('user19', 'passwordJJJ', 2),
    ('user20', 'passwordKKK', 2),
    ('user21', 'passwordLLL', 2),
    ('user22', 'passwordMMM', 2),
    ('user23', 'passwordNNN', 2),
    ('user24', 'passwordOOO', 2),
    ('user25', 'passwordPPP', 2);

INSERT INTO Clients (ActiveStatus, Dni, Cuil, FirstName, LastName, Sex, Email, Phone, BirthDate, NationalityId, AddressId, UserId)
VALUES
    (1, '12345678', '20-12345678-1', 'Juan', 'Perez', 'Masculino', 'juan.perez@example.com', '+5491112345678', '1990-05-15', 1, 1, 2),
    (1, '87654321', '27-87654321-0', 'Maria', 'Gomez', 'Femenino', 'maria.gomez@example.com', '+5491123456789', '1985-10-22', 1, 2, 3),
    (1, '23456789', '23-23456789-2', 'Carlos', 'Lopez', 'Masculino', 'carlos.lopez@example.com', '+5491134567890', '1978-03-12', 1, 3, 4),
    (1, '34567890', '20-34567890-3', 'Ana', 'Martinez', 'Femenino', 'ana.martinez@example.com', '+5491145678901', '1995-08-30', 1, 4, 5),
    (0, '45678901', '23-45678901-4', 'Jorge', 'Fernandez', 'Masculino', 'jorge.fernandez@example.com', '+5491156789012', '1980-01-18', 1, 5, 6),
    (1, '56789012', '20-56789012-5', 'Laura', 'Diaz', 'Femenino', 'laura.diaz@example.com', '+5491167890123', '1993-11-25', 1, 6, 7),
    (1, '11122333', '20-11122333-1', 'Luis', 'Castro', 'Masculino', 'luis.castro@example.com', '+5491171234567', '1990-02-15', 1, 7, 8),
    (1, '22233444', '27-22233444-2', 'Elena', 'Romero', 'Femenino', 'elena.romero@example.com', '+5491182345678', '1983-05-10', 1, 8, 9),
    (1, '33344555', '23-33344555-3', 'Gabriel', 'Sosa', 'Masculino', 'gabriel.sosa@example.com', '+5491193456789', '1975-07-22', 1, 9, 10),
    (0, '44455666', '20-44455666-4', 'Veronica', 'Arias', 'Femenino', 'veronica.arias@example.com', '+5491104567890', '1992-04-18', 1, 10, 11),
    (1, '55566777', '20-55566777-5', 'Ricardo', 'Alvarez', 'Masculino', 'ricardo.alvarez@example.com', '+5491115678901', '1988-03-30', 1, 11, 12),
    (1, '66677888', '27-66677888-6', 'Camila', 'Rios', 'Femenino', 'camila.rios@example.com', '+5491126789012', '1994-09-05', 1, 12, 13),
    (1, '77788999', '23-77788999-7', 'Pablo', 'Diaz', 'Masculino', 'pablo.diaz@example.com', '+5491137890123', '1996-08-12', 1, 13, 14),
    (1, '88899000', '20-88899000-8', 'Sofia', 'Martinez', 'Femenino', 'sofia.martinez@example.com', '+5491148901234', '1991-12-07', 1, 14, 15),
    (0, '99900111', '27-99900111-9', 'Alberto', 'Gutierrez', 'Masculino', 'alberto.gutierrez@example.com', '+5491159012345', '1982-10-20', 1, 15, 16),
    (1, '10011002', '20-10011002-1', 'Carla', 'Fernandez', 'Femenino', 'carla.fernandez@example.com', '+5491160123456', '1987-11-01', 1, 16, 17),
    (1, '10122013', '27-10122013-2', 'Lucas', 'Perez', 'Masculino', 'lucas.perez@example.com', '+5491171234567', '1993-03-14', 1, 17, 18),
    (1, '11011223', '23-11011223-3', 'Diana', 'Lopez', 'Femenino', 'diana.lopez@example.com', '+5491182345678', '1985-06-26', 1, 18, 19),
    (1, '11233444', '20-11233444-4', 'Sergio', 'Mendez', 'Masculino', 'sergio.mendez@example.com', '+5491193456789', '1976-07-18', 1, 19, 20),
    (0, '12344555', '23-12344555-5', 'Julia', 'Silva', 'Femenino', 'julia.silva@example.com', '+5491104567890', '1990-02-27', 1, 20, 21),
    (1, '13455666', '27-13455666-6', 'Mario', 'Castillo', 'Masculino', 'mario.castillo@example.com', '+5491115678901', '1989-01-23', 1, 7, 22),
    (1, '14566777', '20-14566777-7', 'Estela', 'Vega', 'Femenino', 'estela.vega@example.com', '+5491126789012', '1984-09-14', 1, 8, 23),
    (1, '15677888', '23-15677888-8', 'Diego', 'Ruiz', 'Masculino', 'diego.ruiz@example.com', '+5491137890123', '1995-08-11', 1, 9, 24),
    (1, '16788999', '27-16788999-9', 'Monica', 'Perez', 'Femenino', 'monica.perez@example.com', '+5491148901234', '1980-06-10', 1, 10, 25),
    (0, '17899000', '20-17899000-0', 'Ramon', 'Santos', 'Masculino', 'ramon.santos@example.com', '+5491159012345', '1997-03-28', 1, 10, 26);

INSERT INTO
    Accounts (Cbu, Balance, AccountTypeId, ClientId)
VALUES
    ('0123456789012345678901', 10000, 1, 1);
    

-- Insertar 15 cuentas en la tabla Accounts
INSERT INTO Accounts (ActiveStatus, Cbu, Balance, AccountTypeId, ClientId)
VALUES
-- 8 cuentas inactivas y balance mayor a 0
(0, '1234567890123456789011', 100.50, 1, 2),
(0, '1234567890123456789012', 250.75, 2, 3),
(0, '1234567890123456789013', 300.00, 1, 4),
(0, '1234567890123456789014', 425.20, 2, 5),
(0, '1234567890123456789015', 500.90, 1, 6),
(0, '1234567890123456789016', 600.00, 2, 7),
(0, '1234567890123456789017', 750.15, 1, 8),
(0, '1234567890123456789018', 850.00, 2, 9),

-- 7 cuentas activas y balance mayor a 0
(1, '1234567890123456789019', 900.50, 1, 10),
(1, '1234567890123456789020', 150.75, 2, 11),
(1, '1234567890123456789021', 225.30, 1, 12),
(1, '1234567890123456789022', 600.20, 2, 13),
(1, '1234567890123456789023', 725.40, 1, 14),
(1, '1234567890123456789024', 1200.00, 2, 15),
(1, '1234567890123456789025', 1300.90, 1, 16);

-- Insertar 10 registros adicionales para los mismos ClientId
INSERT INTO Accounts (ActiveStatus, Cbu, Balance, AccountTypeId, ClientId)
VALUES

(1, '1234567890123456789026', 450.75, 1, 2),
(1, '1234567890123456789027', 555.00, 2, 2),
(0, '1234567890123456789028', 670.20, 1, 3),

(1, '1234567890123456789029', 340.15, 1, 4),
(0, '1234567890123456789030', 275.30, 2, 4),

(1, '1234567890123456789031', 500.60, 1, 5),
(1, '1234567890123456789032', 650.80, 2, 5),
(0, '1234567890123456789033', 820.25, 1, 6),

(1, '1234567890123456789034', 435.00, 1, 7),
(0, '1234567890123456789035', 315.90, 2, 7);

-- Insertar registros para prestamos

INSERT INTO Loans (CreationDate, InstallmentsQuantity, RequestedAmount, InterestRate, LoanTypeId, LoanStatusId, AccountId)
VALUES
    ('2023-01-10', 12, 5000.00, 5.5, 1, 1, 1),
    ('2023-02-20', 24, 15000.00, 6.8, 2, 2, 2),
    ('2023-03-15', 36, 20000.00, 7.2, 1, 3, 3),
    ('2023-04-01', 18, 7500.00, 5.0, 3, 1, 4),
    ('2023-05-22', 12, 3000.00, 4.5, 2, 2, 5),
    ('2023-06-30', 24, 12000.00, 6.0, 3, 1, 6),
    ('2023-07-05', 48, 25000.00, 8.5, 1, 3, 7),
    ('2023-08-12', 36, 18000.00, 7.0, 2, 2, 8),
    ('2023-09-18', 12, 10000.00, 5.8, 3, 1, 9),
    ('2023-10-25', 24, 5000.00, 6.2, 1, 2, 10),
    ('2023-11-15', 18, 8000.00, 5.3, 2, 1, 11),
    ('2023-12-01', 36, 22000.00, 7.5, 3, 3, 12),
    ('2024-01-10', 12, 4500.00, 4.8, 1, 2, 13),
    ('2024-02-22', 24, 15500.00, 6.9, 2, 1, 14),
    ('2024-03-05', 18, 6500.00, 5.4, 3, 1, 15),
    ('2024-04-18', 48, 27000.00, 8.1, 1, 3, 16),
    ('2024-05-30', 12, 4000.00, 5.1, 2, 2, 2),
    ('2024-06-12', 24, 13000.00, 6.3, 3, 1, 3),
    ('2024-07-20', 36, 16000.00, 6.7, 1, 2, 4),
    ('2024-08-25', 18, 7000.00, 5.2, 2, 1, 5),
    ('2024-09-10', 12, 9500.00, 4.9, 3, 2, 6),
    ('2024-10-02', 24, 14000.00, 6.4, 1, 3, 7),
    ('2024-11-14', 48, 21000.00, 7.8, 2, 1, 8),
    ('2024-12-05', 36, 25000.00, 7.1, 3, 2, 9);


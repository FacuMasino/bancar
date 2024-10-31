USE bancar_db;

INSERT INTO
    Countries (CountryName)
VALUES
    ('Argentina');

INSERT INTO
    Provinces (ProvinceName, CountryId)
VALUES
    ('Buenos Aires', 1), -- ID 1
    ('Catamarca', 1), -- ID 2
    ('Chaco', 1), -- ID 3
    ('Chubut', 1), -- ID 4
    ('Córdoba', 1), -- ID 5
    ('Corrientes', 1), -- ID 6
    ('Entre Ríos', 1), -- ID 7
    ('Formosa', 1), -- ID 8
    ('Jujuy', 1), -- ID 9
    ('La Pampa', 1), -- ID 10
    ('La Rioja', 1), -- ID 11
    ('Mendoza', 1), -- ID 12
    ('Misiones', 1), -- ID 13
    ('Neuquén', 1), -- ID 14
    ('Río Negro', 1), -- ID 15
    ('Salta', 1), -- ID 16
    ('San Juan', 1), -- ID 17
    ('San Luis', 1), -- ID 18
    ('Santa Cruz', 1), -- ID 19
    ('Santa Fe', 1), -- ID 20
    ('Santiago del Estero', 1), -- ID 21
    ('Tierra del Fuego', 1), -- ID 22
    ('Tucumán', 1);

-- ID 23
INSERT INTO
    Roles (RoleName)
VALUES
    ('Administrador'),
    ('Cliente');
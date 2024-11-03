USE bancar_db;

INSERT INTO
    Countries (CountryName)
VALUES
    ('Argentina');

INSERT INTO
    Provinces (ProvinceName, CountryId)
VALUES
    ('Buenos Aires', 1),
    ('Catamarca', 1),
    ('Chaco', 1),
    ('Chubut', 1),
    ('Córdoba', 1),
    ('Corrientes', 1),
    ('Entre Ríos', 1),
    ('Formosa', 1),
    ('Jujuy', 1),
    ('La Pampa', 1),
    ('La Rioja', 1),
    ('Mendoza', 1),
    ('Misiones', 1),
    ('Neuquén', 1),
    ('Río Negro', 1),
    ('Salta', 1),
    ('San Juan', 1),
    ('San Luis', 1),
    ('Santa Cruz', 1),
    ('Santa Fe', 1),
    ('Santiago del Estero', 1),
    ('Tierra del Fuego', 1),
    ('Tucumán', 1);

INSERT INTO
    Roles (RoleName)
VALUES
    ('Administrador'),
    ('Cliente');

INSERT INTO
    AccountTypes (AccountTypeName)
VALUES
    ('Caja de ahorro'),
    ('Cuenta corriente');
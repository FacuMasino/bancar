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

-- dummy_data.sql: Cities

-- dummy_data.sql: Addresses

INSERT INTO
    Roles (RoleName)
VALUES
    ('Administrador'),
    ('Cliente');

-- dummy_data.sql: Users

-- dummy_data.sql: Clients

INSERT INTO
    AccountTypes (AccountTypeName)
VALUES
    ('Caja de ahorro'),
    ('Cuenta corriente');

-- dummy_data.sql: Accounts

INSERT INTO
    Loantypes (LoanTypeName, LoanTypeDescription)
VALUES
    (
        'Préstamo personal',
        'Préstamo para cubrir gastos personales, como viajes, mejoras en el hogar o eventos importantes sin necesidad de justificar el uso del dinero.'
    ),
    (
        'Préstamo hipotecario',
        'Préstamo para comprar, construir o remodelar una vivienda. Plazos de hasta 30 años, con tasas de interés fijas o variables y garantía hipotecaria de la vivienda.'
    ),
    (
        'Préstamo automotriz',
        'Financiamiento de vehículos nuevos o usados. Plazos de hasta 5 años con tasa fija o variable, quedando el vehículo como garantía.'
    ),
    (
        'Préstamo educativo',
        'Financiamiento de educación universitaria, postgrados, cursos o programas de formación. Tasas de interés bajas y periodos de gracia.'
    ),
    (
        'Préstamo para PYME',
        'Préstamo para empresas o emprendedores con gastos operativos, capital de trabajo, expansión o adquisición de activos.'
    ),
    (
        'Préstamo para consumo',
        'Financiamiento de bienes y servicios a corto plazo con altas tasas de interés.'
    );

INSERT INTO
    LoanStatuses (LoanStatusName)
VALUES
    ('En revisión'),
    ('Vigente'),
    ('Finalizado'),
    ('Rechazado');

-- dummy_data.sql: Loans

INSERT INTO
    MovementTypes (MovementTypeName)
VALUES
    ('Alta de cuenta'),
    ('Alta de préstamo'),
    ('Pago de préstamo'),
    ('Transferencia');

-- dummy_data.sql: Movements

-- dummy_data.sql: Installments
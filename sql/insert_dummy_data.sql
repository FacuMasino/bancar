USE bancar_db;

INSERT INTO
    Cities (CityName, ZipCode, ProvinceId)
VALUES
    ('Don Torcuato', '1611', 1);

INSERT INTO
    Addresses (StreetName, StreetNumber, Flat, Details, CityId)
VALUES
    ('Alvear', '1132', NULL, NULL, 1);

INSERT INTO
    Users (Username, UserPassword)
VALUES
    ('admin', 'admin'),
    ('maxi', 'maxi');

INSERT INTO
    RolesToUsers (UserId, RoleId)
VALUES
    (1, 1),
    (2, 2);

INSERT INTO
    Clients (
        Dni,
        Cuil,
        FirstName,
        LastName,
        Sex,
        Email,
        Phone,
        BirthDate,
        NationalityId,
        AddressId,
        UserId
    )
VALUES
    (
        '38123456',
        '20-38123456-9',
        'Maximiliano',
        'Malvicino',
        'Hombre',
        'mrmalvicino@gmail.com',
        '1512345678',
        '1991-02-03',
        1,
        1,
        2
    );

INSERT INTO
    Accounts (Cbu, Balance, AccountTypeId, ClientId)
VALUES
    ('01234567890123456789012', 10000, 1, 1);
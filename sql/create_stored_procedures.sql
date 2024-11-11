DELIMITER $$

USE bancar_db

-- Country

CREATE PROCEDURE insert_country (
    IN _CountryName VARCHAR(50),
    OUT _NewCountryId INT
)
BEGIN
    INSERT INTO Countries (CountryName)
    VALUES (_CountryName);
    SET _NewCountryId = LAST_INSERT_ID();
END $$

CREATE PROCEDURE update_country (
    IN _CountryName VARCHAR(50),
    IN _CountryId INT
)
BEGIN
    UPDATE Countries
    SET CountryName = _CountryName
    WHERE CountryId = _CountryId;
END $$

-- Province

CREATE PROCEDURE insert_province (
    IN _ProvinceName VARCHAR(50),
    IN _CountryId INT,
    OUT _NewProvinceId INT
)
BEGIN
    INSERT INTO Provinces (ProvinceName, CountryId)
    VALUES (_ProvinceName, _CountryId);
    SET _NewProvinceId = LAST_INSERT_ID();
END $$

CREATE PROCEDURE update_province (
    IN _ProvinceName VARCHAR(50),
    IN _CountryId INT,
    IN _ProvinceId INT
)
BEGIN
    UPDATE Provinces
    SET ProvinceName = _ProvinceName, CountryId = _CountryId
    WHERE ProvinceId = _ProvinceId;
END $$

-- City

CREATE PROCEDURE insert_city (
    IN _CityName VARCHAR(50),
    IN _ZipCode VARCHAR(50),
    IN _ProvinceId INT,
    OUT _NewCityId INT
)
BEGIN
    INSERT INTO Cities (CityName, ZipCode, ProvinceId)
    VALUES (_CityName, _ZipCode, _ProvinceId);
    SET _NewCityId = LAST_INSERT_ID();
END $$

CREATE PROCEDURE update_city (
    IN _CityName VARCHAR(50),
    IN _ZipCode VARCHAR(50),
    IN _ProvinceId INT,
    IN _CityId INT
)
BEGIN
    UPDATE Cities
    SET CityName = _CityName, ZipCode = _ZipCode, ProvinceId = _ProvinceId
    WHERE CityId = _CityId;
END $$

-- Address

CREATE PROCEDURE insert_address (
    IN _StreetName VARCHAR(50),
    IN _StreetNumber VARCHAR(50),
    IN _Flat VARCHAR(50),
    IN _Details VARCHAR(500),
    IN _CityId INT,
    OUT _NewAddressId INT
)
BEGIN
    INSERT INTO Addresses (StreetName, StreetNumber, Flat, Details, CityId)
    VALUES (_StreetName, _StreetNumber, _Flat, _Details, _CityId);
    SET _NewAddressId = LAST_INSERT_ID();
END $$

CREATE PROCEDURE update_address (
    IN _StreetName VARCHAR(50),
    IN _StreetNumber VARCHAR(50),
    IN _Flat VARCHAR(50),
    IN _Details VARCHAR(500),
    IN _CityId INT,
    IN _AddressId INT
)
BEGIN
    UPDATE Addresses
    SET StreetName = _StreetName, StreetNumber = _StreetNumber, Flat = _Flat, Details = _Details, CityId = _CityId
    WHERE AddressId = _AddressId;
END $$

-- Client

CREATE PROCEDURE insert_client (
	IN _Dni VARCHAR(50),
    IN _Cuil VARCHAR(50),
    IN _FirstName VARCHAR(50),
    IN _LastName VARCHAR(50),
    IN _Sex VARCHAR(50),
    IN _Email VARCHAR(50),
    IN _Phone VARCHAR(50),
    IN _BirthDate DATE,
    IN _NationalityId INT,
    IN _AddressId INT
)
BEGIN
	INSERT INTO Clients (Dni, Cuil, FirstName, LastName, Sex, Email, Phone, BirthDate, NationalityId, AddressId)
    VALUES (_Dni, _Cuil, _FirstName, _LastName, _Sex, _Email, _Phone, _BirthDate, _NationalityId, _AddressId);
END $$

CREATE PROCEDURE update_client (
	IN _Dni VARCHAR(50),
    IN _Cuil VARCHAR(50),
    IN _FirstName VARCHAR(50),
    IN _LastName VARCHAR(50),
    IN _Sex VARCHAR(50),
    IN _Email VARCHAR(50),
    IN _Phone VARCHAR(50),
    IN _BirthDate DATE,
    IN _NationalityId INT,
    IN _AddressId INT,
    IN _ClientId INT
)
BEGIN
	UPDATE Clients
	SET Dni = _Dni,  Cuil = _Cuil, FirstName = _FirstName, LastName = _LastName, Sex = _Sex, Email = _Email, Phone = _Phone, BirthDate = _BirthDate, NationalityId = _NationalityId, AddressId = _AddressId
    WHERE ClientId = _ClientId;
END $$

-- Account

CREATE PROCEDURE insert_account (
    IN _Cbu VARCHAR(22),
    IN _Balance DECIMAL(10, 2),
    IN _AccountTypeId INT,
    IN _ClientId INT
) 
BEGIN
    INSERT INTO Accounts (Cbu, Balance, AccountTypeId, ClientId)
    VALUES (_Cbu, _Balance, _AccountTypeId, _ClientId);
END $$

CREATE PROCEDURE update_account (
    IN _Cbu VARCHAR(22),
    IN _Balance DECIMAL(10, 2),
    IN _AccountTypeId INT,
    IN _AccountId INT
) 
BEGIN
    UPDATE Accounts
    SET Cbu = _Cbu, Balance = _Balance, AccountTypeId = _AccountTypeId
    WHERE AccountId = _AccountId;
END $$

DELIMITER ;

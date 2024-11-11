DELIMITER $$

USE bancar_db

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

DELIMITER ;

USE bancar_db;

CREATE TABLE
    Countries (
        CountryId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
        CountryName VARCHAR(50) UNIQUE NOT NULL
    );

CREATE TABLE
    Provinces (
        ProvinceId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
        ProvinceName VARCHAR(50) NOT NULL,
        CountryId INT NOT NULL,
        CONSTRAINT FK_Country FOREIGN KEY (CountryId) REFERENCES Countries (CountryId),
        CONSTRAINT UC_Province UNIQUE (ProvinceName, CountryId)
    );

CREATE TABLE
    Cities (
        CityId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
        CityName VARCHAR(50) NOT NULL,
        ZipCode VARCHAR(50) NULL,
        ProvinceId INT NOT NULL,
        CONSTRAINT FK_Province FOREIGN KEY (ProvinceId) REFERENCES Provinces (ProvinceId),
        CONSTRAINT UC_City UNIQUE (CityName, ProvinceId)
    );

CREATE TABLE
    Addresses (
        AddressId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
        StreetName VARCHAR(50) NOT NULL,
        StreetNumber VARCHAR(50) NOT NULL,
        Flat VARCHAR(50) NULL,
        Details VARCHAR(500) NULL,
        CityId INT NOT NULL,
        CONSTRAINT FK_City FOREIGN KEY (CityId) REFERENCES Cities (CityId),
        CONSTRAINT UC_Address UNIQUE (StreetName, StreetNumber, CityId)
    );

CREATE TABLE
    Roles (
        RoleId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
        RoleName VARCHAR(50) NOT NULL
    );

CREATE TABLE
    Users (
        UserId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
        Username VARCHAR(50) NULL,
        UserPassword VARCHAR(50) NOT NULL
    );

CREATE TABLE
    RolesToUsers (
        UserId INT NOT NULL,
        RoleId INT NOT NULL,
        CONSTRAINT FK_User FOREIGN KEY (UserId) REFERENCES Users (UserId),
        CONSTRAINT FK_Role FOREIGN KEY (RoleId) REFERENCES Roles (RoleId),
        PRIMARY KEY (UserId, RoleId)
    );

CREATE TABLE
    Clients (
        ClientId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
        IsActive BIT DEFAULT 1 NOT NULL,
        Dni VARCHAR(50) UNIQUE NOT NULL,
        Cuil VARCHAR(50) UNIQUE NOT NULL,
        FirstName VARCHAR(50) NOT NULL,
        LastName VARCHAR(50) NOT NULL,
        Sex VARCHAR(50) NOT NULL,
        Email VARCHAR(50) NOT NULL,
        Phone VARCHAR(50) NOT NULL,
        BirthDate DATE NOT NULL,
        NationalityId INT NOT NULL,
        AddressId INT NOT NULL,
        UserId INT,
        CONSTRAINT FK_Nationality FOREIGN KEY (NationalityId) REFERENCES Countries (CountryId),
        CONSTRAINT FK_Address FOREIGN KEY (AddressId) REFERENCES Addresses (AddressId),
        CONSTRAINT FK_User_Client FOREIGN KEY (UserId) REFERENCES Users (UserId)
    );

CREATE TABLE
    AccountTypes (
        AccountTypeId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
        AccountTypeName VARCHAR(50) UNIQUE NOT NULL
    );

CREATE TABLE
    Accounts (
        AccountId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
        IsActive BIT DEFAULT 1 NOT NULL,
        Cbu VARCHAR(50) UNIQUE NOT NULL,
        CreationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        Balance DECIMAL(15, 2) NOT NULL,
        AccountTypeId INT NOT NULL,
        ClientId INT NOT NULL,
        CONSTRAINT FK_AccountType FOREIGN KEY (AccountTypeId) REFERENCES AccountTypes (AccountTypeId),
        CONSTRAINT FK_Client FOREIGN KEY (ClientId) REFERENCES Clients (ClientId)
    );

CREATE TABLE
    Installments (
        InstallmentId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
        InstallmentNumber INT NOT NULL,
        Amount DECIMAL(15, 2) NOT NULL,
        PaymentDate DATE NOT NULL
    );

CREATE TABLE
    LoanTypes (
        LoanTypeId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
        LoanTypeName VARCHAR(50) NOT NULL,
        LoanTypeDescription VARCHAR(500) NOT NULL
    );

CREATE TABLE
    LoanStatuses (
        LoanStatusId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
        LoanStatusName VARCHAR(50) NOT NULL
    );

CREATE TABLE
    Loans (
        LoanId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
        CreationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        MonthsLimit INT NOT NULL,
        LoanTypeId INT NOT NULL,
        LoanStatusId INT NOT NULL,
        CONSTRAINT FK_LoanType FOREIGN KEY (LoanTypeId) REFERENCES LoanTypes (LoanTypeId),
        CONSTRAINT FK_LoanStatus FOREIGN KEY (LoanStatusId) REFERENCES LoanStatuses (LoanStatusId)
    );

CREATE TABLE
    InstallmentsToLoans (
        InstallmentId INT NOT NULL,
        LoanId INT NOT NULL,
        CONSTRAINT FK_Installment FOREIGN KEY (InstallmentId) REFERENCES Installments (InstallmentId),
        CONSTRAINT FK_Loan FOREIGN KEY (LoanId) REFERENCES Loans (LoanId),
        PRIMARY KEY (InstallmentId, LoanId)
    );

CREATE TABLE
    Transfers (
        TransferId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
        SenderCbu VARCHAR(50) NOT NULL,
        RecipientCbu VARCHAR(50) NOT NULL,
        CreationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        Details VARCHAR(500) NOT NULL,
        Amount DECIMAL(15, 2) NOT NULL
    );
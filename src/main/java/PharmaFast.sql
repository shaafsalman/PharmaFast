CREATE TABLE Users (
                       UserID INT IDENTITY(1, 1) PRIMARY KEY,
                       Username VARCHAR(50) NOT NULL,
                       Password VARCHAR(50) NOT NULL,
                       Role VARCHAR(20) NOT NULL
);


CREATE TABLE Categories (
                            CategoryID INT IDENTITY(1, 1) PRIMARY KEY,
                            CategoryName VARCHAR(50) NOT NULL
);

CREATE TABLE Products (
                          ProductID INT IDENTITY(1, 1) PRIMARY KEY,
                          CategoryID INT,
                          ProductName VARCHAR(100) NOT NULL,
                          Price DECIMAL(10, 2) NOT NULL,
                          SellingPrice DECIMAL(10, 2) ,
                          Quantity INT NOT NULL,
                          ExpiryDate DATE NOT NULL,
                          FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
);


CREATE TABLE Transactions (
                              TransactionID INT IDENTITY(1, 1) PRIMARY KEY,
                              UserID INT,
                              TotalCost DECIMAL(10, 2) NOT NULL,
                              TransactionDate DATETIME NOT NULL,
                              FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

CREATE TABLE TransactionItems (
                                  TransactionItemID INT IDENTITY(1, 1) PRIMARY KEY,
                                  TransactionID INT,
                                  ProductID INT,
                                  Quantity INT NOT NULL,
                                  FOREIGN KEY (TransactionID) REFERENCES Transactions(TransactionID),
                                  FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);


INSERT INTO Categories (CategoryName)
VALUES
    ('Pain Relief'),
    ('Ointments');


INSERT INTO Products (CategoryID, ProductName, Price, Quantity, ExpiryDate)
VALUES
    (1, 'Painkiller A', 5.99, 100, '2023-12-31'),
    (1, 'Painkiller B', 6.99, 75, '2024-06-30'),
    (2, 'Ointment X', 4.49, 50, '2023-09-15'),
    (2, 'Ointment Y', 3.99, 30, '2024-03-20');

INSERT INTO InventoryLogs (ProductID, LogDate, LogType, Quantity)
VALUES
    (1, '2023-10-10', 'Purchase', 50),
    (2, '2023-10-11', 'Purchase', 25),
    (1, '2023-10-12', 'Sale', 20),
    (3, '2023-10-13', 'Purchase', 40);

INSERT INTO Users (Username, Password, Role)
VALUES
    ('ph', '1122', 'Pharmacist'),
    ('sa', '1122', 'Sales Assistant');


INSERT INTO Transactions (UserID, TotalCost, TransactionDate)
VALUES
    (1, 25.45, '2023-10-15 14:30:00'),
    (2, 15.75, '2023-10-16 10:15:00');

INSERT INTO TransactionItems (TransactionID, ProductID, Quantity)
VALUES
    (1, 1, 5),
    (1, 2, 3),
    (2, 3, 10);

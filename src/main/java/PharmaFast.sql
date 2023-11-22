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





-- Variables for random data generation
DECLARE @maxProducts INT = 20; -- assuming a transaction can have up to 20 different products
DECLARE @quantityRange INT = 5; -- max quantity of each product in a transaction
DECLARE @daysRange INT = 365; -- range of days for the transaction date (last year)
DECLARE @baseDate DATE = '2022-01-01'; -- base date for transactions
DECLARE @maxProductID INT = 1092; -- replace with the highest product ID in your Products table

-- Insert 300 transactions
DECLARE @i INT = 1;
WHILE @i <= 300
BEGIN
        DECLARE @userID INT = 1; -- as specified
        DECLARE @transactionDate DATE = DATEADD(day, RAND() * @daysRange, @baseDate);
        DECLARE @totalCost DECIMAL(10, 2) = 0;

INSERT INTO Transactions (UserID, TotalCost, TransactionDate)
VALUES (@userID, @totalCost, @transactionDate); -- initial total cost is 0, will update later

DECLARE @transactionID INT = SCOPE_IDENTITY(); -- get the last inserted transaction ID

        -- Insert random transaction items
        DECLARE @j INT = 1;
        DECLARE @numProducts INT = RAND() * @maxProducts + 1; -- number of products in the transaction
        WHILE @j <= @numProducts
BEGIN
                DECLARE @productID INT = RAND() * @maxProductID + 1;
                DECLARE @quantity INT = RAND() * @quantityRange + 1;
                DECLARE @price DECIMAL(10, 2);
                DECLARE @sellingPrice DECIMAL(10, 2);

                -- Get product price and selling price
SELECT @price = Price, @sellingPrice = SellingPrice FROM Products WHERE ProductID = @productID;

INSERT INTO TransactionItems (TransactionID, ProductID, Quantity)
VALUES (@transactionID, @productID, @quantity);

-- Update total cost
SET @totalCost = @totalCost + (@sellingPrice * @quantity);

                SET @j = @j + 1;
END

        -- Update the total cost in Transactions table
UPDATE Transactions SET TotalCost = @totalCost WHERE TransactionID = @transactionID;

SET @i = @i + 1;
END

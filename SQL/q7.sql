--scenario 1--
CREATE OR REPLACE PACKAGE CustomerManagement AS
  PROCEDURE AddCustomer(p_Name VARCHAR2, p_Address VARCHAR2, p_InitialBalance NUMBER);
  PROCEDURE UpdateCustomer(p_CustomerID NUMBER, p_Name VARCHAR2, p_Address VARCHAR2);
  FUNCTION GetCustomerBalance(p_CustomerID NUMBER) RETURN NUMBER;
END CustomerManagement;
/

CREATE OR REPLACE PACKAGE BODY CustomerManagement AS

  PROCEDURE AddCustomer(p_Name VARCHAR2, p_Address VARCHAR2, p_InitialBalance NUMBER) IS
    v_new_id NUMBER;
  BEGIN
    SELECT Customers_seq.NEXTVAL INTO v_new_id FROM DUAL;

    INSERT INTO Customers (CustomerID, Name, Address, Balance)
    VALUES (v_new_id, p_Name, p_Address, p_InitialBalance);

    DBMS_OUTPUT.PUT_LINE('New customer added with ID: ' || v_new_id);
  END AddCustomer;

  PROCEDURE UpdateCustomer(p_CustomerID NUMBER, p_Name VARCHAR2, p_Address VARCHAR2) IS
  BEGIN
    UPDATE Customers
    SET Name = p_Name,
        Address = p_Address
    WHERE CustomerID = p_CustomerID;

    DBMS_OUTPUT.PUT_LINE('Customer ID ' || p_CustomerID || ' updated.');
  END UpdateCustomer;

  FUNCTION GetCustomerBalance(p_CustomerID NUMBER) RETURN NUMBER IS
    v_balance NUMBER;
  BEGIN
    SELECT Balance INTO v_balance
    FROM Customers
    WHERE CustomerID = p_CustomerID;

    RETURN v_balance;
  END GetCustomerBalance;

END CustomerManagement;
/

--scenario 2--

CREATE OR REPLACE PACKAGE EmployeeManagement AS
  PROCEDURE HireEmployee(p_Name VARCHAR2, p_Position VARCHAR2, p_Salary NUMBER);
  PROCEDURE UpdateEmployee(p_EmployeeID NUMBER, p_Name VARCHAR2, p_Position VARCHAR2, p_Salary NUMBER);
  FUNCTION CalculateAnnualSalary(p_EmployeeID NUMBER) RETURN NUMBER;
END EmployeeManagement;
/

CREATE OR REPLACE PACKAGE BODY EmployeeManagement AS

  PROCEDURE HireEmployee(p_Name VARCHAR2, p_Position VARCHAR2, p_Salary NUMBER) IS
    v_new_id NUMBER;
  BEGIN
    SELECT Employees_seq.NEXTVAL INTO v_new_id FROM DUAL;

    INSERT INTO Employees (EmployeeID, Name, Position, Salary)
    VALUES (v_new_id, p_Name, p_Position, p_Salary);

    DBMS_OUTPUT.PUT_LINE('New employee hired with ID: ' || v_new_id);
  END HireEmployee;

  PROCEDURE UpdateEmployee(p_EmployeeID NUMBER, p_Name VARCHAR2, p_Position VARCHAR2, p_Salary NUMBER) IS
  BEGIN
    UPDATE Employees
    SET Name = p_Name,
        Position = p_Position,
        Salary = p_Salary
    WHERE EmployeeID = p_EmployeeID;

    DBMS_OUTPUT.PUT_LINE('Employee ID ' || p_EmployeeID || ' updated.');
  END UpdateEmployee;

  FUNCTION CalculateAnnualSalary(p_EmployeeID NUMBER) RETURN NUMBER IS
    v_salary NUMBER;
  BEGIN
    SELECT Salary INTO v_salary
    FROM Employees
    WHERE EmployeeID = p_EmployeeID;

    RETURN v_salary * 12;
  END CalculateAnnualSalary;

END EmployeeManagement;
/

--scenario 3--

CREATE OR REPLACE PACKAGE AccountOperations AS
  PROCEDURE OpenAccount(p_CustomerID NUMBER, p_AccountType VARCHAR2, p_InitialBalance NUMBER);
  PROCEDURE CloseAccount(p_AccountID NUMBER);
  FUNCTION GetTotalBalance(p_CustomerID NUMBER) RETURN NUMBER;
END AccountOperations;
/

CREATE OR REPLACE PACKAGE BODY AccountOperations AS

  PROCEDURE OpenAccount(p_CustomerID NUMBER, p_AccountType VARCHAR2, p_InitialBalance NUMBER) IS
    v_new_id NUMBER;
  BEGIN
    SELECT Accounts_seq.NEXTVAL INTO v_new_id FROM DUAL;

    INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance)
    VALUES (v_new_id, p_CustomerID, p_AccountType, p_InitialBalance);

    DBMS_OUTPUT.PUT_LINE('New account opened with ID: ' || v_new_id);
  END OpenAccount;

  PROCEDURE CloseAccount(p_AccountID NUMBER) IS
  BEGIN
    DELETE FROM Accounts
    WHERE AccountID = p_AccountID;

    DBMS_OUTPUT.PUT_LINE('Account ID ' || p_AccountID || ' closed.');
  END CloseAccount;

  FUNCTION GetTotalBalance(p_CustomerID NUMBER) RETURN NUMBER IS
    v_total_balance NUMBER;
  BEGIN
    SELECT NVL(SUM(Balance), 0) INTO v_total_balance
    FROM Accounts
    WHERE CustomerID = p_CustomerID;

    RETURN v_total_balance;
  END GetTotalBalance;

END AccountOperations;
/

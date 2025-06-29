--scenario 1--
CREATE OR REPLACE PROCEDURE SafeTransferFunds (
  p_from_account_id IN NUMBER,
  p_to_account_id   IN NUMBER,
  p_amount          IN NUMBER
)
AS
  insufficient_funds EXCEPTION;
BEGIN
  -- Check if sufficient balance exists
  DECLARE
    v_balance NUMBER;
  BEGIN
    SELECT BALANCE INTO v_balance
    FROM ACCOUNTS
    WHERE ACCOUNT_ID = p_from_account_id;

    IF v_balance < p_amount THEN
      RAISE insufficient_funds;
    END IF;

    -- Subtract from sender
    UPDATE ACCOUNTS
    SET BALANCE = BALANCE - p_amount
    WHERE ACCOUNT_ID = p_from_account_id;

    -- Add to receiver
    UPDATE ACCOUNTS
    SET BALANCE = BALANCE + p_amount
    WHERE ACCOUNT_ID = p_to_account_id;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Transfer successful!');
  EXCEPTION
    WHEN insufficient_funds THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('Error: Insufficient funds in Account ID ' || p_from_account_id);
    WHEN OTHERS THEN
      ROLLBACK;
      DBMS_OUTPUT.PUT_LINE('Error during transfer: ' || SQLERRM);
  END;
END;
/

--scenario 2--
CREATE OR REPLACE PROCEDURE UpdateSalary (
  p_employee_id IN NUMBER,
  p_percentage  IN NUMBER
)
AS
  e_employee_not_found EXCEPTION;
  PRAGMA EXCEPTION_INIT(e_employee_not_found, -01403);  -- NO_DATA_FOUND
BEGIN
  UPDATE EMPLOYEES
  SET SALARY = SALARY + (SALARY * p_percentage / 100)
  WHERE EMPLOYEE_ID = p_employee_id;

  IF SQL%ROWCOUNT = 0 THEN
    RAISE e_employee_not_found;
  END IF;

  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Salary updated for Employee ID ' || p_employee_id);
EXCEPTION
  WHEN e_employee_not_found THEN
    DBMS_OUTPUT.PUT_LINE('Error: Employee ID ' || p_employee_id || ' does not exist.');
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error while updating salary: ' || SQLERRM);
END;
/

--scenario 3--
CREATE OR REPLACE PROCEDURE AddNewCustomer (
  p_customer_id IN NUMBER,
  p_name        IN VARCHAR2,
  p_age         IN NUMBER,
  p_balance     IN NUMBER
)
AS
  DUP_VAL_ON_INDEX EXCEPTION;
BEGIN
  INSERT INTO CUSTOMERS (CUSTOMER_ID, NAME, AGE, BALANCE)
  VALUES (p_customer_id, p_name, p_age, p_balance);

  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Customer added: ' || p_customer_id);
EXCEPTION
  WHEN DUP_VAL_ON_INDEX THEN
    DBMS_OUTPUT.PUT_LINE('Error: Customer ID ' || p_customer_id || ' already exists.');
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error while adding customer: ' || SQLERRM);
END;
/

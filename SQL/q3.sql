--scenario 1--
CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest
AS
BEGIN
  UPDATE ACCOUNTS
  SET BALANCE = BALANCE + (BALANCE * 0.01)
  WHERE ACCOUNT_TYPE = 'SAVINGS';

  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Monthly interest processed for all savings accounts.');
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Error processing interest: ' || SQLERRM);
END;
/

--scenario 2--
CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus (
  p_department_id IN NUMBER,
  p_bonus_percent IN NUMBER
)
AS
BEGIN
  UPDATE EMPLOYEES
  SET SALARY = SALARY + (SALARY * p_bonus_percent / 100)
  WHERE DEPARTMENT_ID = p_department_id;

  IF SQL%ROWCOUNT = 0 THEN
    DBMS_OUTPUT.PUT_LINE('No employees found in Department ID ' || p_department_id);
  ELSE
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Bonus applied to employees in Department ID ' || p_department_id);
  END IF;
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Error updating bonus: ' || SQLERRM);
END;
/

--scenario 3--
CREATE OR REPLACE PROCEDURE TransferFunds (
  p_from_account_id IN NUMBER,
  p_to_account_id   IN NUMBER,
  p_amount          IN NUMBER
)
AS
  v_balance NUMBER;
BEGIN
  -- Get current balance of source account
  SELECT BALANCE INTO v_balance
  FROM ACCOUNTS
  WHERE ACCOUNT_ID = p_from_account_id;
x
  IF v_balance < p_amount THEN
    DBMS_OUTPUT.PUT_LINE('Error: Insufficient funds in Account ID ' || p_from_account_id);
    RETURN;
  END IF;

  -- Deduct from source
  UPDATE ACCOUNTS
  SET BALANCE = BALANCE - p_amount
  WHERE ACCOUNT_ID = p_from_account_id;

  -- Add to target
  UPDATE ACCOUNTS
  SET BALANCE = BALANCE + p_amount
  WHERE ACCOUNT_ID = p_to_account_id;

  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Transfer of ' || p_amount || ' successful from Account ' || p_from_account_id || ' to Account ' || p_to_account_id);
EXCEPTION
  WHEN NO_DATA_FOUND THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Error: One or both account IDs do not exist.');
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Error during fund transfer: ' || SQLERRM);
END;
/

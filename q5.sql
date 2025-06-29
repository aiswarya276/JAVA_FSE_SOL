--scenario 1--
Customers(CustomerID, Name, Address, LastModified)
CREATE OR REPLACE TRIGGER UpdateCustomerLastModified
BEFORE UPDATE ON Customers
FOR EACH ROW
BEGIN
  :NEW.LastModified := SYSDATE;
END;
/

--scenario 2--
CREATE OR REPLACE TRIGGER LogTransaction
AFTER INSERT ON Transactions
FOR EACH ROW
BEGIN
  INSERT INTO AuditLog (LogID, TransactionID, Action, LogDate)
  VALUES (AuditLog_seq.NEXTVAL, :NEW.TransactionID, 'INSERT', SYSDATE);
END;
/

--scenario 3--
CREATE OR REPLACE TRIGGER CheckTransactionRules
BEFORE INSERT ON Transactions
FOR EACH ROW
DECLARE
  v_balance NUMBER;
BEGIN
  -- Get current account balance
  SELECT Balance INTO v_balance
  FROM Accounts
  WHERE AccountID = :NEW.AccountID;

  IF :NEW.Type = 'Withdrawal' THEN
    IF :NEW.Amount > v_balance THEN
      RAISE_APPLICATION_ERROR(-20001, 'Withdrawal exceeds account balance.');
    END IF;
  ELSIF :NEW.Type = 'Deposit' THEN
    IF :NEW.Amount <= 0 THEN
      RAISE_APPLICATION_ERROR(-20002, 'Deposit amount must be positive.');
    END IF;
  END IF;
END;
/

--scenario 1--
DECLARE
  -- Define cursor to get all transactions for current month
  CURSOR cur_transactions IS
    SELECT CustomerID, TransactionID, Amount, TransactionDate
    FROM Transactions
    WHERE TRUNC(TransactionDate, 'MM') = TRUNC(SYSDATE, 'MM')
    ORDER BY CustomerID;

  v_cust_id     NUMBER;
  v_trans_id    NUMBER;
  v_amount      NUMBER;
  v_trans_date  DATE;

  v_prev_cust_id NUMBER := NULL;
BEGIN
  OPEN cur_transactions;

  LOOP
    FETCH cur_transactions INTO v_cust_id, v_trans_id, v_amount, v_trans_date;
    EXIT WHEN cur_transactions%NOTFOUND;

    IF v_cust_id != v_prev_cust_id THEN
      DBMS_OUTPUT.PUT_LINE('------------------------------');
      DBMS_OUTPUT.PUT_LINE('Statement for Customer ID: ' || v_cust_id);
      DBMS_OUTPUT.PUT_LINE('------------------------------');
      v_prev_cust_id := v_cust_id;
    END IF;

    DBMS_OUTPUT.PUT_LINE('Transaction ID: ' || v_trans_id ||
                         ', Date: ' || v_trans_date ||
                         ', Amount: ' || v_amount);
  END LOOP;

  CLOSE cur_transactions;
END;
/

--scenario 2--
DECLARE
  CURSOR cur_accounts IS
    SELECT AccountID, Balance
    FROM Accounts;

  v_account_id Accounts.AccountID%TYPE;
  v_balance    Accounts.Balance%TYPE;

  v_annual_fee NUMBER := 50; -- Example fee
BEGIN
  OPEN cur_accounts;

  LOOP
    FETCH cur_accounts INTO v_account_id, v_balance;
    EXIT WHEN cur_accounts%NOTFOUND;

    -- Deduct annual fee
    UPDATE Accounts
    SET Balance = Balance - v_annual_fee
    WHERE AccountID = v_account_id;

    DBMS_OUTPUT.PUT_LINE('Annual fee applied to Account ID: ' || v_account_id);
  END LOOP;

  CLOSE cur_accounts;

  COMMIT;
END;
/

--scenario 3--
DECLARE
  CURSOR cur_loans IS
    SELECT LoanID, Amount, InterestRate
    FROM Loans;

  v_loan_id     Loans.LoanID%TYPE;
  v_amount      Loans.Amount%TYPE;
  v_interest    Loans.InterestRate%TYPE;
BEGIN
  OPEN cur_loans;

  LOOP
    FETCH cur_loans INTO v_loan_id, v_amount, v_interest;
    EXIT WHEN cur_loans%NOTFOUND;

    IF v_amount > 100000 THEN
      v_interest := v_interest + 0.5; -- Increase by 0.5%
    ELSE
      v_interest := v_interest + 0.25; -- Increase by 0.25%
    END IF;

    UPDATE Loans
    SET InterestRate = v_interest
    WHERE LoanID = v_loan_id;

    DBMS_OUTPUT.PUT_LINE('Updated Loan ID: ' || v_loan_id ||
                         ', New Interest Rate: ' || v_interest);
  END LOOP;

  CLOSE cur_loans;

  COMMIT;
END;
/

FOR rec IN cur_loans LOOP
  -- Use rec.LoanID, rec.Amount, etc.
END LOOP;

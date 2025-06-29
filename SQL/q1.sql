--scenario 1--
DECLARE
  CURSOR c_customers IS
    SELECT CUSTOMER_ID
    FROM CUSTOMERS
    WHERE AGE > 60;

BEGIN
  FOR cust_rec IN c_customers LOOP
    UPDATE LOANS
    SET INTEREST_RATE = INTEREST_RATE - 1
    WHERE CUSTOMER_ID = cust_rec.CUSTOMER_ID;

    DBMS_OUTPUT.PUT_LINE('Applied discount to Customer ID: ' || cust_rec.CUSTOMER_ID);
  END LOOP;

  COMMIT;
END;
/

--scenario 2--
DECLARE
  CURSOR c_customers IS
    SELECT CUSTOMER_ID
    FROM CUSTOMERS
    WHERE BALANCE > 10000;

BEGIN
  FOR cust_rec IN c_customers LOOP
    UPDATE CUSTOMERS
    SET IS_VIP = 'Y'
    WHERE CUSTOMER_ID = cust_rec.CUSTOMER_ID;

    DBMS_OUTPUT.PUT_LINE('Promoted to VIP: Customer ID ' || cust_rec.CUSTOMER_ID);
  END LOOP;

  COMMIT;
END;
/

--scenario 3--
DECLARE
  CURSOR c_due_loans IS
    SELECT L.LOAN_ID, L.CUSTOMER_ID, L.DUE_DATE, C.NAME
    FROM LOANS L
    JOIN CUSTOMERS C ON L.CUSTOMER_ID = C.CUSTOMER_ID
    WHERE L.DUE_DATE BETWEEN SYSDATE AND SYSDATE + 30;

BEGIN
  FOR loan_rec IN c_due_loans LOOP
    DBMS_OUTPUT.PUT_LINE(
      'Reminder: Customer ' || loan_rec.NAME || 
      ' (ID: ' || loan_rec.CUSTOMER_ID || 
      ') has a loan due on ' || TO_CHAR(loan_rec.DUE_DATE, 'DD-MON-YYYY')
    );
  END LOOP;
END;
/


--scenario 1--
CREATE OR REPLACE FUNCTION CalculateAge (
  p_date_of_birth IN DATE
) RETURN NUMBER
IS
  v_age NUMBER;
BEGIN
  v_age := FLOOR(MONTHS_BETWEEN(SYSDATE, p_date_of_birth) / 12);
  RETURN v_age;
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error calculating age: ' || SQLERRM);
    RETURN NULL;
END;
/

--scenario 2--
CREATE OR REPLACE FUNCTION CalculateMonthlyInstallment (
  p_loan_amount     IN NUMBER,
  p_annual_rate_pct IN NUMBER,
  p_duration_years  IN NUMBER
) RETURN NUMBER
IS
  v_monthly_rate NUMBER;
  v_total_months NUMBER;
  v_emi NUMBER;
BEGIN
  v_monthly_rate := p_annual_rate_pct / (12 * 100);  -- convert % to decimal monthly rate
  v_total_months := p_duration_years * 12;

  IF v_monthly_rate = 0 THEN
    v_emi := p_loan_amount / v_total_months;  -- No interest case
  ELSE
    v_emi := p_loan_amount * v_monthly_rate *
             POWER(1 + v_monthly_rate, v_total_months) /
             (POWER(1 + v_monthly_rate, v_total_months) - 1);
  END IF;

  RETURN ROUND(v_emi, 2);
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error calculating installment: ' || SQLERRM);
    RETURN NULL;
END;
/

--scenario 3--
CREATE OR REPLACE FUNCTION HasSufficientBalance (
  p_account_id IN NUMBER,
  p_amount     IN NUMBER
) RETURN BOOLEAN
  v_balance NUMBER;
BEGIN
  SELECT BALANCE INTO v_balance
  FROM ACCOUNTS
  WHERE ACCOUNT_ID = p_account_id;

  RETURN v_balance >= p_amount;
EXCEPTION
  WHEN NO_DATA_FOUND THEN
    DBMS_OUTPUT.PUT_LINE('Account ID not found: ' || p_account_id);
    RETURN FALSE;
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error checking balance: ' || SQLERRM);
    RETURN FALSE;
END;
/

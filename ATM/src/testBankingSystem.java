import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class testBankingSystem {

    private Bank testBank;

    @Before public void setup() {
        testBank = Bank.getBank();
    }

    @Test public void testInitialBalance() {
        assertEquals("Should be $80.00", 80.00, testBank.checkBalance("1234", "6789"), 0.001);
    }

    @Test public void testGeneralWithdrawal() {
        assertTrue("Cannot withdraw even though there is more money than requested", testBank.withdraw("1234", "6789", 20));
        assertEquals("Final Balance is not as expected", 60, testBank.checkBalance("1234", "6789"), 0.0001);
    }

    @Test public void testExactWithdrawal() {
        assertTrue("Cannot withdraw the exact amount in balance", testBank.withdraw("1234", "6789", 80));
        assertEquals("Final Balance is not 0", 0, testBank.checkBalance("1234", "6789"), 0);
    }

    @Test public void testIncorrectValidation() {
        String accountNumber = "1234";
        String pin = "1235";
        assertFalse("validate with wring pin passes", testBank.validate(accountNumber, pin));
    }

    @Test public void testDeposit() {
        assertTrue(testBank.deposit("1234", "6789", 20));
        assertEquals("Deposit is not accounted for.", 100, testBank.checkBalance("1234", "6789"), 0.001);
    }

    @Test public void testMoreWithdrawalThanExisted() {
        assertFalse("Can Withdraw with higher amount than balance", testBank.withdraw("1234","6789", 100));
        assertEquals("Balance changed after failed withdrawal", 80, testBank.checkBalance("1234", "6789"), 0.001);

    }

    @Test(expected = AccountNotExistedException.class)
    public void testNonExistedAccount() {
        testBank.validate("1111", "blah");
    }

    @After public void tearDown() {
        testBank.resetBank();
    }

}

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestATMSystem {

    private ATM atm;

    @Before public void setup() {
        Hardware hw = null;
        atm = new ATM(hw);
        hw = new Hardware(atm);
        atm.start();
    }

    @Test public void testCorrectAccountNumberMovesToPinState() {
        atm.execute("1234");
        assertEquals("State is not ACCOUNT_NUM_ENTERED when correct acoount is entered", atm.getState(), "ACCOUNT_NUM_ENTERED");
    }

    @Test public void testInvalidAccountNumberMovesToIdle() {
        atm.execute("1111");
        assertEquals("State is not IDLE after invalid account number case 1", atm.getState(), "IDLE");
        atm.execute("sads");
        assertEquals("State is not IDLE after invalid account number case 2", atm.getState(), "IDLE");
    }

    @Test public void testValidPinMovesToValidatedState() {
        atm.execute("1234");
        atm.execute("6789");
        assertEquals("State is not ACCOUNT_VALIDATED after complete account entered", atm.getState(), "ACCOUNT_VALIDATED");
    }

    @Test public void testInvalidPinMovesToIdle() {
        atm.execute("1234");
        atm.execute("1234");
        assertEquals("State is not IDLE after invalid pin number case 1", atm.getState(), "IDLE");
        atm.execute("1234");
        atm.execute("iurthru");
        assertEquals("State is not IDLE after invalid pin number case 2", atm.getState(), "IDLE");
    }

    @Test public void testInvalidOperationMovesToIdle() {
        // case 1
        atm.execute("1234");
        assertEquals("Check moves to correct State", atm.getState(), "ACCOUNT_NUM_ENTERED");
        atm.execute("6789");
        assertEquals("Check moves to correct State", atm.getState(), "ACCOUNT_VALIDATED");
        atm.execute("nxclksd");
        assertEquals("State is not IDLE after invalid operation option case 1", atm.getState(), "IDLE");

        // case 2
        atm.execute("1234");
        assertEquals("Check moves to correct State", atm.getState(), "ACCOUNT_NUM_ENTERED");
        atm.execute("6789");
        assertEquals("Check moves to correct State", atm.getState(), "ACCOUNT_VALIDATED");
        atm.execute("n");
        assertEquals("State is not IDLE after invalid operation option case 2", atm.getState(), "IDLE");
    }

    @Test public void testValidOperationMovesToSpecificOperationState() {
        // case 1
        atm.execute("1234");
        assertEquals("Check moves to correct State", atm.getState(), "ACCOUNT_NUM_ENTERED");
        atm.execute("6789");
        assertEquals("Check moves to correct State", atm.getState(), "ACCOUNT_VALIDATED");
        atm.execute("w");
        assertEquals("State is not WITHDRAWAL_SELECTED after withdrawal operation option selected case 1", atm.getState(), "WITHDRAWAL_SELECTED");

        // case 2
        atm.start();
        atm.execute("1234");
        assertEquals("Check moves to correct State", atm.getState(), "ACCOUNT_NUM_ENTERED");
        atm.execute("6789");
        assertEquals("Check moves to correct State", atm.getState(), "ACCOUNT_VALIDATED");
        atm.execute("withdrawal");
        assertEquals("State is not WITHDRAWAL_SELECTED after withdrawal operation option selected case 1", atm.getState(), "WITHDRAWAL_SELECTED");

        // case 3
        atm.start();
        atm.execute("1234");
        assertEquals("Check moves to correct State", atm.getState(), "ACCOUNT_NUM_ENTERED");
        atm.execute("6789");
        assertEquals("Check moves to correct State", atm.getState(), "ACCOUNT_VALIDATED");
        atm.execute("deposit");
        assertEquals("State is not DEPOSIT_SELECTED after deposit operation option selected case 1", atm.getState(), "DEPOSIT_SELECTED");

        // case 4
        atm.start();
        atm.execute("1234");
        assertEquals("Check moves to correct State", atm.getState(), "ACCOUNT_NUM_ENTERED");
        atm.execute("6789");
        assertEquals("Check moves to correct State", atm.getState(), "ACCOUNT_VALIDATED");
        atm.execute("d");
        assertEquals("State is not DEPOSIT_SELECTED after deposit operation option selected case 1", atm.getState(), "DEPOSIT_SELECTED");
    }

}

public class Driver {
    public static void main(String[] args) {
        Bank testBank = new Bank();
        try {
            testBank.add("1234", "6789", 80);
            testBank.add("6789", "4321", 60);
        } catch (AccountExistedException aee) {
            System.out.println("Cannot add account: " + aee.getMessage());
        }

    }
}

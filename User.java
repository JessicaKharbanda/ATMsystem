import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private String firstName;
    private String lastName;
    private String uuid;
    private byte[] pinHash;
    private ArrayList<Account> accounts;

    // Constructor for creating a new user
    public User(String firstName, String lastName, String pin, Bank theBank) {
        this.firstName = firstName;
        this.lastName = lastName;

        // Storing the MD5 hash of the pin using a helper method
        this.pinHash = hashPin(pin);

        // Generating a new UUID for the user
        this.uuid = theBank.getNewUserUUID();

        // Creating an empty list of accounts
        this.accounts = new ArrayList<>();

        // Logging message
        System.out.printf("\nNew user %s, %s with ID %s created.\n", lastName, firstName, this.uuid);
    }

    // Helper method to hash the user's pin using MD5
    private byte[] hashPin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            handleException(e, "MD5");
        }
        return null; // Return null in case of an error
    }

    // Method to add an account to the user
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    // Getter method to retrieve the UUID of the user
    public String getUUID() {
        return this.uuid;
    }

    // Method to validate the user's pin
    public boolean validatePin(String aPin) {
        // Using the helper method to hash the input pin for comparison
        byte[] inputPinHash = hashPin(aPin);
        return MessageDigest.isEqual(inputPinHash, this.pinHash);
    }

    // Getter method to retrieve the user's first name
    public String getFirstName() {
        return this.firstName;
    }

    // Method to print a summary of the user's accounts
    public void printAccountsSummary() {
        System.out.printf("\n\n%s's accounts summary\n", this.firstName);
        for (int a = 0; a < this.accounts.size(); a++) {
            System.out.printf("%d) %s\n", a + 1, this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }

    // Method to get the number of accounts the user has
    public int numAccounts() {
        return this.accounts.size();
    }

    // Method to print the transaction history of a specific account
    public void printAcctTransHistory(int acctIdx) {
        this.accounts.get(acctIdx).printTransHistory();
    }

    // Method to get the balance of a specific account
    public double getAcctBalance(int acctIdx) {
        return this.accounts.get(acctIdx).getBalance();
    }

    // Method to get the UUID of a specific account
    public String getAcctUUID(int acctIdx) {
        return this.accounts.get(acctIdx).getUUID();
    }

    // Method to add a transaction to a specific account
    public void addAcctTransaction(int acctIdx, double amount, String memo) {
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }

    // Helper method to handle exceptions
    private void handleException(Exception e, String algorithm) {
        System.err.println("Error, caught " + e.getClass().getSimpleName() + " for algorithm: " + algorithm);
        e.printStackTrace();
        System.exit(1);
    }
}
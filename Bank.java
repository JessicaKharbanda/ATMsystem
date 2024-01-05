import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;

    /**
     * Constructor for the Bank class.
     * @param name The name of the bank.
     */
    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Generates a new unique user UUID for the bank.
     * @return A new user UUID.
     */
    public String getNewUserUUID() {
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;

        // Loop until a unique ID is generated
        do {
            uuid = "";
            for (int c = 0; c < len; c++) {
                uuid += ((Integer) rng.nextInt(10)).toString();
            }

            nonUnique = false;
            for (User u : this.users) {
                if (uuid.compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);

        return uuid;
    }

    /**
     * Generates a new unique account UUID for the bank.
     * @return A new account UUID.
     */
    public String getNewAccountUUID() {
        String uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique;

        // Loop until a unique ID is generated
        do {
            uuid = "";
            for (int c = 0; c < len; c++) {
                uuid += ((Integer) rng.nextInt(10)).toString();
            }

            nonUnique = false;
            for (Account a : this.accounts) {
                if (uuid.compareTo(a.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);

        return uuid;
    }

    /**
     * Adds an account to the bank.
     * @param anAcct The account to be added.
     */
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    /**
     * Creates a new user, adds it to the bank, and creates a savings account for the user.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param pin The PIN for the user.
     * @return The newly created user.
     */
    public User addUser(String firstName, String lastName, String pin) {
        // Create a new user and add it to the list
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        // Create a savings account for the user
        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    /**
     * Logs in a user based on the user ID and PIN.
     * @param userID The user ID.
     * @param pin The PIN.
     * @return The logged-in user or null if login fails.
     */
    public User userLogin(String userID, String pin) {
        // Search through the list
        for (User u : this.users) {
            // Check if user ID is correct
            if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Gets the name of the bank.
     * @return The name of the bank.
     */
    public String getName() {
        return this.name;
    }
}
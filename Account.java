import java.util.ArrayList;

public class Account {
    private String name;
    private String uuid;
    private User holder;
    private ArrayList<Transaction> transactions;

    // Constructor for creating a new account
    public Account(String name, User holder, Bank theBank) {
        this.name = name;
        this.holder = holder;

        // Get a new account UUID from the bank
        this.uuid = theBank.getNewAccountUUID();

        this.transactions = new ArrayList<>();
    }

    // Getter method to retrieve the UUID of the account
    public String getUUID() {
        return this.uuid;
    }

    // Method to get a summary line for the account
    public String getSummaryLine() {
        double balance = this.getBalance();

        if (balance >= 0) {
            return String.format("%s: $%.02f: %s", this.uuid, balance, this.name);
        } else {
            return String.format("%s: $(%.02f): %s", this.uuid, balance, this.name);
        }
    }

    // Method to get the balance of the account
    public double getBalance() {
        double balance = 0;
        for (Transaction t : this.transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    // Method to print the transaction history of the account
    public void printTransHistory() {
        System.out.printf("\nTransaction history for account %s\n", this.uuid);
        for (int t = this.transactions.size() - 1; t >= 0; t--) {
            System.out.println(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }

    // Method to add a new transaction to the account
    public void addTransaction(double amount, String memo) {
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }
}
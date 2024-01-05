import java.util.Date;

public class Transaction {
    private double amount;
    private Date timestamp;
    private String memo;
    private Account inAccount;

    /**
     * Constructor for a basic transaction without a memo.
     * @param amount The amount of the transaction.
     * @param inAccount The account associated with the transaction.
     */
    public Transaction(double amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = ""; // Initializing memo to an empty string
    }

    /**
     * Constructor for a transaction with a memo.
     * @param amount The amount of the transaction.
     * @param memo A memo or description for the transaction.
     * @param inAccount The account associated with the transaction.
     */
    public Transaction(double amount, String memo, Account inAccount) {
        // Call the two-arg constructor first
        this(amount, inAccount);
        // Set the memo
        this.memo = memo;
    }

    /**
     * Gets the amount of the transaction.
     * @return The transaction amount.
     */
    public double getAmount() {
        return this.amount;
    }

    /**
     * Gets a summary line for the transaction.
     * @return A formatted string representing the transaction summary.
     */
    public String getSummaryLine() {
        // Using a conditional operator to determine if the amount is positive or negative
        return (this.amount >= 0) ?
                String.format("%s : $%.02f : %s", this.timestamp.toString(), this.amount, this.memo) :
                String.format("%s : $(%.02f) : %s", this.timestamp.toString(), -this.amount, this.memo);
    }
}
import java.util.Scanner;

public class ATM {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    // Initialize the bank
    Bank theBank = new Bank("Bank of Baroda");

    // Create a user and a checking account for that user
    User aUser = theBank.addUser("Java", "Student", "1234");
    Account newAccount = new Account("Checking", aUser, theBank);
    aUser.addAccount(newAccount);
    theBank.addAccount(newAccount);

    User curUser;

    while (true) {
      // Stay in login prompt until successful login
      curUser = mainMenuPrompt(theBank, sc);

      // Stay in the main menu until the user quits
      printUserMenu(curUser, sc);
    }
  }

  // Method to prompt the user for login credentials
  public static User mainMenuPrompt(Bank theBank, Scanner sc) {
    String userID;
    String pin;
    User authUser;

    // Prompt user for ID/Pin until a correct one is entered.
    do {
      System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
      System.out.print("Enter user ID: ");
      userID = sc.nextLine();
      System.out.print("Enter pin: ");
      pin = sc.nextLine();

      authUser = theBank.userLogin(userID, pin);

      if (authUser == null) {
        System.out.println("Incorrect user ID/pin combo. Please try again.");
      }
    } while (authUser == null); // Loop until successful login

    return authUser;
  }

  // Method to display the user menu and handle user choices
  public static void printUserMenu(User theUser, Scanner sc) {
    theUser.printAccountsSummary();

    int choice = 0;

    // User menu
    do {
      System.out.printf(
        "Welcome %s, what would you like to do?\n",
        theUser.getFirstName()
      );
      System.out.println(" 1) Show account transaction history");
      System.out.println(" 2) Withdraw");
      System.out.println(" 3) Deposit");
      System.out.println(" 4) Transfer");
      System.out.println(" 5) Quit");
      System.out.print("Enter Choice: ");

      // Validate user input
      try {
        // choice = Integer.parseInt(sc.nextLine());

        choice = sc.nextInt();
        sc.nextLine();
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.");
        continue;
      }

      if (choice < 1 || choice > 5) {
        System.out.println("Invalid choice");
      }
    } while (choice < 1 || choice > 5);

    // Perform the selected action based on user choice
    switch (choice) {
      case 1:
        showTransactionHistory(theUser, sc);
        break;
      case 2:
        withdrawFunds(theUser, sc);
        break;
      case 3:
        depositFunds(theUser, sc);
        break;
      case 4:
        transferFunds(theUser, sc);
        break;
      case 5:
        sc.nextLine(); // Consume the newline character
        break;
    }

    // If the choice is not to quit, show the user menu again
    if (choice != 5) {
      printUserMenu(theUser, sc);
    }
  }

  // Method to show transaction history for a selected account
  public static void showTransactionHistory(User theUser, Scanner sc) {
    int theAcct;

    // Get the account number for which transactions should be displayed
    do {
      System.out.printf(
        "Enter the number (1-%d) of the account whose transactions you want to see: ",
        theUser.numAccounts()
      );

      theAcct = sc.nextInt() - 1;

      if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
        System.out.println("Invalid account. Please try again.");
      }
    } while (theAcct < 0 || theAcct >= theUser.numAccounts());

    // Print transaction history for the selected account
    theUser.printAcctTransHistory(theAcct);
  }

  // Method to transfer funds between two accounts
  public static void transferFunds(User theUser, Scanner sc) {
    int fromAcct;
    int toAcct;
    double amount;
    double acctBal;

    // Get the account to transfer from
    do {
      System.out.printf(
        "Enter the number (1-%d) of the account to transfer from: ",
        theUser.numAccounts()
      );
      fromAcct = sc.nextInt() - 1;

      if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
        System.out.println("Invalid account. Please try again.");
      }
    } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
    acctBal = theUser.getAcctBalance(fromAcct);

    // Get the account to transfer to
    do {
      System.out.printf(
        "Enter the number (1-%d) of the account to transfer to: ",
        theUser.numAccounts()
      );
      toAcct = sc.nextInt() - 1;

      if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
        System.out.println("Invalid account. Please try again.");
      }
    } while (toAcct < 0 || toAcct >= theUser.numAccounts());

    // Get the amount to transfer
    do {
      System.out.printf("Enter the amount to transfer (max $%.2f): $", acctBal);
      amount = sc.nextDouble();

      if (amount < 0) {
        System.out.println("Amount must be greater than zero.");
      } else if (amount > acctBal) {
        System.out.printf(
          "Amount must not be greater than balance of $%.2f\n",
          acctBal
        );
      }
    } while (amount < 0 || amount > acctBal);

    // Perform the transfer
    theUser.addAcctTransaction(
      fromAcct,
      -1 * amount,
      String.format("Transfer to account %s", theUser.getAcctUUID(toAcct))
    );
    theUser.addAcctTransaction(
      toAcct,
      amount,
      String.format("Transfer from account %s", theUser.getAcctUUID(fromAcct))
    );
  }

  // Method to withdraw funds from an account
  public static void withdrawFunds(User theUser, Scanner sc) {
    int fromAcct;
    double amount;
    double acctBal;
    String memo;

    // Get the account to withdraw from
    do {
      System.out.printf(
        "Enter the number (1-%d) of the account to withdraw from: ",
        theUser.numAccounts()
      );
      fromAcct = sc.nextInt() - 1;

      if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
        System.out.println("Invalid account. Please try again.");
      }
    } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
    acctBal = theUser.getAcctBalance(fromAcct);

    // Get the amount to withdraw
    do {
      System.out.printf("Enter the amount to withdraw (max $%.2f): $", acctBal);
      amount = sc.nextDouble();

      if (amount < 0) {
        System.out.println("Amount must be greater than zero.");
      } else if (amount > acctBal) {
        System.out.printf(
          "Amount must not be greater than balance of $%.2f\n",
          acctBal
        );
      }
    } while (amount < 0 || amount > acctBal);

    sc.nextLine(); // Consume the newline character

    // Get a memo for the withdrawal
    System.out.print("Enter a memo: ");
    memo = sc.nextLine();

    // Perform the withdrawal
    theUser.addAcctTransaction(fromAcct, -1 * amount, memo);
  }

  // Method to deposit funds into an account
  public static void depositFunds(User theUser, Scanner sc) {
    int toAcct;
    double amount;
    double acctBal;
    String memo;

    // Get the account to deposit to
    do {
      System.out.printf(
        "Enter the number (1-%d) of the account to deposit to: ",
        theUser.numAccounts()
      );
      toAcct = sc.nextInt() - 1;

      if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
        System.out.println("Invalid account. Please try again.");
      }
    } while (toAcct < 0 || toAcct >= theUser.numAccounts());
    acctBal = theUser.getAcctBalance(toAcct);

    // Get the amount to deposit
    do {
      System.out.printf("Enter the amount to deposit (max $%.2f): $", acctBal);
      amount = sc.nextDouble();

      if (amount < 0) {
        System.out.println("Amount must be greater than zero.");
      }
    } while (amount < 0);

    sc.nextLine(); // Consume the newline character

    // Get a memo for the deposit
    System.out.print("Enter a memo: ");
    memo = sc.nextLine();

    // Perform the deposit
    theUser.addAcctTransaction(toAcct, amount, memo);
  }
}
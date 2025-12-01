import java.util.*;
import java.io.*;

public class LibraryManagementSystem{
    static Scanner sc = new Scanner(System.in);

    static ArrayList<String[]> users = new ArrayList<>();
    static ArrayList<String[]> books = new ArrayList<>();
    static String currentUserID = "";

    public static void main(String[] args) {

        loadUsers();
        loadBooks();

        boolean loggedIn = false;

        System.out.println("Welcome to the Library Management System!");
        System.out.print("Do you want to log in? (yes/no): ");
        String choice = sc.nextLine();

        if (choice.equalsIgnoreCase("yes")) {
            loggedIn = login();
        } else {
            System.out.println("Exiting the system. Goodbye!");
            System.exit(0);
        }

        if (loggedIn) {
            mainMenu();
        }

        sc.close();
    }

    public static boolean login() {
        while (true) {
            System.out.print("Do you have an account? (yes/no): ");
            String hasAccount = sc.nextLine();

            if (hasAccount.equalsIgnoreCase("yes")) {
                System.out.print("Enter your email: ");
                String email = sc.nextLine();
                System.out.print("Enter your password: ");
                String password = sc.nextLine();

                for (String[] u : users) {
                    if (u[2].equals(email) && u[3].equals(password)) {
                        currentUserID = u[0];
                        System.out.println("Login successful! Welcome " + u[1]);
                        return true;
                    }
                }

                System.out.println("Email or password incorrect. Try again.");

            } else {
                register();
            }
        }
    }

    public static void register() {
        System.out.println("Register a new account:");
        System.out.print("Enter your ID: ");
        String id = sc.nextLine();
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter your email: ");
        String email = sc.nextLine();
        System.out.print("Enter your password: ");
        String password = sc.nextLine();
        System.out.print("Enter your role (Student/Staff): ");
        String role = sc.nextLine();
        String borrowedCount = "0";

        String[] newUser = {id, name, email, password, role, borrowedCount};
        users.add(newUser);
        saveUsers();
        System.out.println("Registration successful! Please log in.");
    }

    public static void mainMenu() {
        while (true) {
            System.out.println("\nWelcome to the Main Menu!");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Issue Books");
            System.out.println("4. Return Books");
            System.out.println("5. Check Book Availability");
            System.out.println("6. Display Borrow Limit");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = -1;
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                sc.nextLine(); 
                continue;
            }
            sc.nextLine();

            switch (choice) {
                case 1: addBook(); break;
                case 2: removeBook(); break;
                case 3: issueBook(); break;
                case 4: returnBook(); break;
                case 5: checkBookAvailability(); break;
                case 6: displayBorrowLimit(); break;
                case 7: exit(); break;
                default: System.out.println("Enter correct option!");
            }
        }
    }

    public static void addBook() { 
        System.out.println("Add Book feature coming soon!"); 
    }
    public static void removeBook() { 
        System.out.println("Remove Book feature coming soon!"); 
    }
    public static void issueBook() {
       System.out.println("Issue Book feature coming soon!"); 
    }
    public static void returnBook() { 
        System.out.println("Return Book feature coming soon!"); 
    }
    public static void checkBookAvailability() {
    System.out.print("Enter Book ID or Book Name to check availability: ");
    String input = sc.nextLine().trim();

    String[] foundBook = null;

    for (String[] b : books) {
        if (b[0].equalsIgnoreCase(input) || b[1].equalsIgnoreCase(input)) {
            foundBook = b;
            break;
        }
    }

    if (foundBook == null) {
        System.out.println("Book not found!");
        return;
    }

    String status = foundBook[3].trim().toLowerCase();
    if (status.contains("true")) status = "true";
    if (status.contains("false")) status = "false";

    boolean isAvailable = status.equals("true") || status.equals("1");

    System.out.println("\n--- Book Details ---");
    System.out.println("Book ID: " + foundBook[0]);
    System.out.println("Book Name: " + foundBook[1]);
    System.out.println("Author: " + foundBook[2]);
    System.out.println("Status: " + (isAvailable ? "AVAILABLE " : "NOT AVAILABLE "));
}

   public static void displayBorrowLimit() {
    String[] currentUser = null;
    for (int i = 0; i < users.size(); i++) {
        String[] u = users.get(i);
        if (u[0].equals(currentUserID)) {
            currentUser = u;
            break;
        }
    }

    if (currentUser == null) {
        System.out.println("User not found!");
        return;
    }
    int borrowLimit = 0;
    if (currentUser[4].equalsIgnoreCase("Student")) {
        borrowLimit = 3;
    } else if (currentUser[4].equalsIgnoreCase("Staff")) {
        borrowLimit = 5;
    }
    int borrowedCount = Integer.parseInt(currentUser[5]);
    int remaining = borrowLimit - borrowedCount;
    System.out.println("Your borrow limit: " + borrowLimit + " books.");
    System.out.println("Books already borrowed: " + borrowedCount);
    System.out.println("You can still borrow: " + remaining + " books.");
}

    public static void exit() { 
        System.out.println("Exiting... Goodbye!"); 
        System.exit(0); 
    }

    public static void loadUsers() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                users.add(line.split(","));
            }
            br.close();
        } catch (Exception e) {
            System.out.println("users.txt not found. Starting with empty list.");
        }
    }

    public static void saveUsers() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt"));
            for (String[] u : users) {
                bw.write(String.join(",", u));
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("Error saving users.");
        }
    }

    public static void loadBooks() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("books.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                books.add(line.split(","));
            }
            br.close();
        } catch (Exception e) {
            System.out.println("books.txt not found. Starting with empty list.");
        }
    }

    public static void saveBooks() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("books.txt"));
            for (String[] b : books) {
                bw.write(String.join(",", b));
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("Error saving books.");
        }
    }
}

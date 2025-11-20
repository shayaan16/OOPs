import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== LIBRARY MENU =====");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Show All Books");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID: ");
                    String bid = sc.nextLine();
                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();
                    library.addBook(new Book(bid, title, author));
                    break;

                case 2:
                    System.out.print("Enter Member ID: ");
                    String mid = sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    library.addMember(new Member(mid, name));
                    break;

                case 3:
                    System.out.print("Enter Book ID: ");
                    String ibid = sc.nextLine();
                    System.out.print("Enter Member ID: ");
                    String imid = sc.nextLine();
                    library.issueBook(ibid, imid);
                    break;

                case 4:
                    System.out.print("Enter Book ID: ");
                    String rb = sc.nextLine();
                    try {
                        library.returnBook(rb);
                    } catch (InvalidReturnException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 5:
                    library.showAllBooks();
                    break;

                case 0:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option!");
            }

        } while (choice != 0);

        sc.close();
    }
}

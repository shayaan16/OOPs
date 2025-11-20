import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

public class Library {

    private HashMap<String, Book> books = new HashMap<>();
    private HashMap<String, Member> members = new HashMap<>();
    private HashMap<String, LocalDate> issuedDates = new HashMap<>();
    private FileWriter logger;

    public Library() {
        try {
            File logDir = new File("logs");
            if (!logDir.exists()) {
                logDir.mkdir();
            }

            logger = new FileWriter("logs/operations.log", true);

        } catch (IOException e) {
            System.out.println("Failed to open log file: " + e.getMessage());
            logger = null; // prevent NullPointerException
        }
    }

    private void log(String message) {
        try {
            logger.write(LocalDate.now() + ": " + message + "\n");
            logger.flush();
        } catch (IOException e) {
            System.out.println("Log error.");
        }
    }

    public void addBook(Book book) {
        books.put(book.getId(), book);
        log("Book added: " + book.getId());
    }

    public void addMember(Member member) {
        members.put(member.getId(), member);
        log("Member added: " + member.getId());
    }

    public void issueBook(String bookId, String memberId) {
        if (!books.containsKey(bookId)) {
            System.out.println("Book not found!");
            return;
        }
        if (!members.containsKey(memberId)) {
            System.out.println("Member not found!");
            return;
        }

        Book b = books.get(bookId);
        if (b.isIssued()) {
            System.out.println("Book already issued!");
            return;
        }

        b.issue();
        issuedDates.put(bookId, LocalDate.now());

        log("Book issued: " + bookId + " to member " + memberId);
        System.out.println("Book issued successfully.");
    }

    public void returnBook(String bookId) throws InvalidReturnException {
        if (!books.containsKey(bookId)) {
            throw new InvalidReturnException("Book not found in library records!");
        }

        Book b = books.get(bookId);
        if (!b.isIssued()) {
            throw new InvalidReturnException("This book was not issued!");
        }

        LocalDate issueDate = issuedDates.get(bookId);
        long daysLate = java.time.temporal.ChronoUnit.DAYS.between(issueDate, LocalDate.now()) - 7;

        double lateFee = daysLate > 0 ? daysLate * 2 : 0;

        b.returnBook();
        issuedDates.remove(bookId);

        log("Book returned: " + bookId + " Late fees: " + lateFee);
        System.out.println("Book returned successfully. Late fees: ₹" + lateFee);
    }

    public void showAllBooks() {
        books.values().forEach(System.out::println);
    }
}

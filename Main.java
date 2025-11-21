package Project02;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter folder path containing log files: ");
        String folder = sc.nextLine();

        Map<String, Integer> aggregate = new HashMap<>();
        List<Thread> threads = new ArrayList<>();

        long start = System.currentTimeMillis();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(folder), "*.txt")) {
            for (Path file : stream) {
                Thread t = new Thread(new FileProcessor(file, aggregate));
                threads.add(t);
                t.start();
            }
        } catch (IOException e) {
            System.out.println("Error accessing folder: " + folder);
            e.printStackTrace();
        }

        // Wait for all threads to finish
        for (Thread t : threads) {
            t.join();
        }

        long end = System.currentTimeMillis();

        System.out.println("\n=== Keyword Counts ===");
        aggregate.forEach((k, v) -> System.out.println(k + " : " + v));
        System.out.println("\nExecution time: " + (end - start) + " ms");

        sc.close();
    }
}

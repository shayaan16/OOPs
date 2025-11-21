package Project02;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class LogAnalyzer {
    private Map<String, Integer> aggregate = new HashMap<>();

    public Map<String, Integer> analyzeFolder(String folderPath) {
        List<Thread> threads = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(folderPath), "*.txt")) {
            for (Path file : stream) {
                Thread t = new Thread(new FileProcessor(file, aggregate));
                threads.add(t);
                t.start();
            }
        } catch (IOException e) {
            System.out.println("Error accessing folder: " + folderPath);
            e.printStackTrace();
        }

        // Wait for all threads to finish
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return aggregate;
    }
}

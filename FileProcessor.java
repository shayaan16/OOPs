package Project02;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class FileProcessor implements Runnable {
    private Path filePath;
    private Map<String, Integer> aggregate;

    public FileProcessor(Path filePath, Map<String, Integer> aggregate) {
        this.filePath = filePath;
        this.aggregate = aggregate;
    }

    @Override
    public void run() {
        Map<String, Integer> localCount = new HashMap<>();
        try {
            Files.lines(filePath).forEach(line -> {
                String[] words = line.split("\\s+");
                for (String w : words) {
                    if (!w.isEmpty()) {
                        localCount.put(w, localCount.getOrDefault(w, 0) + 1);
                    }
                }
            });

            // Merge localCount into shared aggregate (synchronized)
            synchronized (aggregate) {
                localCount.forEach((k, v) -> aggregate.put(k, aggregate.getOrDefault(k, 0) + v));
            }

            System.out.println("Processed file: " + filePath.getFileName() + " in " + Thread.currentThread().getName());
        } catch (IOException e) {
            System.out.println("Error reading file: " + filePath);
        }
    }
}

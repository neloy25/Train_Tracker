import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;

public class TimeData {
    // Listeners that get notified each minute
    private static final List<Runnable> listeners = new ArrayList<>();
    private static Thread timerThread = null;

    // Start the background ticker (call once at app startup)
    public static synchronized void startTicker() {
        if (timerThread != null && timerThread.isAlive()) return;
        timerThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // Sleep until the start of the next minute
                    long now = System.currentTimeMillis();
                    long sleepMs = 60_000 - (now % 60_000);
                    Thread.sleep(sleepMs);
                    notifyListeners();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        timerThread.setDaemon(true);
        timerThread.start();
    }

    public static synchronized void addListener(Runnable r) {
        listeners.add(r);
    }

    public static synchronized void removeListener(Runnable r) {
        listeners.remove(r);
    }

    private static void notifyListeners() {
        // Copy to avoid ConcurrentModificationException
        List<Runnable> copy;
        synchronized (TimeData.class) { copy = new ArrayList<>(listeners); }
        for (Runnable r : copy) {
            try { r.run(); } catch (Exception ignored) {}
        }
    }

    // Methods fetch fresh time every time they are called
    public static String getTime() {
        LocalDateTime now = LocalDateTime.now();
        return String.format("%02d:%02d", now.getHour(), now.getMinute());
    }

    public static String getDay() {
        LocalDateTime now = LocalDateTime.now();
        return now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    public static String getDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return now.format(formatter);
    }
}
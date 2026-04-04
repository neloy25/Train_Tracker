import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class TimeData {
    private static final String time;
    private static final String day;

    static {
        LocalDateTime now = LocalDateTime.now();
        time = String.format("%02d:%02d", now.getHour(), now.getMinute());
        day = now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    public static String getTime() { return time; }
    public static String getDay() { return day; }
}
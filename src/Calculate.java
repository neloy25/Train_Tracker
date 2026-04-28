public class Calculate {
    public static boolean compareTime(String time1, String time2) {
        String[] t1 = time1.split(":");
        int h1 = Integer.parseInt(t1[0]);
        int m1 = Integer.parseInt(t1[1]);

        String[] t2 = time2.split(":");
        int h2 = Integer.parseInt(t2[0]);
        int m2 = Integer.parseInt(t2[1]);

        int total1 = h1 * 60 + m1;
        int total2 = h2 * 60 + m2;

        if (Math.abs(total1 - total2) > 720) { // 12 hours
            if (total1 < total2) total1 += 1440;
            else total2 += 1440;
        }

        return total1 <= total2;
    }

    public static int timeDifference(String t1, String t2) {
        String[] a = t1.split(":");
        int h1 = Integer.parseInt(a[0]);
        int m1 = Integer.parseInt(a[1]);

        String[] b = t2.split(":");
        int h2 = Integer.parseInt(b[0]);
        int m2 = Integer.parseInt(b[1]);

        int total1 = h1 * 60 + m1;
        int total2 = h2 * 60 + m2;

        int diff = total2 - total1;

        // handle crossing midnight
        if (diff < 0) diff += 1440;

        return diff;
    }

    public static int toMinutes(String time) {
        String[] parts = time.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }

    public static double calcDistance(int distanceKm, String t1, String t2, String t3) {
        double gone = (double) timeDifference(t1, t2);
        double total = (double) timeDifference(t1, t3);

        if (total == 0) return 0; // safety

        return (gone / total) * distanceKm;
    }
}

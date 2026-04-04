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
}

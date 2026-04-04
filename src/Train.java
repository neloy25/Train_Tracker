import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Train {
    private final int number;
    private final String name;
    private final String startStation;
    private final String startTime;
    private final String reverseStartTime;
    private final String endStation;
    private final String endTime;
    private final String reverseEndTime;
    private final ArrayList<Station> stations;
    private final ArrayList<Station> reverseStations;
    private final ArrayList<String> offDays;

    public Train(int number, String name, String startStation, String startTime,
                 String reverseStartTime, String endStation, String endTime,
                 String reverseEndTime, ArrayList<Station> stations, ArrayList<String> offDays) {
        this.number = number;
        this.name = name;
        this.startStation = startStation;
        this.startTime = startTime;
        this.reverseStartTime = reverseStartTime;
        this.endStation = endStation;
        this.endTime = endTime;
        this.reverseEndTime = reverseEndTime;
        this.stations = stations;
        this.offDays = offDays;
        reverseStations = new ArrayList<>(stations);
        Collections.reverse(reverseStations);
    }

    public static Train toFileTrain(String line) {
        String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        int number = Integer.parseInt(parts[0]);
        String name = parts[1];
        String startStation = parts[2];
        String startTime = parts[3];
        String reverseStartTime = parts[4];
        String endStation = parts[5];
        String endTime = parts[6];
        String reverseEndTime = parts[7];

        String stationStr = parts[8].replace("\"", "");
        String[] stationList = stationStr.split(";");

        ArrayList<Station> stations = new ArrayList<>();

        for (String s : stationList) {
            String[] p = s.split("\\|");

            stations.add(new Station(
                    p[0], p[1], p[2], p[3], p[4],
                    p[5], Integer.parseInt(p[6])
            ));
        }

        String[] off = parts[9].replace("\"", "").split("\\|");
        ArrayList<String> offDays = new ArrayList<>(Arrays.asList(off));

        return new Train(number, name, startStation, startTime,
                reverseStartTime, endStation, endTime,
                reverseEndTime, stations, offDays);
    }

    public void trackTrain(String currTime) {
        for (int i = 0; i < stations.size(); i++) {
            Station s = stations.get(i);
            String arr = s.getArrivalTime();
            String dep = s.getDepartureTime();

            // Start station
            if (i == 0) {
                if (Calculate.compareTime(currTime, dep)) {
                    System.out.println("Train will leave " + s.getName());
                    return;
                } else {
                    System.out.println("Train has left " + s.getName());
                    continue;
                }
            }

            // End station
            if (i == stations.size() - 1) {
                if (Calculate.compareTime(currTime, arr)) {
                    System.out.println("Train will reach " + s.getName());
                } else {
                    System.out.println("Train is at " + s.getName());
                }
                return;
            }

            // Middle station
            if (Calculate.compareTime(currTime, arr)) {
                System.out.println("Train will reach " + s.getName());
                return;
            }
            if (Calculate.compareTime(currTime, dep)) {
                System.out.println("Train is at " + s.getName());
                return;
            }
            System.out.println("Train has left " + s.getName());
        }
    }

    public void reverseTrackTrain(String currTime) {
        for (int i = 0; i < reverseStations.size(); i++) {
            Station s = reverseStations.get(i);
            String arr = s.getReverseArrivalTime();
            String dep = s.getReverseDepartureTime();

            // Start station
            if (i == 0) {
                if (Calculate.compareTime(currTime, dep)) {
                    System.out.println("Train will leave " + s.getName());
                    return;
                } else {
                    System.out.println("Train has left " + s.getName());
                    continue;
                }
            }

            // End station
            if (i == reverseStations.size() - 1) {
                if (Calculate.compareTime(currTime, arr)) {
                    System.out.println("Train will reach " + s.getName());
                } else {
                    System.out.println("Train is at " + s.getName());
                }
                return;
            }

            // Middle station
            if (Calculate.compareTime(currTime, arr)) {
                System.out.println("Train will reach " + s.getName());
                return;
            }
            if (Calculate.compareTime(currTime, dep)) {
                System.out.println("Train is at " + s.getName());
                return;
            }
            System.out.println("Train has left " + s.getName());
        }
    }

    public boolean track(String currTime) {
        if (!Calculate.compareTime(currTime, startTime)
                && Calculate.compareTime(currTime, endTime)) {

            trackTrain(currTime);
            return true;

        } else if (!Calculate.compareTime(currTime, reverseStartTime)
                && Calculate.compareTime(currTime, reverseEndTime)) {

            reverseTrackTrain(currTime);
            return false;

        } else {
            if (Calculate.compareTime(currTime, startTime)) {
                System.out.println("Train is at " + startStation);
                return true;
            } else {
                System.out.println("Train is at " + endStation);
                return false;
            }
        }
    }
    // getters
    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getStartStation() {
        return startStation;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getReverseStartTime() {
        return reverseStartTime;
    }

    public String getEndStation() {
        return endStation;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getReverseEndTime() {
        return reverseEndTime;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    public ArrayList<String> getOffDays() {
        return offDays;
    }

}

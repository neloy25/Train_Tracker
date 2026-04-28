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

    public TrackResult trackTrain() {
        String localTime = TimeData.getTime();

        if (!Calculate.compareTime(localTime, startTime) &&
                Calculate.compareTime(localTime, endTime)) {

            TrackResult res = forwardTrack();
            res.isForward = true;
            return res;
        }

        else if (!Calculate.compareTime(localTime, reverseStartTime) &&
                Calculate.compareTime(localTime, reverseEndTime)) {

            TrackResult res = reverseTrack();
            res.isForward = false;
            return res;
        }

        else if (!Calculate.compareTime(localTime, endTime) &&
                Calculate.compareTime(localTime, reverseStartTime)) {

            TrackResult res = new TrackResult();
            res.status = "Train is at " + endStation;
            res.nextStation = endStation;
            res.isForward = false; // about to go reverse
            return res;
        }

        else {
            TrackResult res = new TrackResult();
            res.status = "Train is at " + startStation;
            res.nextStation = startStation;
            res.isForward = true; // about to go forward
            return res;
        }
    }

    public TrackResult forwardTrack() {
        TrackResult res = new TrackResult();
        res.isForward = true;
        String localTime = TimeData.getTime();

        int n = stations.size();

        Station first = stations.get(0);
        if (Calculate.compareTime(localTime, first.getDepartureTime())) {
            res.status = "Train is at " + first.getName();
            res.nextStation = first.getName();
            return res;
        }
        res.stationsPassed++;
        Station prev = first;

        for (int i = 1; i < n - 1; i++) {
            Station s = stations.get(i);

            if (Calculate.compareTime(localTime, s.getArrivalTime())) {
                res.status = "Train will reach " + s.getName();
                res.nextStation = s.getName();
                res.distance += Calculate.calcDistance(prev.getNextStationDistance(), prev.getDepartureTime(), localTime, s.getArrivalTime());
                return res;
            }
            else if (Calculate.compareTime(localTime, s.getDepartureTime())) {
                res.status = "Train is at " + s.getName();
                res.nextStation = s.getName();
                res.distance += prev.getNextStationDistance();
                return res;
            }
            res.distance += prev.getNextStationDistance();
            prev = s;
            res.stationsPassed++;
        }

        Station last = stations.get(n - 1);

        if (Calculate.compareTime(localTime, last.getArrivalTime())) {
            res.status = "Train will reach " + last.getName();
            res.nextStation = last.getName();
            res.distance += Calculate.calcDistance(prev.getNextStationDistance(), prev.getDepartureTime(), localTime, last.getArrivalTime());
        } else {
            res.distance += prev.getNextStationDistance();
            res.status = "Train has reached " + last.getName();
            res.nextStation = last.getName();
            res.stationsPassed++;
        }
        return res;
    }

    public TrackResult reverseTrack() {
        TrackResult res = new TrackResult();
        res.isForward = false;

        String localTime = TimeData.getTime();
        int n = reverseStations.size();

        Station first = reverseStations.get(0);

        if (Calculate.compareTime(localTime, first.getReverseDepartureTime())) {
            res.status = "Train is at " + first.getName();
            res.nextStation = first.getName();
            return res;
        }

        res.stationsPassed++;
        Station prev = first;

        for (int i = 1; i < n - 1; i++) {
            Station curr = reverseStations.get(i);

            if (Calculate.compareTime(localTime, curr.getReverseArrivalTime())) {
                res.status = "Train will reach " + curr.getName();
                res.nextStation = curr.getName();

                // FIX: Use curr.getNextStationDistance() for reverse leg distance
                res.distance += Calculate.calcDistance(
                        curr.getNextStationDistance(),
                        prev.getReverseDepartureTime(),
                        localTime,
                        curr.getReverseArrivalTime()
                );
                return res;
            }

            if (Calculate.compareTime(localTime, curr.getReverseDepartureTime())) {
                res.status = "Train is at " + curr.getName();
                res.nextStation = curr.getName();

                // FIX: Use curr.getNextStationDistance()
                res.distance += curr.getNextStationDistance();
                return res;
            }

            // FIX: Use curr.getNextStationDistance()
            res.distance += curr.getNextStationDistance();
            prev = curr;
            res.stationsPassed++;
        }

        Station last = reverseStations.get(n - 1);

        if (Calculate.compareTime(localTime, last.getReverseArrivalTime())) {
            res.status = "Train will reach " + last.getName();
            res.nextStation = last.getName();

            // FIX: Use last.getNextStationDistance()
            res.distance += Calculate.calcDistance(
                    last.getNextStationDistance(),
                    prev.getReverseDepartureTime(),
                    localTime,
                    last.getReverseArrivalTime()
            );
        } else {
            // FIX: Use last.getNextStationDistance()
            res.distance += last.getNextStationDistance();
            res.status = "Train has reached " + last.getName();
            res.nextStation = last.getName();
            res.stationsPassed++;
        }

        return res;
    }

    @Override
    public String toString() {
        return number + " - " + name;
    }

    public int getTotalDistance() {
        int sum = 0;
        for (Station s : stations) {
            sum += s.getNextStationDistance();
        }
        return sum;
    }

    // =====================
    // GETTERS
    // =====================
    public int getNumber() { return number; }
    public String getName() { return name; }
    public String getStartStation() { return startStation; }
    public String getStartTime() { return startTime; }
    public String getReverseStartTime() { return reverseStartTime; }
    public String getEndStation() { return endStation; }
    public String getEndTime() { return endTime; }
    public String getReverseEndTime() { return reverseEndTime; }
    public ArrayList<Station> getStations() { return stations; }
    public ArrayList<String> getOffDays() { return offDays; }
}
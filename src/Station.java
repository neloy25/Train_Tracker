public class Station {
    private final String name;
    private final String arrivalTime;
    private final String departureTime;
    private final String reverseArrivalTime;
    private final String reverseDepartureTime;
    private final String nextStationTime;
    private final int nextStationDistance;

    public Station(String name, String arrivalTime, String departureTime, String reverseArrivalTime,
                   String reverseDepartureTime, String nextStationTime, int nextStationDistance) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.reverseArrivalTime = reverseArrivalTime;
        this.reverseDepartureTime = reverseDepartureTime;
        this.nextStationTime = nextStationTime;
        this.nextStationDistance = nextStationDistance;
    }

    //getters

    public String getName() {
        return name;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getReverseArrivalTime() {
        return reverseArrivalTime;
    }

    public String getReverseDepartureTime() {
        return reverseDepartureTime;
    }

    public String getNextStationTime() {
        return nextStationTime;
    }

    public int getNextStationDistance() {
        return nextStationDistance;
    }
}

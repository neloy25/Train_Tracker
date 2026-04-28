public class TrackResult {
    public String status;
    public String nextStation;
    public int stationsPassed;
    public boolean isForward;
    public double distance;

    public TrackResult() {
        status        = "";
        nextStation   = "";
        stationsPassed = 0;
        isForward     = true;
        distance = 0;
    }
}
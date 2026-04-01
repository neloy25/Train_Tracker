public class Station {
    private  String stationName;
    private  String arrivalTime;
    private  String reverseArrivalTime;
    private int waitTime;
    public Station(String stationName,String arrivalTime,String reverseArrivalTime,int waitTime)
    {
        this.arrivalTime=arrivalTime;
        this.stationName=stationName;
        this.reverseArrivalTime= reverseArrivalTime;
    }
}

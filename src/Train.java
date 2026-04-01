import java.util.ArrayList;
import java.util.stream.Stream;

public class Train {
    private String trainName ;
    private int trainNumber ;
    private String startStaion;
    private  String destination;
    private  String nextStaion;

    private  int delayTime;
    ArrayList<Station> stations;


    public  Train(int trainNumber,String trainName,String startStaion,String destination,
                  ArrayList<Station>stations )
    {

     this.trainName=trainName;
     this.trainNumber=trainNumber;

     this.startStaion = startStaion;
     this.destination=destination;

     this.delayTime=delayTime;
     this.stations=stations;

    }






}

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Train> trains = TrainData.loadAll();

        System.out.println(trains.get(1).getName());
//        trains.get(2).trackTrain("5:40");



        Train t = trains.get(1);
        String time = TimeData.getTime();
        String day = TimeData.getDay();
        if(t.track(time)) {
            System.out.println("Start : " + t.getStartStation());
            System.out.println("End : " + t.getEndStation());
        } else {
            System.out.println("Start : " + t.getEndStation());
            System.out.println("End : " + t.getStartStation());
        }
    }
}

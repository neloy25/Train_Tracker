import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TrainData {
    public static ArrayList<Train> loadAll() {
        ArrayList<Train> trains = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("train.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if(line.trim().isEmpty()) continue;

                trains.add(Train.toFileTrain(line));
            }

        } catch (IOException e) {
            System.out.println("Error file: " + e.getMessage());
        }

        return trains;
    }
}

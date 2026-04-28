import javax.swing.*;
import java.sql.Time;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        TrainDataGenerator.main(null);
        SwingUtilities.invokeLater(TrainTrackerUI::new);
    }
}

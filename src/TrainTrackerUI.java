import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TrainTrackerUI extends JFrame {

    private JComboBox<String> trainDropdown;
    private JLabel trainNumVal, trainNameVal, startVal, departVal, destVal, arriveVal, stopsVal, nextStatVal, nextStopVal, distVal;
    private CustomTrainProgressBar progressBar;

    private ArrayList<Train> trains;

    // For dragging the undecorated window
    private Point posX;

    // UI Colors
    private final Color MAROON_HEADER   = new Color(139, 58, 58);
    private final Color LIGHT_GREEN_BG  = new Color(215, 228, 189);
    private final Color DARK_PANEL_BG   = new Color(64, 64, 64);
    private final Color TEXT_CYAN       = new Color(135, 206, 235);
    private final Color PROGRESS_BG     = new Color(192, 192, 192);

    public TrainTrackerUI() {
        // Start the per-minute ticker
        TimeData.startTicker();

        // Load all trains from file
        trains = TrainData.loadAll();

        setUndecorated(true);
        setSize(780, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // ===== HEADER =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(MAROON_HEADER);
        headerPanel.setCursor(new Cursor(Cursor.MOVE_CURSOR));

        headerPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) { posX = e.getPoint(); }
        });
        headerPanel.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point curr = e.getLocationOnScreen();
                setLocation(curr.x - posX.x, curr.y - posX.y);
            }
        });

        JLabel headerLabel = new JLabel("Bangladesh Train Tracker", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(new EmptyBorder(10, 50, 10, 10));

        JLabel closeBtns = new JLabel("x -  ");
        closeBtns.setForeground(Color.WHITE);
        closeBtns.setFont(new Font("Arial", Font.BOLD, 18));
        closeBtns.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeBtns.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { System.exit(0); }
        });

        headerPanel.add(headerLabel, BorderLayout.CENTER);
        headerPanel.add(closeBtns, BorderLayout.EAST);

        // ===== MAIN CONTENT =====
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(LIGHT_GREEN_BG);
        mainContent.setBorder(BorderFactory.createLineBorder(MAROON_HEADER, 1));

        // ===== LEFT PANEL =====
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        leftPanel.setPreferredSize(new Dimension(240, 400));
        leftPanel.setBorder(new EmptyBorder(20, 10, 20, 10));

        JLabel selectLabel = new JLabel("Select Train");
        selectLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        selectLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Populate dropdown from loaded trains
        String[] trainItems = new String[trains.size()];
        for (int i = 0; i < trains.size(); i++) {
            trainItems[i] = trains.get(i).getNumber() + " - " + trains.get(i).getName();
        }
        trainDropdown = new JComboBox<>(trainItems);
        trainDropdown.setMaximumSize(new Dimension(200, 30));
        trainDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setOpaque(false);
        logoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        try {
            ImageIcon icon = new ImageIcon("logos.png");
            Image scaled = icon.getImage().getScaledInstance(180, 100, Image.SCALE_SMOOTH);
            logoPanel.add(new JLabel(new ImageIcon(scaled)));
        } catch (Exception e) {
            logoPanel.add(new JLabel("[Logo Not Found]"));
        }

        JLabel copyright = new JLabel("Copyright@2026, CSE RUET");
        copyright.setFont(new Font("Arial", Font.PLAIN, 12));
        copyright.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftPanel.add(selectLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(trainDropdown);
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(logoPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(copyright);

        // ===== RIGHT PANEL =====
        JPanel rightPanelContainer = new JPanel(new BorderLayout());
        rightPanelContainer.setOpaque(false);
        rightPanelContainer.setBorder(new EmptyBorder(20, 10, 20, 20));

        JPanel infoPanel = new JPanel(new GridLayout(10, 2, 5, 5));
        infoPanel.setBackground(DARK_PANEL_BG);
        infoPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        trainNumVal  = createValLabel();
        trainNameVal = createValLabel();
        startVal     = createValLabel();
        departVal    = createValLabel();
        destVal      = createValLabel();
        arriveVal    = createValLabel();
        stopsVal     = createValLabel();
        nextStatVal  = createValLabel();
        nextStopVal  = createValLabel();
        distVal      = createValLabel();

        addInfoRow(infoPanel, "1. Train Number:",        trainNumVal);
        addInfoRow(infoPanel, "2. Train Name:",          trainNameVal);
        addInfoRow(infoPanel, "3. Start Station:",       startVal);
        addInfoRow(infoPanel, "4. Time of Departure:",   departVal);
        addInfoRow(infoPanel, "5. Destination Station:", destVal);
        addInfoRow(infoPanel, "6. Time of Arrival:",     arriveVal);
        addInfoRow(infoPanel, "7. Number of Stations:",  stopsVal);
        addInfoRow(infoPanel, "8. Current Status:",      nextStatVal);
        addInfoRow(infoPanel, "9. Next Station:",        nextStopVal);
        addInfoRow(infoPanel, "10. Total Distance:",     distVal);

        // Initial progress bar
        progressBar = new CustomTrainProgressBar(1, 0, 0.0, "Loading...");

        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(PROGRESS_BG);
        footerPanel.add(progressBar, BorderLayout.CENTER);

        rightPanelContainer.add(infoPanel, BorderLayout.CENTER);
        rightPanelContainer.add(footerPanel, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);
        mainContent.add(leftPanel, BorderLayout.WEST);
        mainContent.add(rightPanelContainer, BorderLayout.CENTER);
        add(mainContent, BorderLayout.CENTER);

        // Load first train on start
        loadSelectedTrain();

        // Update on dropdown change
        trainDropdown.addActionListener(e -> loadSelectedTrain());

        // Auto-refresh every minute when time ticks
        TimeData.addListener(() -> SwingUtilities.invokeLater(this::loadSelectedTrain));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadSelectedTrain() {
        int idx = trainDropdown.getSelectedIndex();
        if (idx < 0 || idx >= trains.size()) return;

        Train t = trains.get(idx);
        TrackResult res = t.trackTrain();

        trainNumVal.setText(String.valueOf(t.getNumber()));
        trainNameVal.setText(t.getName());

        if (res.isForward) {
            startVal.setText(t.getStartStation());
            departVal.setText(t.getStartTime());
            destVal.setText(t.getEndStation());
            arriveVal.setText(t.getEndTime());
        } else {
            startVal.setText(t.getEndStation());
            departVal.setText(t.getReverseStartTime());
            destVal.setText(t.getStartStation());
            arriveVal.setText(t.getReverseEndTime());
        }

        int totalStations = t.getStations().size();
        stopsVal.setText(String.valueOf(totalStations));

        nextStatVal.setText(res.status);
        nextStopVal.setText(res.nextStation);

        int totalDist = t.getTotalDistance();
        distVal.setText(totalDist + " km");

        int distCovered = (int) Math.round(res.distance);
        int passedStations = res.stationsPassed;
        if (res.status.startsWith("Train is at") || res.status.startsWith("Train has reached")) {
            passedStations = res.stationsPassed + 1;
        }

        int remaining = Math.max(0, totalDist - distCovered);
        String endTime = res.isForward ? t.getEndTime() : t.getReverseEndTime();
        int remainingMins = Calculate.timeDifference(TimeData.getTime(), endTime);
        if (remainingMins > 1440) remainingMins = 0;
        int remH = remainingMins / 60;
        int remM = remainingMins % 60;
        String footerText = "Remaining " + remaining + " km in "
                + String.format("%02d:%02d", remH, remM) + " hours";

        double fraction = totalDist > 0 ? (double) distCovered / totalDist : 0.0;

        Container footer = progressBar.getParent();
        footer.remove(progressBar);
        progressBar = new CustomTrainProgressBar(totalStations, passedStations, fraction, footerText);
        footer.add(progressBar, BorderLayout.CENTER);
        footer.revalidate();
        footer.repaint();
    }

    private JLabel createValLabel() {
        JLabel label = new JLabel();
        label.setForeground(TEXT_CYAN);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private void addInfoRow(JPanel panel, String text, JLabel val) {
        JLabel l = new JLabel(text);
        l.setForeground(Color.WHITE);
        panel.add(l);
        panel.add(val);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TrainTrackerUI::new);
    }
}

class CustomTrainProgressBar extends JPanel {
    private final int totalDots;
    private final int completedDots;
    private final double fraction;
    private final String text;

    public CustomTrainProgressBar(int total, int completed, double fraction, String txt) {
        this.totalDots     = Math.max(total, 1);
        this.completedDots = completed;
        this.fraction      = Math.max(0.0, Math.min(1.0, fraction));
        this.text          = txt;
        setPreferredSize(new Dimension(400, 90));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();
        int trackY = 35, trackH = 10, dotS = 12;
        int dotR = dotS / 2;

        int trackStart = dotR;
        int trackEnd   = w - dotR;
        int trackLen   = trackEnd - trackStart;
        int dotTop     = trackY + (trackH / 2) - dotR;

        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(trackStart, trackY, trackLen, trackH);

        int progressW = (int) (trackLen * fraction);
        g2.setColor(Color.ORANGE);
        g2.fillRect(trackStart, trackY, progressW, trackH);

        for (int i = 0; i < totalDots; i++) {
            int cx = (totalDots == 1)
                    ? trackStart
                    : trackStart + (i * trackLen / (totalDots - 1));
            g2.setColor(i < completedDots ? new Color(0, 180, 80) : Color.RED);
            g2.fillOval(cx - dotR, dotTop, dotS, dotS);
        }

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        int textW = g2.getFontMetrics().stringWidth(text);
        g2.drawString(text, (w - textW) / 2, h - 15);
    }
}
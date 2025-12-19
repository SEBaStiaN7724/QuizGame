import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizConfigScreen extends JFrame {

    private JSpinner questionCount;
    private JSpinner playerCount;
    private JComboBox<String> gameType;
    private JComboBox<String> gameMode;
    private JButton startButton;

    public QuizConfigScreen() {
        setTitle("Konfiguracja Gry Quizowej");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setLayout(new GridLayout(6, 2, 10, 10));
        getContentPane().setBackground(Colors.LIGHT_BLUE);

        // Liczba pytań
        questionCount = new JSpinner(new SpinnerNumberModel(5, 1, 50, 1));
        add(new JLabel("  Liczba Pytań (na gracza):"));
        add(questionCount);

        // Liczba graczy
        playerCount = new JSpinner(new SpinnerNumberModel(1, 1, 4, 1));
        add(new JLabel("  Liczba Graczy:"));
        add(playerCount);

        // Kategoria
        String[] types = {"Ogólny", "Historia", "Nauka", "Sport", "Geografia"};
        gameType = new JComboBox<>(types);
        add(new JLabel("  Kategoria:"));
        add(gameType);

        // Tryb Gry
        String[] modes = {"Klasyczny", "Na Milion"};
        gameMode = new JComboBox<>(modes);
        add(new JLabel("  Tryb Gry:"));
        add(gameMode);
        
        add(new JLabel("")); 
        add(new JLabel(""));

        startButton = new JButton("Rozpocznij Grę");
        add(startButton);
        add(new JLabel("")); 

        startButton.addActionListener(e -> getSettings());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void getSettings() {
        int numQuestions = (int) questionCount.getValue();
        int numPlayers = (int) playerCount.getValue();
        String quizType = (String) gameType.getSelectedItem();
        String selectedMode = (String) this.gameMode.getSelectedItem();

        String[] playerNames;
        // (Tutaj kod pobierania nazw graczy)
        if (numPlayers == 1) {
            String single = JOptionPane.showInputDialog(this, "Wprowadź imię gracza:", "Gracz 1");
            if (single == null || single.trim().isEmpty()) single = "Gracz 1";
            playerNames = new String[]{single};
        } else {
           // wieleosobowa
             JPanel namesPanel = new JPanel(new GridLayout(numPlayers, 2, 5, 5));
            JTextField[] nameFields = new JTextField[numPlayers];
            for (int i = 0; i < numPlayers; i++) {
                namesPanel.add(new JLabel("Imię Gracza " + (i + 1) + ":"));
                nameFields[i] = new JTextField("Gracz " + (i + 1));
                namesPanel.add(nameFields[i]);
            }
            int res = JOptionPane.showConfirmDialog(this, namesPanel, "Imiona graczy", JOptionPane.OK_CANCEL_OPTION);
            if (res != JOptionPane.OK_OPTION) return;

            playerNames = new String[numPlayers];
            for (int i = 0; i < numPlayers; i++) {
                playerNames[i] = nameFields[i].getText();
            }
        }

    this.dispose(); 
    
    //  Wywołanie odpowiedniej klasy gry
    SwingUtilities.invokeLater(() -> {
        if (selectedMode.equals("Na Milion")) {
            // Uruchamiamy klasę dziedziczącą
            new MillionaireGameScreen(playerNames, numQuestions, quizType);
        } else {
            // Uruchamiamy klasę bazową (klasyczną)
            new GameScreen(playerNames, numQuestions, quizType);
        }
    });
}

    public static void main(String[] args) {
        // Uruchomienie okna konfiguracji
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QuizConfigScreen();
            }
        });
    }
}
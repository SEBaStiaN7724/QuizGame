import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameScreen extends JFrame {
    private List<Question> questions;
    private String[] playerNames;
    private int[] playerScores;
    
    // Zmienne stanu gry
    private int currentQuestionIndex = 0;
    private int currentPlayerIndex = 0; // 0 = pierwszy gracz, 1 = drugi...

    // Elementy interfejsu
    private JLabel questionLabel;
    private JLabel statusLabel;
    private JButton[] answerButtons;

    public GameScreen(String[] playerNames, int questionCount, String category) {
        this.playerNames = playerNames;
        this.playerScores = new int[playerNames.length];
        
        // 1. Konfiguracja okna
        setTitle("Quiz Game - Rozgrywka");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Colors.LIGHT_BLUE); // Używamy Twojej klasy Colors

        // 2. Pobranie pytań z bazy
        loadQuestionsFromDB(questionCount, category);

        // Zabezpieczenie przed pustą bazą
        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Brak pytań w bazie!\nUpewnij się, że wykonałeś polecenia INSERT w bazie MySQL.", 
                "Błąd", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // 3. Budowa interfejsu
        initUI();
        
        // 4. Rozpoczęcie pierwszej tury
        displayCurrentState();
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI() {
        // Górny pasek statusu
        statusLabel = new JLabel("Start gry...", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(Colors.DARK_BLUE);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(statusLabel, BorderLayout.NORTH);

        // Środek - Pytanie i przyciski
        JPanel centerPanel = new JPanel(new GridLayout(5, 1, 15, 15)); // 1 wiersz na pytanie, 4 na odp.
        centerPanel.setBackground(Colors.LIGHT_BLUE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        questionLabel = new JLabel("Ładowanie pytania...", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 22));
        centerPanel.add(questionLabel);

        answerButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            answerButtons[i] = new JButton();
            answerButtons[i].setBackground(Colors.BRIGHT_YELLOW);
            answerButtons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            answerButtons[i].setFocusPainted(false);
            
            // Obsługa kliknięcia
            int index = i; 
            answerButtons[i].addActionListener(e -> handleAnswer(index));
            
            centerPanel.add(answerButtons[i]);
        }
        add(centerPanel, BorderLayout.CENTER);
    }

    // --- LOGIKA GRY ---

    private void handleAnswer(int answerIndex) {
        Question currentQ = questions.get(currentQuestionIndex);
        List<Question.Answer> answers = currentQ.getAnswers();

        if (answerIndex < answers.size()) {
            boolean isCorrect = answers.get(answerIndex).isCorrect();
            
            if (isCorrect) {
                playerScores[currentPlayerIndex]++;
                JOptionPane.showMessageDialog(this, "Brawo! Poprawna odpowiedź.");
            } else {
                // Szukamy poprawnej, żeby wyświetlić
                String correctText = "Brak danych";
                for(Question.Answer a : answers) if(a.isCorrect()) correctText = a.getText();
                
                JOptionPane.showMessageDialog(this, "Niestety, źle.\nPoprawna odpowiedź to: " + correctText);
            }
        }

        nextTurn();
    }

    private void nextTurn() {
        currentPlayerIndex++;
        
        // Jeśli przeszliśmy wszystkich graczy dla tego pytania -> nowe pytanie
        if (currentPlayerIndex >= playerNames.length) {
            currentPlayerIndex = 0;
            currentQuestionIndex++;
        }

        // Sprawdzenie końca gry
        if (currentQuestionIndex >= questions.size()) {
            endGame();
        } else {
            displayCurrentState();
        }
    }

    private void displayCurrentState() {
        // Aktualizacja nagłówka
        statusLabel.setText("Tura gracza: " + playerNames[currentPlayerIndex] + 
                            "  |  Pytanie " + (currentQuestionIndex + 1) + "/" + questions.size());

        // Aktualizacja pytania
        Question q = questions.get(currentQuestionIndex);
        questionLabel.setText("<html><center>" + q.getText() + "</center></html>");

        // Aktualizacja przycisków
        List<Question.Answer> ans = q.getAnswers();
        for (int i = 0; i < 4; i++) {
            if (i < ans.size()) {
                answerButtons[i].setText(ans.get(i).getText());
                answerButtons[i].setVisible(true);
            } else {
                answerButtons[i].setVisible(false);
            }
        }
    }

    // --- KONIEC GRY I BAZA DANYCH ---

    private void endGame() {
        StringBuilder sb = new StringBuilder();
        sb.append("KONIEC GRY!\n\nWyniki:\n");
        
        int maxScore = -1;
        for (int score : playerScores) {
            if (score > maxScore) maxScore = score;
        }

        for (int i = 0; i < playerNames.length; i++) {
            sb.append(playerNames[i]).append(": ").append(playerScores[i]).append(" pkt");
            if (playerScores[i] == maxScore) sb.append(" (ZWYCIĘZCA!)");
            sb.append("\n");
        }

        // Zapis do bazy
        saveScoresToDB();

        sb.append("\nWyniki zostały zapisane w bazie danych.");
        
        JOptionPane.showMessageDialog(this, sb.toString());
        dispose(); // Zamknij okno gry
        
        // Opcjonalnie: Powrót do menu
        SwingUtilities.invokeLater(() -> new QuizConfigScreen());
    }

    private void saveScoresToDB() {
        String sql = "INSERT INTO wyniki (imie_gracza, punkty) VALUES (?, ?)";
        
        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            for (int i = 0; i < playerNames.length; i++) {
                ps.setString(1, playerNames[i]);
                ps.setInt(2, playerScores[i]);
                ps.executeUpdate();
            }
            System.out.println("Wyniki zapisane pomyślnie.");
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Błąd zapisu wyników: " + e.getMessage());
        }
    }

    private void loadQuestionsFromDB(int limit, String categoryName) {
        questions = new ArrayList<>();
        
        // Pobieramy ID pytań i treść
        String sqlContent = "SELECT c.content_id, c.content_text FROM content c " +
                            "JOIN category cat ON c.category_id = cat.category_id " +
                            "WHERE cat.category_name LIKE ? " +
                            "ORDER BY RAND() LIMIT ?";
                            
        // Jeśli wybrano "Ogólny", pobieramy wszystko (wildcard %)
        if (categoryName.equals("Ogólny")) categoryName = "%";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlContent)) {
            
            ps.setString(1, categoryName);
            ps.setInt(2, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("content_id");
                String text = rs.getString("content_text");
                
                // Dla każdego pytania pobieramy odpowiedzi
                List<Question.Answer> answers = loadAnswersForQuestion(conn, id);
                questions.add(new Question(text, answers));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Question.Answer> loadAnswersForQuestion(Connection conn, int contentId) throws SQLException {
        List<Question.Answer> list = new ArrayList<>();
        String sql = "SELECT answer_text, is_correct FROM answer WHERE content_id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, contentId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                list.add(new Question.Answer(
                    rs.getString("answer_text"),
                    rs.getBoolean("is_correct")
                ));
            }
        }
        // Mieszamy odpowiedzi, żeby nie zawsze A było poprawne
        Collections.shuffle(list);
        return list;
    }
}
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameScreen extends JFrame {
    protected List<Question> questions;
    protected String[] playerNames;
    protected int[] playerScores;
    
    protected int currentQuestionIndex = 0;
    protected int currentPlayerIndex = 0;

    protected JLabel questionLabel;
    protected JLabel statusLabel;
    protected JButton[] answerButtons;

    public GameScreen(String[] playerNames, int roundsPerPlayer, String category) {
        this.playerNames = playerNames;
        this.playerScores = new int[playerNames.length];
    
        initInitialScores(); 

        setTitle("Quiz Game - " + getGameTitle());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 550);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Colors.LIGHT_BLUE); 

        //  Pobieranie pytań
        int totalQuestionsNeeded = roundsPerPlayer * playerNames.length;
        loadQuestions(totalQuestionsNeeded, category);

        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Brak pytań w bazie!", "Błąd", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        startWindow();
        displayCurrentState();
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    protected void initInitialScores() {
        // W klasycznej grze startujemy od 0
        for(int i=0; i<playerScores.length; i++) playerScores[i] = 0;
    }

    protected String getGameTitle() {
        return "Tryb Klasyczny";
    }

    protected String getScoreUnit() {
        return "pkt";
    }

    protected void onAnswer(boolean isCorrect, Question currentQ) {
        // zwykła logika punktacji
        if (isCorrect) {
            playerScores[currentPlayerIndex]++;
            JOptionPane.showMessageDialog(this, "Poprawna odpowiedź! (+1 pkt)");
        } else {
            String correctTxt = getCorrectAnswerText(currentQ);
            JOptionPane.showMessageDialog(this, "Błąd! Poprawna to: " + correctTxt);
        }
    }

    private void startWindow() {
        statusLabel = new JLabel("Start...", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(Colors.DARK_BLUE);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(statusLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(5, 1, 15, 15));
        centerPanel.setBackground(Colors.LIGHT_BLUE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        centerPanel.add(questionLabel);

        answerButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            answerButtons[i] = new JButton();
            answerButtons[i].setBackground(Colors.BRIGHT_YELLOW);
            answerButtons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            answerButtons[i].setFocusPainted(false);
            
            int index = i; 
            answerButtons[i].addActionListener(e -> checkAnswer(index));
            centerPanel.add(answerButtons[i]);
        }
        add(centerPanel, BorderLayout.CENTER);
    }

    private void checkAnswer(int answerIndex) {
        Question currentQ = questions.get(currentQuestionIndex);
        boolean isCorrect = false;
        
        if (answerIndex < currentQ.getAnswers().size()) {
            isCorrect = currentQ.getAnswers().get(answerIndex).isCorrect();
        }

        onAnswer(isCorrect, currentQ);

        nextTurn();
    }
    
    protected String getCorrectAnswerText(Question q) {
        for(Question.Answer a : q.getAnswers()) {
            if(a.isCorrect()) return a.getText();
        }
        return "Błąd danych";
    }

    private void nextTurn() {
        currentPlayerIndex++;
        if (currentPlayerIndex >= playerNames.length) {
            currentPlayerIndex = 0;
        }
        currentQuestionIndex++;

        if (currentQuestionIndex >= questions.size()) {
            endGame();
        } else {
            displayCurrentState();
        }
    }

    private void displayCurrentState() {
        Question q = questions.get(currentQuestionIndex);
        String gracz = playerNames[currentPlayerIndex].toUpperCase();
        int punkty = playerScores[currentPlayerIndex];
        
        statusLabel.setText("Gracz: " + gracz + "  |  Wynik: " + punkty + " " + getScoreUnit());
        questionLabel.setText("<html><center>" + q.getText() + "</center></html>");

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

    protected void endGame() {
        StringBuilder sb = new StringBuilder();
        sb.append("KONIEC GRY! (" + getGameTitle() + ")\n\n");
        
        int maxScore = -99999;
        for (int s : playerScores) if (s > maxScore) maxScore = s;

        for (int i = 0; i < playerNames.length; i++) {
            sb.append(playerNames[i]).append(": ").append(playerScores[i]).append(" ").append(getScoreUnit());
            if (playerScores[i] == maxScore) sb.append(" 🏆");
            sb.append("\n");
        }

        saveScoresToDB();
        JOptionPane.showMessageDialog(this, sb.toString());
        dispose();
        SwingUtilities.invokeLater(() -> new QuizConfigScreen());
    }

   private void saveScoresToDB() {
        // Zaktualizowane zapytanie SQL - dodajemy kolumnę 'tryb_gry'
        String sql = "INSERT INTO wyniki (imie_gracza, punkty, tryb_gry) VALUES (?, ?, ?)";
        
        try (Connection conn = JDBC.Connection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            for (int i = 0; i < playerNames.length; i++) {
                ps.setString(1, playerNames[i]);
                ps.setInt(2, playerScores[i]);        
                ps.setString(3, getGameTitle());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Błąd zapisu wyników: " + e.getMessage());
        }
    }

    private void loadQuestions(int totalNeeded, String categoryName) {
        questions = new ArrayList<>();
        if (categoryName.equals("Ogólny")) categoryName = "%";

        String sql = "SELECT c.content_id, c.content_text FROM content c " +
                     "JOIN category cat ON c.category_id = cat.category_id " +
                     "WHERE cat.category_name LIKE ? " +
                     "ORDER BY RAND() LIMIT ?";

        try (Connection conn = JDBC.Connection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, categoryName);
            ps.setInt(2, totalNeeded);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("content_id");
                String txt = rs.getString("content_text");
                questions.add(new Question(txt, loadAnswers(conn, id)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Błąd bazy: " + e.getMessage());
        }
    }

    private List<Question.Answer> loadAnswers(Connection conn, int contentId) throws SQLException {
        List<Question.Answer> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT answer_text, is_correct FROM answer WHERE content_id = ?")) {
            ps.setInt(1, contentId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                list.add(new Question.Answer(rs.getString("answer_text"), rs.getBoolean("is_correct")));
            }
        }
        Collections.shuffle(list);
        return list;
    }
}
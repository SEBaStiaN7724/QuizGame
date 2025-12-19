import javax.swing.JOptionPane;

public class MillionaireGameScreen extends GameScreen {

    public MillionaireGameScreen(String[] playerNames, int roundsPerPlayer, String category) {
        super(playerNames, roundsPerPlayer, category);
    }

    // ustawiw tytuł okna na "Na Milion"
    @Override
    protected String getGameTitle() {
        return "Na Milion";
    }

    // zmieniamy jednostkę punktów na "zł"
    @Override
    protected String getScoreUnit() {
        return "zł";
    }

    // ustawiamy początkowe pieniądze dla każdego gracza
    @Override
    protected void initInitialScores() {
        for (int i = 0; i < playerScores.length; i++) {
            playerScores[i] = 1000;
        }
    }

    // nowa logika odpowiedzi
    @Override
    protected void onAnswer(boolean isCorrect, Question currentQ) {
        if (isCorrect) {
            JOptionPane.showMessageDialog(this, "Dobrze! Zachowujesz pieniądze.");
        } else {
            // strata 100 zł
            playerScores[currentPlayerIndex] -= 100;

            String correctTxt = getCorrectAnswerText(currentQ);

            // sprawdzamy czy gracz ma jeszcze pieniądze
            if (playerScores[currentPlayerIndex] <= 0) {
                playerScores[currentPlayerIndex] = 0; 
                
                JOptionPane.showMessageDialog(this, 
                    "Źle! Poprawna odpowiedź to: " + correctTxt + "\n\n" +
                    "BANKRUCTWO! Straciłeś wszystkie pieniądze.\n" +
                    "Koniec Gry.", 
                    "Game Over", 
                    JOptionPane.ERROR_MESSAGE);
                
                // jeśli gracz zbankrutował, kończymy grę
                endGame();
                } else {
                    // gra 
                    JOptionPane.showMessageDialog(this, "Źle! Tracisz 100 zł.\nPoprawna: " + correctTxt);
                }
            }
        }
    }
package com.mycompany.quizgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizConfigScreen extends JFrame {

    // Komponenty GUI (Deklaracja Pól Klasy)
    private JTextField nameField;
    private JSpinner questionCountSpinner;
    private JSpinner playerCountSpinner;
    private JComboBox<String> gameTypeComboBox;
    private JButton startButton;

    public QuizConfigScreen() {
        // 1. Ustawienia głównego okna
        setTitle("Konfiguracja Gry Quizowej");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Użycie pack() jest lepsze, ale ustawiamy stały rozmiar, aby pasował do GridLayout
        setSize(400, 350); 
        // 6 wierszy, 2 kolumny, odstępy 10px
        setLayout(new GridLayout(6, 2, 10, 10)); 
        getContentPane().setBackground(Colors.LIGHT_BLUE);
        // 2. Inicjalizacja komponentów (w konstruktorze)
        
        // Imię/Nazwa Gracza
        add(new JLabel("  Imię/Nazwa Gracza:"));
        nameField = new JTextField("Gracz 1");
        add(nameField);

        // Liczba pytań (Spinner - pole z przyciskami)
        // Wartość początkowa: 10, Min: 5, Max: 50, Krok: 5
        SpinnerModel questionModel = new SpinnerNumberModel(10, 5, 50, 5); 
        questionCountSpinner = new JSpinner(questionModel);
        add(new JLabel("  Liczba Pytań:"));
        add(questionCountSpinner);

        // Liczba graczy
        // Wartość początkowa: 1, Min: 1, Max: 4, Krok: 1
        SpinnerModel playerModel = new SpinnerNumberModel(1, 1, 4, 1); 
        playerCountSpinner = new JSpinner(playerModel);
        add(new JLabel("  Liczba Graczy:"));
        add(playerCountSpinner);

        // Typ Quizu (ComboBox - lista rozwijana)
        String[] types = {"Ogólny", "Historia", "Nauka", "Sport", "Geografia"};
        gameTypeComboBox = new JComboBox<>(types);
        add(new JLabel("  Typ Quizu:"));
        add(gameTypeComboBox);
        
        // Pusta przestrzeń dla lepszego wyglądu
        add(new JLabel("")); 
        add(new JLabel(""));
        
        // Przycisk Start
        startButton = new JButton("Rozpocznij Grę");
        add(startButton);

        // Dodatkowa pusta komórka dla układu 2 kolumn
        add(new JLabel("")); 

        // 3. Dodanie obsługi zdarzeń (akcji przycisku)
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pobierzUstawienia();
            }
        });

        // Wyświetlenie okna na środku ekranu
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Metoda do pobierania i wyświetlania wprowadzonych ustawień.
     */
    private void pobierzUstawienia() {
        // Pobieranie danych z komponentów
        String playerName = nameField.getText();
        int numQuestions = (int) questionCountSpinner.getValue();
        int numPlayers = (int) playerCountSpinner.getValue();
        String quizType = (String) gameTypeComboBox.getSelectedItem();

        // Wyświetlanie informacji
        String info = String.format(
            "--- Ustawienia Quizu ---\n" +
            "Gracz: %s\n" +
            "Liczba Pytań: %d\n" +
            "Liczba Graczy: %d\n" +
            "Typ: %s",
            playerName, numQuestions, numPlayers, quizType
        );
        
        JOptionPane.showMessageDialog(this, info, "Gra się rozpoczyna!", JOptionPane.INFORMATION_MESSAGE);
        
        // Tutaj w przyszłości można wstawić kod uruchamiający okno gry
    }

    public static void main(String[] args) {
        // Uruchomienie GUI w wątku EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QuizConfigScreen();
            }
        });
    }
}
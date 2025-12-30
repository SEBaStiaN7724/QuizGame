package QuizGame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataGenerator {

    public static void stworzPytaniaTestowe() {
        File plik = new File("QuizGame/pytania.txt");
        if (!plik.exists()) {
            try {
                FileWriter pisarz = new FileWriter(plik);
                
                // Geografia
                pisarz.write("Stolica Polski?\nGeografia\nKraków\nWarszawa\nGdańsk\nPoznań\n2\n");
                pisarz.write("Najdłuższa rzeka w Polsce?\nGeografia\nOdra\nWisła\nWarta\nBug\n2\n");

                // Nauka
                pisarz.write("2 + 2 * 2?\nNauka\n6\n8\n4\n10\n1\n");
                pisarz.write("Symbol tlenu?\nNauka\nTl\nO\nH\nOx\n2\n");

                // Historia
                pisarz.write("Pierwszy król Polski?\nHistoria\nMieszko I\nBolesław Chrobry\nKazimierz Wielki\nWładysław Jagiełło\n2\n");
                
                // Sport
                pisarz.write("Ilu piłkarzy w drużynie?\nSport\n10\n11\n12\n6\n2\n");

                pisarz.close();
                System.out.println("Wygenerowano plik pytania.txt przez DataGenerator.");
            } catch (IOException e) {
                System.out.println("Błąd generatora: " + e.getMessage());
            }
        }
    }
}

# 🎮 QuizGame
**QuizGame to aplikacja okienkowa (desktopowa)** napisana w języku Java z wykorzystaniem biblioteki Swing.

Celem projektu jest symulacja gry typu quiz z obsługą wielu graczy, oferująca zróżnicowane tryby rozgrywki oraz system zarządzania wynikami.

Projekt demonstruje praktyczne zastosowanie paradygmatów programowania obiektowego.

## 📋 Główne Funkcjonalności
***Aplikacja umożliwia przeprowadzenie dynamicznej rozgrywki z następującymi funkcjami:***

**Dynamiczna Konfiguracja:** Wybór liczby graczy (do 4 osób), liczby pytań na głowę oraz kategorii tematycznej.

**Zarządzanie Baza Pytań:**  Automatyczne wczytywanie i filtrowanie pytań z pliku tekstowego pytania.txt. 
Pytania są losowane i mieszane co eliminuje szanse na powtarzanie się zestawu pytań tak samo zachowują się pola odpowiedzi.

**System Rankingowy:** Trwałe zapisywanie wyników w pliku wyniki.txt oraz prezentacja tabeli najlepszych wyników (TOP 5) dla każdego trybu.

## 🕹 Tryby Rozgrywki
***Aplikacja oferuje trzy zróżnicowane tryby gry:***

**Klasyczny:**  *Standardowa rywalizacja turowa. Gracze zbierają punkty za poprawne odpowiedzi.*

**Milionerzy:** *Tryb ekonomiczny. Każdy gracz zaczyna z kwotą 1000 zł. Poprawna odpowiedź to +500 zł, błędna to kara -1000 zł. Bankructwo kończy grę gracza.*

**Survival:** *Tryb dla jednego gracza. Gra toczy się do pierwszego błędu. Celem jest przetrwanie jak największej liczby pytań.*

## 🚀 Uruchomienie i Testowanie
**Wymagania:** 
Java JDK 17 lub nowsza.

Plik pytania.txt w głównym katalogu

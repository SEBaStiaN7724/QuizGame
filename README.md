
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

## 📖 Instrukcja Obsługi
**Konfiguracja:** Po uruchomieniu wpisz imię pierwszego gracza, wybierz liczbę uczestników, kategorię pytań oraz tryb gry.

**Start:** Kliknij "Rozpocznij Grę". Jeśli wybrałeś więcej niż 1 gracza, aplikacja poprosi o podanie imion pozostałych osób.

***Rozgrywka:***

*Masz 30 sekund na odpowiedź (zegar zmienia kolor na czerwony przy ostatnich 5 sekundach).*

*W trybie Milionerzy uważaj na stan portfela – ujemny bilans oznacza bankructwo.*

*W trybie Survival każda pomyłka kończy Twoją grę.*

**Koniec:** Po zakończeniu serii pytań wyświetlone zostanie podsumowanie oraz lokalna tabela rekordów pobrana z pliku wyniki.txt.

## 🚀 Uruchomienie i Testowanie
**Wymagania:** 
Java JDK 17 lub nowsza.

**Aby zapewnić poprawną kompilację i odtwarzalność projektu, należy zachować następującą strukturę:**

```
Nazwa_Folderu/
├── pytania.txt           # Plik z bazą pytań (w głównym katalogu)

├── wyniki.txt            # (Generowany automatycznie) Plik z rankingiem

└── src/                  # Katalog ze źródłami

    └── QuizGame/         # Pakiet główny
    
        ├── QuizGame.java # Klasa startowa
        
        ├── Game.java
        
        └── ... (pozostałe klasy)```

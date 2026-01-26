# QuizGame
**QuizGame to aplikacja okienkowa (desktopowa)** napisana w języku Java z wykorzystaniem biblioteki Swing. Celem projektu jest symulacja gry typu quiz z obsługą wielu graczy, oferująca zróżnicowane tryby rozgrywki oraz system zarządzania wynikami. Projekt demonstruje praktyczne zastosowanie paradygmatów programowania obiektowego.

##📋 Opis Funkcjonalności
Aplikacja umożliwia przeprowadzenie rozgrywki wiedzy w systemie turowym. Główne funkcje obejmują:
**Konfiguracja rozgrywki:** Przed startem użytkownik definiuje parametry gry: liczbę graczy, liczbę pytań na osobę, kategorię tematyczną oraz tryb gry.
**System kategorii:** Wybór tematyki pytań realizowany jest w oparciu o bezpieczny typ wyliczeniowy (Enum), co gwarantuje spójność danych.
**Mechanika czasu:** Każde pytanie posiada limit czasowy, odliczany przez niezależny wątek zegara (Timer), co wymusza szybkie podejmowanie decyzji.
**Zapis wyników:** Aplikacja automatycznie archiwizuje osiągnięcia graczy w pliku tekstowym (wyniki.txt) i prezentuje tabelę liderów po zakończeniu partii.
**Walidacja danych:** System weryfikuje dostępność wystarczającej liczby pytań w bazie przed rozpoczęciem gry, zapobiegając błędom w trakcie rozgrywki.

## 🎮 Tryby Gry 
Projekt implementuje polimorfizm poprzez klasę bazową GameScreen i klasy pochodne, oferując trzy unikalne mechaniki:
**Klasyczny:** *Standardowy tryb punktowy. Wygrywa gracz z największą liczbą poprawnych odpowiedzi w zadanej serii pytań.*
**Milionerzy:** *Tryb ekonomiczny. Gracze zdobywają wirtualne środki finansowe. Błędna odpowiedź skutkuje karą pieniężną. Gra kończy się po wyczerpaniu pytań lub bankructwie gracza.*
**Survival:** *Tryb o podwyższonym ryzyku dla jednego gracza. Gra toczy się do momentu popełnienia pierwszego błędu. Celem jest utrzymanie jak najdłuższej serii poprawnych odpowiedzi.*
##🛠 Aspekty Techniczne (Model Obiektowy)

## 🚀 Uruchomienie 
**Wymagane środowisko: Java SE Development Kit (JDK).**

**Skompiluj i uruchom klasę główną:**

Bash
javac QuizGame/*.java
java QuizGame.QuizGame

package QuizGame;

public class KonsolaTest {
    public static void main(String[] args) {
        System.out.println("--- TEST MODELU OBIEKTOWEGO ---");
        
        // 1. Test gracza
        Player p = new Player("Adam");
        p.dodajPunkty(10);
        System.out.println("Gracz: " + p.getImie() + ", Pkt: " + p.getPunkty());

        // 2. Test pytania
        String[] odp = {"1", "2", "3", "4"};
        Question q = new Question("Ile to 2+2?", "Nauka", odp, 3); // Poprawna to "4"
        
        System.out.println("Pytanie: " + q.getTresc());
        System.out.println("Czy '4' jest pod poprawnym indeksem? " + 
                           q.getOdpowiedzi()[q.getPoprawnyIndeks()].equals("4"));
    }
}
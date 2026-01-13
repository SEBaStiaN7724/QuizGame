package QuizGame;

public enum Category {
    OGOLNY("Ogólny"),
    HISTORIA("Historia"),
    NAUKA("Nauka"),
    SPORT("Sport"),
    GEOGRAFIA("Geografia");

    private final String nazwa;

    Category(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNazwa() {
        return nazwa;
    }

    @Override
    public String toString() {
        return nazwa;
    }
}
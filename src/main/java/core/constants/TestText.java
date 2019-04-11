package core.constants;

public enum TestText {
    MOTHER("mother", "mottherr"),
    BROTHER("brother", "bbrother"),
    UK_WORD("питання", "питаня"),
    RU_WORD("сметана", "смитана"),
    RU_WRONG_CAPITAL("Санкт-Петербург", "санкт-петербург"),
    EN_WITH_DIGITS("father", "122father");

    private String corrVer;
    private String wrongVer;

    TestText(String corrVer, String wrongVer) {
        this.corrVer = corrVer;
        this.wrongVer = wrongVer;
    }

    public String corrVer() {
        return corrVer;
    }

    public String wrongVer() {
        return wrongVer;
    }

    public static Object[][] getTextsWithCorrectionsSmokeTestData() {
        return new Object[][]{
                {"карова", "корова"},
                {"малако", "молоко"},
                {"облоко", "облако"},
                {"перескоп", "перископ"},
                {"пречина", "причина"},
                {"колобог", "колобок"},
                {"машына", "машина"},
                {"побдорка", "подборка"},
                {"працэнт", "процент"},
                {"абажюр", "абажур"}
        };
    }
}
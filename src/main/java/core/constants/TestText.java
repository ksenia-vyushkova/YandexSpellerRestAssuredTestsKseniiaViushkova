package core.constants;

public enum TestText {
    MOTHER("mother", "mottherr"),
    BROTHER("brother", "bbrother"),
    UK_WORD("питання", "питаня"),
    RU_WORD("сметана", "смитана"),
    RU_WRONG_CAPITAL("Санкт-Петербург", "санкт-петербург"),
    EN_WITH_DIGITS("father", "122father"),
    KOROVA("корова", "карова"),
    MOLOKO("молоко", "малако"),
    OBLAKO("облако", "облоко"),
    PERISKOP("перископ", "перескоп"),
    PRICHINA("причина", "пречина"),
    KOLOBOK("колобок", "колобог"),
    MASHINA("машина", "машына"),
    PODBORKA("подборка", "побдорка"),
    PROTSENT("процент", "працэнт"),
    ABAJUR("абажур", "абажюр"),
    OPECHATKA("опечатка", "опечтка"),
    HTML_SENTENCE("Корова<span class=\"опечтка\">пьет</span>молоко", "Карова<span class=\"опечтка\">пъет</span>малако");

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
                {KOROVA},
                {MOLOKO},
                {OBLAKO},
                {PERISKOP},
                {PRICHINA},
                {KOLOBOK},
                {MASHINA},
                {PODBORKA},
                {PROTSENT},
                {ABAJUR}
        };
    }
}
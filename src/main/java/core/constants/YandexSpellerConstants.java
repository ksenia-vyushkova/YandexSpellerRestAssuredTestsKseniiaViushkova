package core.constants;

public class YandexSpellerConstants {

    //useful constants for API under test
    public static final String PARAM_TEXT = "text";
    public static final String PARAM_OPTIONS = "options";
    public static final String PARAM_LANG = "lang";
    public static final String PARAM_FORMAT = "format";
    public static final String QUOTES = "\"";


    public enum Language {
        RU("ru"),
        UK("uk"),
        EN("en");
        public String languageCode;

        Language(String lang) {
            this.languageCode = lang;
        }
    }

    public enum TextFormat {
        PLAIN("plain"),
        HTML("html");

        public String type;

        TextFormat(String type) {
            this.type = type;
        }
    }

    public enum ErrorCode {
        ERROR_UNKNOWN_WORD("1"),
        ERROR_REPEAT_WORD("2"),
        ERROR_CAPITALIZATION("3"),
        ERROR_TOO_MANY_ERRORS("4");

        private String code;

        ErrorCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public enum CorrectTexts {
        RUSSIAN("Этот текст на русском языке"),
        URL_TEXT("This text contains URL http://yandex.ru");
        private String text;

        CorrectTexts(String text) {
            this.text = text;
        }

        public String text() {
            return text;
        }
    }
}
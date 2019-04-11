import beans.YandexSpellerAnswer;
import core.YandexSpellerCheckTextApi;
import core.constants.TestText;
import core.constants.YandexSpellerConstants;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

@RunWith(Enclosed.class)
public class TestYandexSpellerHomeworkOf10Tests {

    @RunWith(Parameterized.class)
    public static class ParameterizedTests {

        private String text;
        private String expectedCorrection;

        public ParameterizedTests(String text, String expectedCorrection) {
            this.text = text;
            this.expectedCorrection = expectedCorrection;
        }

        @Parameterized.Parameters
        public static Object[][] data() {
            return TestText.getTextsWithCorrectionsSmokeTestData();
        }

        @Test
        public void textsWithCorrectionsSmokeTest() {
            List<YandexSpellerAnswer> answers =
                    YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                            YandexSpellerCheckTextApi.with().text(text).callApi());
            assertThat(answers.get(0).word, equalTo(text));
            assertThat(answers.get(0).s.get(0), equalTo(expectedCorrection));
        }
    }

    public static class NotParameterizedTests {
        @Test
        public void textFormatTest() {
            List<YandexSpellerAnswer> answers =
                    YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                            YandexSpellerCheckTextApi.with()
                                    .textFormat(YandexSpellerConstants.TextFormat.HTML)
                                    .text("Карова" +
                                            "&lt;span class=\"опечтка\"&gt;" +
                                            "пъет" +
                                            "&lt;/span&gt;" +
                                            "малако")
                                    .callApi());
            assertThat(answers.get(1).word, not("опечтка"));
        }
    }
}
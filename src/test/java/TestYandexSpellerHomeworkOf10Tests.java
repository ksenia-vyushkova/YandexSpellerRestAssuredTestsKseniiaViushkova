import beans.YandexSpellerAnswer;
import core.YandexSpellerCheckTextApi;
import core.constants.TestText;
import core.constants.YandexSpellerConstants;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static core.constants.TestText.HTML_SENTENCE;
import static core.matchers.ContainsCorrection.containsCorrection;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

@RunWith(Enclosed.class)
public class TestYandexSpellerHomeworkOf10Tests {

    @RunWith(Parameterized.class)
    public static class ParameterizedTests {

        private TestText testText;

        public ParameterizedTests(TestText testText) {
            this.testText = testText;
        }

        @Parameterized.Parameters
        public static Object[][] data() {
            return TestText.getTextsWithCorrectionsSmokeTestData();
        }

        @Test
        public void textsWithCorrectionsSmokeTest() {
            List<YandexSpellerAnswer> answers =
                    YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                            YandexSpellerCheckTextApi.with().text(testText.wrongVer()).callApi());
            assertThat(answers, containsCorrection(testText));
        }
    }

    public static class NotParameterizedTests {
        @Test
        public void textFormatTest() {
            List<YandexSpellerAnswer> answers =
                    YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                            YandexSpellerCheckTextApi.with()
                                    .textFormat(YandexSpellerConstants.TextFormat.HTML)
                                    .text(HTML_SENTENCE.wrongVer())
                                    .callApi());
            assertThat(answers, not(containsCorrection(TestText.OPECHATKA)));
        }
    }
}
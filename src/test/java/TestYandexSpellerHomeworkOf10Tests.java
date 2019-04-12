import beans.YandexSpellerAnswer;
import core.YandexSpellerCheckTextApi;
import core.constants.TestText;
import core.constants.YandexSpellerConstants;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static core.constants.TestText.*;
import static core.matchers.ContainsCorrection.containsCorrection;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(Enclosed.class)
public class TestYandexSpellerHomeworkOf10Tests {

    private static int MAX_POS = 10000;

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
            assertThat("expected correction not present in results", answers, containsCorrection(testText));
        }

        @Test
        public void textsWithoutCorrectionsSmokeTest() {
            List<YandexSpellerAnswer> answers =
                    YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                            YandexSpellerCheckTextApi.with().text(testText.corrVer()).callApi());
            assertThat("expected number of answers is wrong", answers.size(), equalTo(0));
        }
    }

    public static class NotParameterizedTests {

        @Test
        public void defaultLanguageTest() {
            SoftAssertions softAssertions = new SoftAssertions();
            //1. Check ru word returns corrections
            List<YandexSpellerAnswer> answers =
                    YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                            YandexSpellerCheckTextApi.with().text(RU_WORD.wrongVer()).callApi());
            softAssertions.assertThat(answers.get(0).s).contains(RU_WORD.corrVer());

            //2. Check ru word returns corrections
            answers = YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                    YandexSpellerCheckTextApi.with().text(EN_WORD.wrongVer()).callApi());
            softAssertions.assertThat(answers.get(0).s).contains(EN_WORD.corrVer());

            //3. Check uk word does not return corrections
            answers = YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                    YandexSpellerCheckTextApi.with().text(UK_WORD.wrongVer()).callApi());
            softAssertions.assertThat(answers.size()).isEqualTo(0);

            //4. Check fr word does not return corrections
            answers = YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                    YandexSpellerCheckTextApi.with().text(FR_WORD.wrongVer()).callApi());
            softAssertions.assertThat(answers.size()).isEqualTo(0);

            softAssertions.assertAll();
        }

        @Test
        public void incorrectEncodingTest() {
            List<YandexSpellerAnswer> answers =
                    YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                            YandexSpellerCheckTextApi.with().text(INCORRECT_ENCODING.wrongVer()).callApi());
            assertThat("results not empty", answers.size(), equalTo(0));
        }

        @Test
        public void textFormatTest() {
            List<YandexSpellerAnswer> answers =
                    YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                            YandexSpellerCheckTextApi.with()
                                    .textFormat(YandexSpellerConstants.TextFormat.HTML)
                                    .text(HTML_SENTENCE.wrongVer())
                                    .callApi());
            assertThat("html markup not ignored", answers, not(containsCorrection(TestText.OPECHATKA)));
        }

        @Test
        public void restrictionsTest() {
            List<YandexSpellerAnswer> answers =
                    YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                            YandexSpellerCheckTextApi.with().text(LARGE_TEXT.wrongVer()).callApiWithPOST());
            answers.forEach(answer -> assertThat(answer.pos, lessThan(MAX_POS)));
        }
    }
}
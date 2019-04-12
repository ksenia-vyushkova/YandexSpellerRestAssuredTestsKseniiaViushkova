import beans.YandexSpellerAnswer;
import core.YandexSpellerCheckTextApi;
import core.assertj.CustomSoftAssertions;
import core.constants.TestText;
import core.constants.YandexSpellerConstants;
import core.model.YandexSpellerAnswers;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static core.constants.Options.FIND_REPEAT_WORDS;
import static core.constants.Options.computeOptions;
import static core.constants.TestText.*;
import static core.matchers.ContainsCorrection.containsCorrection;
import static core.matchers.ContainsError.containsError;
import static core.matchers.PosLessThen.posLessThen;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;

@RunWith(Enclosed.class)
public class TestYandexSpellerHomeworkOf10Tests {

    private static int MAX_POS = 10000;
    private static long MAX_TIME = 5000L;

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
            assertThat("expected number of answers is wrong", answers.isEmpty());
        }
    }

    public static class NotParameterizedTests {

        @Test
        public void defaultLanguageTest() {
            CustomSoftAssertions softAssertions = new CustomSoftAssertions();
            //1. Check ru word returns corrections
            YandexSpellerAnswers answers = new YandexSpellerAnswers(
                    YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                            YandexSpellerCheckTextApi.with().text(RU_WORD.wrongVer()).callApi()));
            softAssertions.assertThat(answers).containsCorrection(RU_WORD);

            //2. Check ru word returns corrections
            answers = new YandexSpellerAnswers(
                    YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                            YandexSpellerCheckTextApi.with().text(EN_WORD.wrongVer()).callApi()));
            softAssertions.assertThat(answers).containsCorrection(EN_WORD);

            //3. Check uk word does not return corrections
            answers = new YandexSpellerAnswers(
                    YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                            YandexSpellerCheckTextApi.with().text(UK_WORD.wrongVer()).callApi()));
            softAssertions.assertThat(answers).isResultsEmpty();

            //4. Check fr word does not return corrections
            answers = new YandexSpellerAnswers(
                    YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                            YandexSpellerCheckTextApi.with().text(FR_WORD.wrongVer()).callApi()));
            softAssertions.assertThat(answers).isResultsEmpty();

            softAssertions.assertAll();
        }

        @Test
        public void incorrectEncodingTest() {
            List<YandexSpellerAnswer> answers =
                    YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                            YandexSpellerCheckTextApi.with().text(INCORRECT_ENCODING.wrongVer()).callApi());
            assertThat("unexpected results for incorrect encoding", answers.isEmpty());
        }

        @Test
        public void unknownWordTest() {
            List<YandexSpellerAnswer> answers =
                    YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                            YandexSpellerCheckTextApi.with().text(QWERTY.wrongVer()).callApi());
            assertThat("error ERROR_UNKNOWN_WORD expected, but not present", answers, containsError(YandexSpellerConstants.ErrorCode.ERROR_UNKNOWN_WORD));
        }

        @Test
        public void findRepeatWordTest() {
            List<YandexSpellerAnswer> answers =
                    YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                            YandexSpellerCheckTextApi.with().text(REPEAT_TEXT.wrongVer()).options(computeOptions(FIND_REPEAT_WORDS)).callApi());
            assertThat("error ERROR_REPEAT_WORD expected, but not present", answers, containsError(YandexSpellerConstants.ErrorCode.ERROR_REPEAT_WORD));
        }

        @Test
        public void capitalizationTest() {
            List<YandexSpellerAnswer> answers =
                    YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                            YandexSpellerCheckTextApi.with().text(INCORRECT_CAPITALIZATION.wrongVer()).callApi());
            assertThat("error ERROR_CAPITALIZATION expected, but not present", answers, containsError(YandexSpellerConstants.ErrorCode.ERROR_CAPITALIZATION));
        }

        @Test
        public void textFormatTest() {
            List<YandexSpellerAnswer> answers =
                    YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                            YandexSpellerCheckTextApi.with()
                                    .textFormat(YandexSpellerConstants.TextFormat.HTML)
                                    .text(HTML_SENTENCE.wrongVer())
                                    .callApi());
            assertThat("html markup not ignored when using format=html", answers, not(containsCorrection(TestText.OPECHATKA)));
        }

        @Test
        public void restrictionsTest() {
            List<YandexSpellerAnswer> answers = YandexSpellerCheckTextApi.getYandexSpellerAnswers(
                    YandexSpellerCheckTextApi.with().text(LARGE_TEXT.wrongVer()).callApiWithPOST());
            assertThat("word position is higher than expected", answers, posLessThen(MAX_POS));
        }

        @Test
        public void slaTest() {
            YandexSpellerCheckTextApi.with()
                    .text(LARGE_TEXT.wrongVer())
                    .callApiWithPOST()
                    .then()
                    .time(lessThan(MAX_TIME));
        }
    }
}
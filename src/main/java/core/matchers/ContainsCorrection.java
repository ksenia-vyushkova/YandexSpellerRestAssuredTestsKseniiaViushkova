package core.matchers;

import beans.YandexSpellerAnswer;
import core.constants.TestText;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;

public class ContainsCorrection extends TypeSafeMatcher<List<YandexSpellerAnswer>> {

    private TestText testText;

    private ContainsCorrection(TestText testText) {
        this.testText = testText;
    }

    public static Matcher<List<YandexSpellerAnswer>> containsCorrection(TestText testText) {
        return new ContainsCorrection(testText);
    }

    @Override
    public boolean matchesSafely(List<YandexSpellerAnswer> answers) {
        return isContainingWord(answers) && isContainingCorrectionForWord(answers);
    }

    public void describeTo(Description description) {
        description.appendText("corrections list contains <" + testText.corrVer() + ">");
    }

    private boolean isContainingWord(List<YandexSpellerAnswer> answers) {
        return answers.stream().anyMatch(answer -> answer.word.contains(testText.wrongVer()));
    }

    private boolean isContainingCorrectionForWord(List<YandexSpellerAnswer> answers) {
        return answers.stream()
                .filter(answer -> answer.word.contains(testText.wrongVer()))
                .allMatch(answer -> answer.s.contains(testText.corrVer()));
    }
}
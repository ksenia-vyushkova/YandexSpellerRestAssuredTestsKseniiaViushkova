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
        return answers.stream().anyMatch(answer -> answer.s.contains(testText.corrVer()));
    }

    public void describeTo(Description description) {
        description.appendText("corrections list contains <" + testText.corrVer() + ">");
    }
}
package core.matchers;

import beans.YandexSpellerAnswer;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;

public class PosLessThen extends TypeSafeMatcher<List<YandexSpellerAnswer>> {

    private int maxPos;

    private PosLessThen(int maxPos) {
        this.maxPos = maxPos;
    }

    public static Matcher<List<YandexSpellerAnswer>> posLessThen(int maxPos) {
        return new PosLessThen(maxPos);
    }

    @Override
    public boolean matchesSafely(List<YandexSpellerAnswer> answers) {
        return answers.stream().allMatch(answer -> answer.pos < maxPos);
    }

    public void describeTo(Description description) {
        description.appendText("all word positions are less then <" + maxPos + ">");
    }
}
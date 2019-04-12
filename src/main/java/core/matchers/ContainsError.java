package core.matchers;

import beans.YandexSpellerAnswer;
import core.constants.YandexSpellerConstants;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;

public class ContainsError extends TypeSafeMatcher<List<YandexSpellerAnswer>> {

    private YandexSpellerConstants.ErrorCode errorCode;

    private ContainsError(YandexSpellerConstants.ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public static Matcher<List<YandexSpellerAnswer>> containsError(YandexSpellerConstants.ErrorCode errorCode) {
        return new ContainsError(errorCode);
    }

    @Override
    public boolean matchesSafely(List<YandexSpellerAnswer> answers) {
        return answers.stream().anyMatch(answer -> answer.code.equals(Integer.parseInt(errorCode.getCode())));
    }

    public void describeTo(Description description) {
        description.appendText("contails error code <" + errorCode.getCode() + ">");
    }
}
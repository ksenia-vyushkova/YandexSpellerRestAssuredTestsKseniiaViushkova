package core.assertj;

import core.constants.TestText;
import core.model.YandexSpellerAnswers;
import org.assertj.core.api.AbstractAssert;

public class ContainsCorrectionAssert extends AbstractAssert<ContainsCorrectionAssert, YandexSpellerAnswers> {

    public ContainsCorrectionAssert(YandexSpellerAnswers actual) {
        super(actual, ContainsCorrectionAssert.class);
    }

    public static ContainsCorrectionAssert assertThat(YandexSpellerAnswers actual) {
        return new ContainsCorrectionAssert(actual);
    }

    public ContainsCorrectionAssert containsCorrection(TestText testText) {
        if (!(isContainingWord(testText) && isContainingCorrectionForWord(testText))) {
            failWithMessage("expected correction <" + testText.corrVer() + "> not present in results");
        }
        return this;
    }

    public ContainsCorrectionAssert isResultsEmpty() {
        if (!actual.getAnswers().isEmpty()) {
            failWithMessage("expected empty results, but instead unexpected results found");
        }
        return this;
    }

    private boolean isContainingWord(TestText testText) {
        return actual.getAnswers().stream().anyMatch(answer -> answer.word.contains(testText.wrongVer()));
    }

    private boolean isContainingCorrectionForWord(TestText testText) {
        return actual.getAnswers().stream()
                .filter(answer -> answer.word.contains(testText.wrongVer()))
                .allMatch(answer -> answer.s.contains(testText.corrVer()));
    }
}
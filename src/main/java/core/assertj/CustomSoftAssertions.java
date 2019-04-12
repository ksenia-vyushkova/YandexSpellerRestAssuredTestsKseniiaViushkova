package core.assertj;

import core.model.YandexSpellerAnswers;
import org.assertj.core.api.SoftAssertions;

public class CustomSoftAssertions extends SoftAssertions {

    public ContainsCorrectionAssert assertThat(YandexSpellerAnswers actual) {
        return proxy(ContainsCorrectionAssert.class, YandexSpellerAnswers.class, actual);
    }
}
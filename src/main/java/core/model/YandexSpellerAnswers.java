package core.model;

import beans.YandexSpellerAnswer;

import java.util.List;

public class YandexSpellerAnswers {
    private List<YandexSpellerAnswer> answers;

    public YandexSpellerAnswers(List<YandexSpellerAnswer> answers) {
        this.answers = answers;
    }

    public List<YandexSpellerAnswer> getAnswers() {
        return answers;
    }
}
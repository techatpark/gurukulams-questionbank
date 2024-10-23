package com.gurukulams.questionbank.service;

import com.gurukulams.questionbank.model.QuestionChoice;
import com.gurukulams.questionbank.payload.Question;
import com.gurukulams.questionbank.payload.QuestionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

class MatchTheFollowingTest extends ChoseTheBestTest {

    @Override
    void testUpdate(final Question questionToUpdate,final Locale locale) throws SQLException {

        final String updatedQuestionTxt = "Updated at " + System.currentTimeMillis();

        questionToUpdate.withQuestion(updatedQuestionTxt);

        this.questionService.update(questionToUpdate.getType(),
                questionToUpdate.id(),locale, questionToUpdate);

        Assertions.assertEquals(updatedQuestionTxt,
                this.questionService.read(questionToUpdate.id(),locale)
                        .get().getQuestion());

        QuestionChoice questionChoice = questionToUpdate.getChoices().get(0);

        questionChoice.withCValue(updatedQuestionTxt);

        this.questionService.update(questionToUpdate.getType(),
                questionToUpdate.id(),locale, questionToUpdate);

        Assertions.assertEquals(updatedQuestionTxt,
                this.questionService.read(questionToUpdate.id(),locale).get()
                        .getChoices().stream()
                        .filter(questionChoice1 -> questionChoice1.id().equals(questionChoice.id()))
                        .findFirst().get().cValue());

        int existingQuestions = questionToUpdate.getChoices().size();

        String cValue = UUID.randomUUID().toString();
        QuestionChoice choice = new QuestionChoice(null,
                null,
                cValue,
                Boolean.FALSE
                );
        questionToUpdate.getChoices().add(choice);

        choice = new QuestionChoice(null,
                null,
                cValue,
                Boolean.FALSE
        );
        questionToUpdate.getMatches().add(choice);

        this.questionService.update(questionToUpdate.getType(),
                questionToUpdate.id(),locale, questionToUpdate);

        QuestionChoice choiceReturned = this.questionService.read(questionToUpdate.id(),locale).get()
                .getChoices().stream()
                .filter(questionChoice1 -> questionChoice1.cValue().equals(cValue))
                .findFirst().get();

        Assertions.assertTrue(
                choiceReturned.cValue().equals(cValue));

        questionToUpdate.withChoices(this.questionService.read(questionToUpdate.id(),locale)
                .get()
                .getChoices().stream()
                .filter(questionChoice1 -> !questionChoice1.cValue().equals(cValue)).toList());

        this.questionService.update(questionToUpdate.getType(),
                questionToUpdate.id(),locale, questionToUpdate);

        Assertions.assertEquals(existingQuestions,
                this.questionService.read(questionToUpdate.id(),locale).get()
                        .getChoices().size());
    }

    @Override
    String getCorrectAnswer(Question question) {
        List<QuestionChoice> questionChoices = new ArrayList<>(question.getChoices());
        questionChoices
                .addAll(question.getMatches().subList(0, question.getChoices().size()));
        return questionChoices.stream()
                .map(choice -> choice.id().toString())
                .collect(Collectors.joining(","));
    }

    @Override
    List<String> getWrongAnswers(final Question question) {
        List<String> wrongAnswers = super.getWrongAnswers(question);

        List<QuestionChoice> answerChoices = new ArrayList<>(question.getChoices());

        List<QuestionChoice> matches = question.getMatches().subList(0, question.getChoices().size());
        Collections.swap(matches, 0, 1);
        answerChoices
                .addAll(matches);

        wrongAnswers.add(answerChoices.stream()
                .map(choice -> choice.id().toString())
                .collect(Collectors.joining(","))) ;

        return wrongAnswers;
    }

    @Override
    List<Question> getInvalidQuestions() {

        List<Question> invalidQuestions = new ArrayList<>();

        Question question = getTestQuestion();
        //null matches
        question.withMatches(null);
        invalidQuestions.add(question);
        return invalidQuestions;
    }

    @Override
    Question getTestQuestion() {
        Question question = super.getTestQuestion();
        question.withType(QuestionType.MATCH_THE_FOLLOWING);

        question.withQuestion("Match the Following");

        question.getChoices()
                .forEach(questionChoice ->
                        questionChoice.withIsAnswer(false));

        question.withMatches(getMatches());

        return question;
    }

    private List<QuestionChoice> getMatches() {
        List<QuestionChoice> matches = new ArrayList<>();

        QuestionChoice choice = new QuestionChoice(null,
                null,
                "Object Oriented",
                null
                );
        matches.add(choice);

        choice = new QuestionChoice(null,
                null,
                "System Language",
                null);
        matches.add(choice);

        choice = new QuestionChoice(null,
                null,
                "Regional Language",
                null);
        matches.add(choice);

        choice = new QuestionChoice(null,
                null,
                "Universal Language",
                null);
        matches.add(choice);

        return matches;
    }


}
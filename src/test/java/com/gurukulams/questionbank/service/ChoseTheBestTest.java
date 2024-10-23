package com.gurukulams.questionbank.service;

import com.gurukulams.questionbank.model.QuestionChoice;
import com.gurukulams.questionbank.payload.Question;
import com.gurukulams.questionbank.payload.QuestionType;

import org.junit.jupiter.api.Assertions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

class ChoseTheBestTest extends QuestionServiceTest {

    protected static final String C_LANGUAGE = "C";

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
        return question.getChoices().stream()
                .filter(QuestionChoice::isAnswer)
                .findFirst()
                .get()
                .id().toString();
    }

    @Override
    List<String> getWrongAnswers(Question question) {
        List<String> answers = new ArrayList<>();
        answers.add(UUID.randomUUID().toString());
        return answers;
    }

    @Override
    Question getTestQuestion() {
        Question question = new Question();
        question.withType(QuestionType.CHOOSE_THE_BEST);

        question.withQuestion("Which one of the folloing is a Object Oriented Language?");
        question.withExplanation("Language that suppors class and objects");

        question.withChoices(new ArrayList<>());

        QuestionChoice choice = new QuestionChoice(null,
                null,
                "Java",
                true
                );
        question.getChoices().add(choice);

        choice = new QuestionChoice(null,
                null,
                C_LANGUAGE,
                null);
        question.getChoices().add(choice);

        choice = new QuestionChoice(null,
                null,
                "Tamil",
                null);
        question.getChoices().add(choice);

        choice = new QuestionChoice(null,
                null,
                "English",
                null);
        question.getChoices().add(choice);

        return question;
    }

    @Override
    List<Question> getInvalidQuestions() {

        List<Question> invalidQuestions  = new ArrayList<>();

        Question question = getTestQuestion();

        // Question without Answer
        question.getChoices().forEach(questionChoice -> questionChoice.withIsAnswer(false));

        invalidQuestions.add(question);


        question = getTestQuestion();
        question.getChoices().remove(0);
        question.getChoices().remove(0);
        question.getChoices().remove(0);

        // Question with Only One Choice
        invalidQuestions.add(question);

        question = getTestQuestion();


        question.withChoices(null);

        // Question with Null Choice
        invalidQuestions.add(question);

        return invalidQuestions;
    }
}
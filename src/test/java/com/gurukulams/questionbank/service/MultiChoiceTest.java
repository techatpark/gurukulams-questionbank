package com.gurukulams.questionbank.service;

import com.gurukulams.questionbank.model.QuestionChoice;
import com.gurukulams.questionbank.payload.Question;
import com.gurukulams.questionbank.payload.QuestionType;

import java.util.stream.Collectors;

public class MultiChoiceTest extends ChoseTheBestTest {
    @Override
    String getCorrectAnswer(Question question) {
        return question.getChoices().stream()
                .filter(QuestionChoice::isAnswer)
                .map(choice -> choice.id().toString())
                .collect(Collectors.joining(","));
    }

    @Override
    Question getTestQuestion() {
        Question question = super.getTestQuestion();
        question.withType(QuestionType.MULTI_CHOICE);

        question.withQuestion("Which of the following are programing Languages?");

        question.getChoices().stream()
                .filter(questionChoice ->
                        questionChoice.cValue().equals(C_LANGUAGE))
                .findFirst()
                .get().withIsAnswer(true);

        return question;
    }
}

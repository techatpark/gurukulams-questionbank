package com.gurukulams.questionbank.service;

import com.gurukulams.questionbank.model.QuestionChoice;
import com.gurukulams.questionbank.payload.Question;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Answer service.
 */

public class AnswerService {

    /**
     * question Service.
     */
    private final QuestionService questionService;

    /**
     * Constructs Answer Service.
     *
     * @param anQuestionService the an question service
     */
    public AnswerService(final QuestionService anQuestionService) {
        this.questionService = anQuestionService;
    }

    /**
     * checks whether the given answer is correct.returns true if correct.
     *
     * @param questionId the question id
     * @param answer     the answer
     * @return true boolean
     */
    public final boolean answer(final UUID questionId,
                                final String answer)
            throws SQLException {
        boolean isRigntAnswer = false;
        final Optional<Question> oQuestion = questionService
                .read(questionId, null);
        if (oQuestion.isPresent()) {
            final Question question = oQuestion.get();
            switch (question.getType()) {
                case CHOOSE_THE_BEST:
                    Optional<QuestionChoice> rightChoice = question.getChoices()
                            .stream()
                            .filter(QuestionChoice::isAnswer)
                            .findFirst();
                    if (rightChoice.isPresent()) {
                        isRigntAnswer = rightChoice.get()
                                .id()
                                .toString()
                                .equals(answer);
                    }
                    break;
                case MULTI_CHOICE:
                    List<String> rightChoiceIds = question.getChoices()
                            .stream()
                            .filter(QuestionChoice::isAnswer)
                            .map(choice -> choice.id().toString())
                            .toList();
                    if (!rightChoiceIds.isEmpty()) {
                        Set<String> answerIds = Set.of(answer.split(","));
                        isRigntAnswer =
                                answerIds.size() == rightChoiceIds.size()
                                && answerIds.containsAll(rightChoiceIds);
                    }
                    break;
                case MATCH_THE_FOLLOWING:
                    List<QuestionChoice> questionChoices =
                            new ArrayList<>(question.getChoices());
                    questionChoices
                            .addAll(question.getMatches()
                                    .subList(0, question.getChoices().size()));

                    if (!questionChoices.isEmpty()) {
                        isRigntAnswer = answer.equals(questionChoices.stream()
                                .map(choice ->
                                        choice.id().toString())
                                .collect(Collectors.joining(",")));
                    }
                    break;
                default:
                    break;
            }
        }
        return isRigntAnswer;
    }

}

package com.gurukulams.questionbank.payload;

import com.gurukulams.questionbank.model.QuestionChoice;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The type Question.
 */
public class Question {
    /**
     * tells the id of question.
     */
    private UUID id;

    /**
     * this is the question.
     */
    @NotBlank(message = "question is mandatory")
    private String question;

    /**
     * this is the explanation.
     */
    private String explanation;

    /**
     * tells the answer.
     */
    private String answer;

    /**
     * created_by of question.
     */
    private String createdBy;

    /**
     * created_at of question.
     */
    private LocalDateTime createdAt;


    /**
     * updated_at of question.
     */
    private LocalDateTime updatedAt;

    /**
     * tells the type of question being created.
     */
    private QuestionType type;

    /**
     * tells the question choices available.
     */
    private List<QuestionChoice> choices;
    /**
     *
     * @return match
     */
    public List<QuestionChoice> getMatches() {
        return matches;
    }
    /**
     *
     * @param matched
     */
    public final void withMatches(final List<QuestionChoice> matched) {
        this.matches = matched;
    }
    /**
     * tells the match the following available.
     */
    private List<QuestionChoice> matches;

    /**
     * gets the type of question.
     *
     * @return type
     */
    public QuestionType getType() {
        return type;
    }

    /***
     * withs the type of question.
     *
     * @param aType the type
     */
    public void withType(final QuestionType aType) {
        this.type = aType;
    }

    /**
     * gets the id of question.
     *
     * @return id id
     */
    public UUID id() {
        return id;
    }

    /**
     * withs the id of question.
     *
     * @param anId the id
     */
    public void withId(final UUID anId) {
        this.id = anId;
    }

    /**
     * gets the question.
     *
     * @return question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * withs the question.
     *
     * @param anQuestion the question
     */
    public void withQuestion(final String anQuestion) {
        this.question = anQuestion;
    }

    /**
     * Gets Explanation.
     *
     * @return explanation
     */
    public String getExplanation() {
        return explanation;
    }

    /**
     * Sets Explanation.
     *
     * @param anExplanation
     */
    public void withExplanation(final String anExplanation) {
        this.explanation = anExplanation;
    }

    /**
     * gets the answer.
     *
     * @return answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * withs the answer.
     *
     * @param anAnswer the answer
     */
    public void withAnswer(final String anAnswer) {
        this.answer = anAnswer;
    }

    /**
     * Gets question choice.
     *
     * @return the question choice
     */
    public List<QuestionChoice> getChoices() {
        return choices;
    }

    /**
     * Sets question choice.
     *
     * @param theChoice the question choice
     */
    public void withChoices(final List<QuestionChoice> theChoice) {
        this.choices = theChoice;
    }

    /**
     * created_by of the question.
     *
     * @return created_by created_by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets created_by of the question.
     *
     * @param theOwner the created_by
     */
    public void withCreatedBy(final String theOwner) {
        this.createdBy = theOwner;
    }

    /**
     * withs created at.
     *
     * @return createdAt
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * gets created At.
     *
     * @param aCreatedAt the created at
     */
    public void withCreatedAt(final LocalDateTime aCreatedAt) {
        this.createdAt = aCreatedAt;
    }

    /**
     * gets updated at.
     *
     * @return updatedAt
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets updated at.
     *
     * @param aupdatedAt
     */
    public void withUpdatedAt(final LocalDateTime aupdatedAt) {
        this.updatedAt = aupdatedAt;
    }
}

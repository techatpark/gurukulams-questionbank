package com.gurukulams.questionbank.service;

import com.gurukulams.questionbank.payload.Question;
import com.gurukulams.questionbank.util.TestUtil;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.gurukulams.questionbank.service.QuestionService.OWNER_USER;

abstract class QuestionServiceTest {

    protected final QuestionService questionService;


    protected final AnswerService answerService;

    QuestionServiceTest() {
        Validator validator = Validation.buildDefaultValidatorFactory()
                .getValidator();
        this.questionService = new QuestionService(
                validator,
                TestUtil.dataManager());
        this.answerService = new AnswerService(this.questionService);
    }

    /**
     * Before.
     *
     * @throws IOException the io exception
     */
    @BeforeEach
    void before() throws  SQLException {
        cleanUp();
    }

    /**
     * After.
     */
    @AfterEach
    void after() throws SQLException {
        cleanUp();
    }

    private void cleanUp() throws SQLException {
        questionService.delete();
    }

    /**
     * Gets Correct Answer.
     * @param question
     * @throws SQLException
     */
    abstract String getCorrectAnswer(final Question question);

    @Test
    void testInvalidQuestions() {
        getInvalidQuestions().forEach(question -> {
            Assertions.assertThrows(ConstraintViolationException.class, () ->
                    questionService.create(List.of("c1",
                                    "c2"),
                            null,
                            question.getType(),
                            null,
                            OWNER_USER,
                            question)
            );
        });

    }

    /**
     * Gets Wrong Answers.
     * @param question
     * @throws SQLException
     */
    abstract List<String> getWrongAnswers(final Question question);

    abstract void testUpdate(final Question questionToUpdate, Locale locale) throws SQLException;

    /**
     * Creates a VALID question.
     * @return
     */
    abstract Question getTestQuestion() ;

    @Test
    void testCreate() throws SQLException {
        testAnswers(testCreate(null));;
        testAnswers(testCreate(Locale.GERMAN));
    }

    private void testAnswers(Question question) throws SQLException {
        // Right Answer
        Assertions.assertTrue(answerService.answer(question.id(),
                getCorrectAnswer(question)));
        // Wrong Answer

        for(String answer:getWrongAnswers(question)) {
            Assertions.assertFalse(answerService.answer(question.id(),
                    answer));
        }

    }

    /**
     * Tests the Question foir given locale.
     * @param locale
     * @throws SQLException
     */
    protected Question testCreate(Locale locale) throws SQLException {
        Question crateQuestion = getTestQuestion();

        // Create a Question
        Optional<Question> question = questionService.create(List.of("c1",
                        "c2"),
                null,
                crateQuestion.getType(),
                locale,
                OWNER_USER,
                crateQuestion);

        return question.get();


    }

    @Test
    void testUpdate() throws SQLException {
        Question question = testCreate(null);
        testUpdate(question, null);
        question = testCreate(Locale.GERMAN);
        testUpdate(question, Locale.GERMAN);
    }

    @Test
    void testDelete() throws SQLException {
        Question crateQuestion = getTestQuestion();


        // Create a Question
        Optional<Question> question = questionService.create(List.of("c1",
                        "c2"),
                null,
                crateQuestion.getType(),
                null,
                OWNER_USER,
                crateQuestion);


        questionService.delete(question.get().id(), crateQuestion.getType());

        Assertions.assertTrue(questionService.read(question.get().id(), null).isEmpty());

    }

    @Test
    void testList() throws SQLException {
        Question crateQuestion = getTestQuestion();



        // Create a Question
        questionService.create(List.of("c1",
                        "c2"),
                null,
                crateQuestion.getType(),
                null,
                OWNER_USER,
                crateQuestion);



        questionService.create(List.of("c1",
                        "c2"),
                null,
                crateQuestion.getType(),
                Locale.FRENCH,
                OWNER_USER,
                crateQuestion);

        Assertions.assertEquals(2,
                questionService.list(OWNER_USER, null, List.of("c1",
                        "c2")).size());

        Assertions.assertEquals(2,
                questionService.list(OWNER_USER, Locale.FRENCH, List.of("c1",
                        "c2")).size());

        Assertions.assertEquals(2,
                questionService.list("NEW_USER", null, List.of("c1",
                        "c2")).size());

        Assertions.assertEquals(2,
                questionService.list("NEW_USER", Locale.FRENCH, List.of("c1",
                        "c2")).size());

    }

    /**
     * Invalid Questions.
     * @return
     */
    abstract List<Question> getInvalidQuestions();

}

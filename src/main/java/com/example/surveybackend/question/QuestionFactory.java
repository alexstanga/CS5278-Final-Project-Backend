package com.example.surveybackend.question;

import java.util.List;

/**
 * Factory class for creating different types of survey questions.
 */
public class QuestionFactory {

    /**
     * Creates a Question object based on the specified type.
     *
     * @param type     The type of question to create (e.g., "text" or "radiogroup").
     * @param name     The name of the question.
     * @param title    The title or prompt of the question.
     * @param choices  The list of choices for the question (relevant for "radiogroup" type).
     * @return A Question object of the specified type.
     * @throws IllegalArgumentException If the provided question type is unknown.
     */
    public Question createQuestion(String type, String name, String title, List<Choice> choices) {
        switch(type){
            case "text":
                return new TextQuestion(name, title);
            case "radiogroup":
                return new RadioGroupQuestion(name, title, choices);
            default:
                throw new IllegalArgumentException("Unknown question type");
        }
    }
}
